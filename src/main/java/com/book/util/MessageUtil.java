package com.book.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


import javax.servlet.http.HttpServletRequest;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.book.domain.TextMessage;
import com.thoughtworks.xstream.XStream;

/*
 * 创建一个方法 进行xml->map集合的转换
 *
 */
public class MessageUtil {
    //消息类型的常量
    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_VIDEO="video";
    public static final String MESSAGE_LINK="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVENT="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
    public static final String MESSAGE_CLICK="CLICK";
    public static final String MESSAGE_VIEW="VIEW";
    public static final String MSGTYPE_EVENT = "event";//消息类型--事件
    public static final String MESSAGE_SUBSCIBE = "subscribe";//消息事件类型--订阅事件
    public static final String MESSAGE_UNSUBSCIBE = "unsubscribe";//消息事件类型--取消订阅事件
    private static final int MESSAGE_CACHE_SIZE = 1000;
    private static List<TextMessage> MESSAGE_CACHE = new ArrayList<TextMessage>(MESSAGE_CACHE_SIZE);

    public static Map<String,String>xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
        //创建一个集合
        Map<String, String> map=new HashMap<String, String>();
        SAXReader reader=new SAXReader();

        InputStream ins=request.getInputStream();
        org.dom4j.Document doc=reader.read(ins);

        Element root=doc.getRootElement();

        List<Element> list=root.elements();
        for (Element e:list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;


    }
    //将文本消息转化为xml文件
    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream=new XStream();
        xstream.alias("xml", textMessage.getClass()) ;
        return xstream.toXML(textMessage);
    }

    //判断微信请求是否重复（微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次）
    public static boolean isDuplicate(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        String createTime = map.get("CreateTime");
        String msgId = map.get("MsgId");

        TextMessage textMessage = new TextMessage();

        if (msgId != null) {
            textMessage.setMsgId(msgId);
        } else {
            textMessage.setCreateTime(Long.parseLong(createTime));
            textMessage.setFromUserName(fromUserName);
        }

        if (MESSAGE_CACHE.contains(textMessage)) {
            // 缓存中存在，直接pass
            return true;
        } else {
            setMessageToCache(textMessage);
            return false;
        }
    }
    private static void setMessageToCache(TextMessage textMessage) {
        if (MESSAGE_CACHE.size() >= MESSAGE_CACHE_SIZE) {
            MESSAGE_CACHE.remove(0);
        }
        MESSAGE_CACHE.add(textMessage);
    }

     //响应订阅事件--回复文本消息
    public static void subscribeForText(){
        System.out.println("欢迎关注，精彩内容不容错过！！！");
        return;
    }

    /*
     * 响应取消订阅事件
     */
    public static String unsubscribe(String toUserName,String fromUserName){
        //TODO 可以进行取关后的其他后续业务处理
        System.out.println("用户："+ fromUserName +"取消关注~");
        return "";
    }

    /*
     * 主菜单
     */
    public static String menuText(){
        StringBuffer sb=new StringBuffer();
        sb.append("      欢迎您的关注！\n");
        sb.append(" 请按照菜单提示进行操作\n\n");
        sb.append("     1.账号介绍          \n");
        sb.append("     2.美剧推荐          \n");
        sb.append("     3.超级英雄          \n");
        sb.append("     4.美剧时间表      \n");
        sb.append("     ?.回复？调出此菜单\n\n");
        sb.append("     更多功能尚在开发中  \n");
        return sb.toString();
    }
    /*
     * 拼接文本消息
     */
    public static String initText(String toUserName,String fromUserName,String content ){
        TextMessage text=new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }
    /*
     * 回复“1”弹出信息
     */
    public static String firstMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("本账号属于测试账号，正在开发中，主要发布与美剧相关信息！");

        return sb.toString();
    }
    /*
     * 回复“2”弹出信息
     */
    public static String secondMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("美剧推荐功能正在开发中！");

        return sb.toString();
    }
    /*
     * 回复“3”弹出信息
     */
    public static String thirdMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("超级英雄功能正在开发中！");

        return sb.toString();
    }
    /*
     * 回复“1”弹出信息
     */
    public static String fourtMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("美剧时间表功能正在开发中！");

        return sb.toString();
    }
    public static String wenMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("美剧时间表功能正在开发中！");

        return sb.toString();
    }
}