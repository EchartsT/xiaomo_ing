package com.book.service;

import net.sf.json.JSONObject;
import com.google.gson.Gson;
import com.book.domain.BaseMessage;
import com.book.util.WeiXinUtil;


public class WeixinService {

    private  static  String sendMessage_url="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    private  static  String getUserInfo_url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    // 公众号向用户主动发送消息
    public void sendMessage(String accessToken,BaseMessage message){
        //1.获取json字符串：将message对象转换为json字符串
        Gson gson = new Gson();
        String jsonMessage =gson.toJson(message);      //使用gson.toJson(user)即可将user对象顺序转成json
        System.out.println("jsonTextMessage:"+jsonMessage);


        //2.获取请求的url
        String url=sendMessage_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送消息
        JSONObject jsonObject = WeiXinUtil.httpRequest(url, "POST", jsonMessage);
        System.out.println("jsonObject:"+jsonObject.toString());
    }

    //获取关注用户信息
    /**
     * 获取微信用户账号的相关信息
     * @param opendID  用户的openId，这个通过当用户进行了消息交互的时候，才有
     * @return
     */
    public static JSONObject getUserInfo(String accessToken, String opendID){
        String url = getUserInfo_url.replace("ACCESS_TOKEN" , accessToken);
        url = url.replace("OPENID" ,opendID);
        JSONObject jsonObject = WeiXinUtil.doGetStr(url);
        return jsonObject;
    }
}