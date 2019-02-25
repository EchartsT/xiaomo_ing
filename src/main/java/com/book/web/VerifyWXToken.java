package com.book.web;

import com.book.domain.*;
import com.book.service.ActiveService;
import com.book.service.OperatorService;
import com.book.service.UserService;
import com.book.util.*;
import com.book.service.WeixinService;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 核心请求处理类
 *
 */

public class VerifyWXToken extends HttpServlet{
    private UserService userService= ApplicationContextHelper.getBean(UserService.class);
    private OperatorService operatorService= ApplicationContextHelper.getBean(OperatorService.class);
    private ActiveService activeService= ApplicationContextHelper.getBean(ActiveService.class);

    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("-----开始校验签名-----");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO 消息的接收、处理、响应
        request.setCharacterEncoding("UTF-8");//转换编码方式
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();//通过PrintWriter返回消息至微信后台
        //String accessToken = "18_uQPNIfvdSIbD047YybtwGeViKwUdGlpJzRmi2fiyCJXFd-HFz7B9uy1N1ZvKI_srCSaqIOFJx2jrP6eaf-0PgqkgTio76bJSyCj-VHtroeiOjhJ0DcBgj61zSe50XTPnMcr5VN7-YuBXfA1ZTLYeAGAOGT";

        /**
         * accessToken超过两小时重新获取
         */
        String accessToken =null;
        AccessToken accessTokenObject = new AccessToken();
        accessTokenObject = operatorService.getAccess();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date oldtime = new Date();
        Date newtime =new Date();
        if(accessTokenObject.getAccess_token() != null ){
            //判断是不是间隔两个小时
            try {
                oldtime= sdf.parse(accessTokenObject.getAccess_time());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                newtime = sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long cha = newtime.getTime() - oldtime.getTime();
            double result = cha * 1.0 / (1000 * 60 * 60);
            if(result >2){
                //accessTokenObject=WeiXinUtil.getAccessToken(WeiXinParamesUtil.appid,WeiXinParamesUtil.secret);
                accessTokenObject=WeiXinUtil.getToken(WeiXinParamesUtil.appid,WeiXinParamesUtil.secret);
                operatorService.deleteAccess();
                operatorService.updateAccess(accessTokenObject);
            }
        }else {
            accessTokenObject= WeiXinUtil.getToken(WeiXinParamesUtil.appid,WeiXinParamesUtil.secret);
            operatorService.updateAccess(accessTokenObject);
        }
        accessToken = accessTokenObject.getAccess_token();

        //接收消息
        try {
            Map<String,String> map = MessageUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");//发送方帐号（一个OpenID）
            String toUserName = map.get("ToUserName");//开发者微信号
            String msgType = map.get("MsgType");//text//如果是文本消息的话，MsgType="text"
            String content = map.get("Content");//文本消息内容
            String MsgId = map.get("MsgId");//消息ID
            String eventType = map.get("Event");//事件类型

            String line;
            String lines = "";
            String message = null;

            WeixinService weixinService=new WeixinService();
            JSONObject userInfo = weixinService.getUserInfo(accessToken,fromUserName);//获取用户信息（openID，昵称，订阅状态）

            //获取当前系统时间作为用户发送消息时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String askTime = df.format(new Date());


            User user = new User();
            Oprecord oprecord = new Oprecord();
            ActiveRank activeRank = new ActiveRank();

            //判断是否为事件类型
            if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){
                if(MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)){//处理订阅事件
                    TextMessage pretext = new TextMessage();
                    pretext.setFromUserName(toUserName);//注意，这里发送者与接收者调换了
                    pretext.setToUserName(fromUserName);
                    pretext.setMsgType("text");//文本类型
                    pretext.setCreateTime(new Date().getTime());//当前时间
                    pretext.setContent("1:日常聊天，2:专业聊天");//返回消息
                    pretext.setMsgId(MsgId);//消息ID

                    //将文本消息转换为xml
                    message = MessageUtil.textMessageToXml(pretext);
                    out.print(message);//返回消息
                    out.close();
                    System.out.println(message);

                    //创建txt文件用于存储聊天记录
                    weixinService.createTxtFile(fromUserName);

                    //在user表（用户表）中插入该用户的记录
                    String userName = userInfo.getString("nickname");
                    boolean isSubscribe = (userInfo.getInt("subscribe") == 1 ? true : false);
                    user.setUserId(fromUserName);
                    user.setUserName(userName);
                    user.setIsSubscribe(isSubscribe);
                    user.setFileName(fromUserName + ".txt");
                    user.setChatData("1");
                    userService.addUser2(user);

                    //在oprecord表（操作流水记录表）中插入一条记录用于记录该微信用户的流水
                    oprecord.setUserId(fromUserName);
                    oprecord.setOperatorId(fromUserName+askTime);
                    oprecord.setStartTime(askTime);
                    oprecord.setFileName(fromUserName + ".txt");
                    operatorService.addOprecord(oprecord);

                    //在activerank表（活跃度排名表）中插入一条记录用于记录该微信用户的活跃度
                    activeRank.setUserId(fromUserName);
                    activeService.addActiveItem(activeRank);

                }else if(MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)){//处理取消订阅事件
                    //在user表（用户表）中更改该用户的记录
                    boolean isSubscribe = (userInfo.getInt("subscribe") == 1 ? true : false);
                    user.setUserId(fromUserName);
                    user.setIsSubscribe(isSubscribe);
                    userService.updateUser(user);
                }
            }
            else {
                user = userService.queryUserById(fromUserName);
                //判断是否为文本消息
                if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                    if(content.equals("1")){
                        TextMessage pretext = new TextMessage();
                        pretext.setFromUserName(toUserName);//注意，这里发送者与接收者调换了
                        pretext.setToUserName(fromUserName);
                        pretext.setMsgType("text");//文本类型
                        pretext.setCreateTime(new Date().getTime());//当前时间
                        pretext.setContent("进入日常聊天");//返回消息
                        pretext.setMsgId(MsgId);//消息ID
                        //将文本消息转换为xml
                        message = MessageUtil.textMessageToXml(pretext);
                        out.print(message);//返回消息
                        user.setUserId(fromUserName);
                        user.setChatData("1");
                        userService.updateUser_chatdata(user);
                        return;
                    }else if(content.equals("2")){
                        TextMessage pretext = new TextMessage();
                        pretext.setFromUserName(toUserName);//注意，这里发送者与接收者调换了
                        pretext.setToUserName(fromUserName);
                        pretext.setMsgType("text");//文本类型
                        pretext.setCreateTime(new Date().getTime());//当前时间
                        pretext.setContent("进入专业对话");//返回消息
                        pretext.setMsgId(MsgId);//消息ID
                        //将文本消息转换为xml
                        message = MessageUtil.textMessageToXml(pretext);
                        out.print(message);//返回消息
                        user.setUserId(fromUserName);
                        user.setChatData("2");
                        userService.updateUser_chatdata(user);
                        return;
                    } else{
                        out.print("");//返回消息
                    }
                }
                out.close();
                String[] args = new String[] {"python","D:\\python\\code\\chatbot_by_similarity\\demo\\demo_chat_ask&answer.py",content};

                user = userService.queryUserById(fromUserName);
                if(user.getChatData().equals("1"))
                    args = new String[] {"python","D:\\python\\code\\chatbot_by_similarity\\demo\\demo_chat_ask&answer.py",content};
                else if(user.getChatData().equals("2")){
                    //将本次用户发送的消息存于TXT文件（用于词频统计）
                    String alldataFilename = FileUtil.createDirectory()+"/"+"allchatdata.txt";
                    weixinService.writeChatInfo(alldataFilename, content + "，");

                    args = new String[] {"D:\\python\\anaconda\\setupway\\python","D:\\python\\code\\QASystemOnMedicalKG\\chatbot_graph.py",content};
                }

                //执行python脚本———聊天
                Process proc;
                proc = Runtime.getRuntime().exec(args);

                //使用缓冲流接受程序返回的结果
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));//注意格式
                while ((line = in.readLine()) != null) {
                    lines += (line + "\n");
                }
                in.close();
                proc.waitFor();

                //主动发送消息给用户
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);//注意，这里发送者与接收者调换了
                text.setToUserName(fromUserName);
                text.setMsgType("text");//文本类型
                text.setCreateTime(new Date().getTime());//当前时间
                text.setContent(lines);//返回消息
                text.setMsgId(MsgId);//消息ID

                //排重
                boolean isDuplicate = MessageUtil.isDuplicate(map);
                if (isDuplicate)
                    return;

                //将文本消息转换为xml
                message = MessageUtil.textMessageToXml(text);

                System.out.println(message);

                //创建文本消息对象
                SendTextMessage sendtextmessage = new SendTextMessage();
                sendtextmessage.setTouser(fromUserName);  //不区分大小写
                sendtextmessage.setMsgtype("text");
                Text texts = new Text();
                texts.setContent(lines);
                sendtextmessage.setText(texts);

                //发送消息：调用业务类，发送消息
                weixinService.sendMessage(accessToken, sendtextmessage);

                //获取当前系统时间作为回答时间
                String answerTime = df.format(new Date());

                //将本轮对话存入TXT文件
                String filename = FileUtil.createDirectory()+"/"+fromUserName+ ".txt";
                String data = askTime + "  " + "用户："+ content + "\r\n" + answerTime + " " + "小莫："+ lines + "\r\n";
                weixinService.writeChatInfo(filename, data);

                //在oprecord表（操作流水记录表）中更新相应记录
                oprecord.setUserId(fromUserName);
                oprecord.setEndTime(answerTime);
                operatorService.updateOprecord(oprecord);

                //在activerank表（活跃度排名表）中更新相应记录
                activeRank.setUserId(fromUserName);
                activeService.updateActiveItem(activeRank);
            }
        } catch (InterruptedException | DocumentException e) {
            e.printStackTrace();
        } finally {
            //out.close();
        }
    }
}
