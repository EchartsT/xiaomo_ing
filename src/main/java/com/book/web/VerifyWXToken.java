package com.book.web;

import com.book.domain.Oprecord;
import com.book.domain.SendTextMessage;
import com.book.domain.Text;
import com.book.service.OperatorService;
import com.book.util.ApplicationContextHelper;
import com.book.util.FileUtil;
import com.book.util.SignUtil;
import com.book.util.MessageUtil;
import com.book.domain.TextMessage;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 核心请求处理类
 *
 */

public class VerifyWXToken extends HttpServlet{
    private OperatorService operatorService= ApplicationContextHelper.getBean(OperatorService.class);

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
        String accessToken = "18_r-ZsoJRDrNlEKFZ2DYB_bF2RPuOu6QxHgyNcXruTryfUxJaJ8-t2NbPcNEUlX1D5Pxahgvs3FzbmEeJFULRreAc-X0tTwAW92qPExl70LKvYNVwjr7z-ToN5VOhF7c6Cl20SLUAj2ImTKACYMHLhAJAPKO";

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
            JSONObject userInfo = null;

            //获取当前系统时间作为用户发送消息时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String askTime = df.format(new Date());

            WeixinService weixinService=new WeixinService();
            Oprecord oprecord = new Oprecord();

            //判断是否为事件类型
            if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){
                if(MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)){//处理订阅事件

                    //获取用户信息（openID，昵称，订阅状态）
                    userInfo = weixinService.getUserInfo(accessToken,fromUserName);
                    System.out.println(userInfo);

                    //创建txt文件用于存储聊天记录
                    weixinService.createTxtFile(fromUserName);

                    //在oprecord表（操作流水记录表）中插入一条记录用于记录该微信用户的流水
                    oprecord.setUserId(fromUserName);
                    oprecord.setOperatorId(fromUserName+askTime);
                    oprecord.setStartTime(askTime);
                    oprecord.setFileName(fromUserName + ".txt");
                    operatorService.addOprecord(oprecord);

                }else if(MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)){//处理取消订阅事件
                    userInfo = weixinService.getUserInfo(accessToken,fromUserName);
                    System.out.println(userInfo);
                }
            }
            else {
                //判断是否为文本消息
                //不论发送什么，先被动回复一句“正在查询，请稍候...”
                if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                    TextMessage pretext = new TextMessage();
                    pretext.setFromUserName(toUserName);//注意，这里发送者与接收者调换了
                    pretext.setToUserName(fromUserName);
                    pretext.setMsgType("text");//文本类型
                    pretext.setCreateTime(new Date().getTime());//当前时间
                    pretext.setContent("正在查询，请稍候...");//返回消息
                    pretext.setMsgId(MsgId);//消息ID

                    //将文本消息转换为xml
                    message = MessageUtil.textMessageToXml(pretext);
                    out.print(message);//返回消息
                    System.out.println(message);
                }
                out.close();

                //执行python脚本———聊天
                Process proc;
                //String[] args = new String[]{"python", "D:\\python\\code\\chatbot_by_similarity\\demo\\demo_knowledge_ask&answer.py", content};
                //String[] args = new String[] {"python","D:\\python\\code\\chatbot_by_similarity\\demo\\demo_chat_ask&answer.py",content};
                String[] args = new String[] {"D:\\python\\anaconda\\setupway\\python","D:\\python\\code\\QASystemOnMedicalKG\\chatbot_graph.py",content};
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

                //1.创建文本消息对象
                SendTextMessage sendtextmessage = new SendTextMessage();
                sendtextmessage.setTouser(fromUserName);  //不区分大小写
                sendtextmessage.setMsgtype("text");
                Text texts = new Text();
                texts.setContent(lines);
                sendtextmessage.setText(texts);

                //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
                //String accessToken= WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
                //System.out.println("accessToken:"+accessToken);

                //3.发送消息：调用业务类，发送消息
                weixinService.sendMessage(accessToken, sendtextmessage);

                //获取当前系统时间作为回答时间
                String answerTime = df.format(new Date());

                //将本轮对话存入TXT文件
                String filename = FileUtil.createDirectory()+"/"+fromUserName+ ".txt";
                String data = askTime + "  " + content + "\r\n" + answerTime + " " + lines + "\r\n";
                weixinService.writeChatInfo(filename, data);

                //在oprecord表（操作流水记录表）中的更新相应记录
                oprecord.setUserId(fromUserName);
                oprecord.setEndTime(answerTime);
                operatorService.updateOprecord(oprecord);
            }
        } catch (InterruptedException | DocumentException e) {
            e.printStackTrace();
        } finally {
            //out.close();
        }
    }
}
