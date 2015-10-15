package com.zxczone.wechat.pojo.request;

/**
 * Request Text Message
 *
 * @author Jason Zhao
 */
public class ReqTextMessage extends ReqMessage{

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}
