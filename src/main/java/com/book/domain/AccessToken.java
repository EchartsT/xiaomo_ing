package com.book.domain;
  
/**
 * @desc  : 微信通用接口凭证
 */
public class AccessToken {  
    // 获取到的凭证  
    private String access_token;
    // 获取时间
    private String access_time;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_time() {
        return access_time;
    }

    public void setAccess_time(String access_time) {
        this.access_time = access_time;
    }
}