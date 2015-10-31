package com.zxczone.wechat.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zxczone.wechat.pojo.response.Article;
import com.zxczone.wechat.pojo.response.ResNewsMessage;
import com.zxczone.wechat.pojo.response.ResTextMessage;
import com.zxczone.wechat.util.Constant;

@Component
public class ResponseXMLBuilder {

    /**
     * Build XML for text response
     * @param senderName sender ID
     * @param receiverName receiver ID
     * @param message message content
     * @return
     */
    public String buildTextXML(String senderName, String receiverName, String message){
        ResTextMessage resMsg = new ResTextMessage();
        resMsg.setFromUserName(senderName);
        resMsg.setToUserName(receiverName);
        resMsg.setCreateTime(new Date().getTime());
        resMsg.setMsgType(Constant.RES_MSG_TYPE_TEXT);
        resMsg.setContent(message);
            
        return XMLConverter.textMsgToXML(resMsg);
    }
    
    /**
     * Build XML for picture-text response
     * @param senderName sender ID
     * @param receiverName receiver ID
     * @param  message content
     * @param articles article list
     * @return
     */
    public String buildNewsXML(String senderName, String receiverName, List<Article> articles){
        ResNewsMessage newsMsg = new ResNewsMessage();
        newsMsg.setFromUserName(senderName);
        newsMsg.setToUserName(receiverName);
        newsMsg.setCreateTime(new Date().getTime());
        newsMsg.setMsgType(Constant.RES_MSG_TYPE_NEWS);
        
        /* Don't use subList, otherwise XStream will generate malformed XML */
        int limit = 5;
        if (articles.size() > limit) {
            newsMsg.setArticleCount(limit);
            
            List<Article> truncList = new ArrayList<Article>();
            truncList.addAll(articles.subList(0, limit));
            newsMsg.setArticles(truncList);
        } else {
            newsMsg.setArticleCount(articles.size());
            newsMsg.setArticles(articles);
        }
            
        return XMLConverter.newsMsgToXML(newsMsg);
    }
}
