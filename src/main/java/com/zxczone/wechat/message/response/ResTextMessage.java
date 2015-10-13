package com.zxczone.wechat.message.response;

/**
 * Response Text Message
 *
 * @author Jason Zhao
 */
public class ResTextMessage extends ResMessage{

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }
    
}
