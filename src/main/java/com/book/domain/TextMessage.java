package com.book.domain;

public class TextMessage {

    private String ToUserName;    //开发者微信号
    private String FromUserName;    //发送方帐号（一个OpenID）
    private long CreateTime;    //消息创建时间 （整型）
    private String MsgType    ;//text
    private String Content;//文本消息内容
    private String MsgId;//消息id，64位整型
    public String getToUserName() {
        return ToUserName;
    }
    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }
    public String getFromUserName() {
        return FromUserName;
    }
    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }
    public long getCreateTime() {
        return CreateTime;
    }
    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }
    public String getMsgType() {
        return MsgType;
    }
    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
    public String getMsgId() {
        return MsgId;
    }
    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((CreateTime == 0) ? 0 : Long.toString(CreateTime).hashCode());
        result = prime * result + ((FromUserName == null) ? 0 : FromUserName.hashCode());
        result = prime * result + ((MsgId == null) ? 0 : MsgId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TextMessage other = (TextMessage) obj;
        if (CreateTime != other.CreateTime){
            return false;
        }
        if (FromUserName == null) {
            if (other.FromUserName != null)
                return false;
        } else if (!FromUserName.equals(other.FromUserName))
            return false;
        if (MsgId == null) {
            if (other.MsgId != null)
                return false;
        } else if (!MsgId.equals(other.MsgId))
            return false;
        return true;
    }
}