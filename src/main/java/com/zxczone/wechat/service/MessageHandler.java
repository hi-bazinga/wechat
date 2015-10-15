package com.zxczone.wechat.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxczone.wechat.message.parser.XMLConvertor;
import com.zxczone.wechat.message.response.ResTextMessage;
import com.zxczone.wechat.util.MessageUtil;

/**
 * Service for processing text message
 *
 * @author Jason Zhao
 */
@Service
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);
    
    private static final RestTemplate restTmpl = new RestTemplate();
    
    private static final String API_KEY = "6747ba7946ccea6390d600a68dcaa337";
    
    private static final String ROBOT_ERROR_MSG = "你在说啥，有点听不懂。。。";
    
    /**
     * Process request message map, reply message in XML format
     * @param messageMap
     * @return
     */
    public static String processTextMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        String content = messageMap.get(MessageUtil.TAG_CONTENT);
        
        String replyText = getReplyFromRobot(content, clientName);
        
        return textToXML(myName, clientName, replyText);
    }
    
    public static String processVoiceMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        String recognition = messageMap.get(MessageUtil.TAG_RECOGNITION);
        
        String replyText = recognition == null ? "未开通语音识别功能" : getReplyFromRobot(recognition, clientName);
        return textToXML(myName, clientName, replyText);
    }
    
    public static String buildSubScribeReply(Map<String, String> messageMap){
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        
        return textToXML(myName, clientName, "熊孩子，怎么现在才关注我！");
    }
    
    public static String textToXML(String myName, String replyToName, String replyMsg){
        ResTextMessage resMsg = new ResTextMessage();
        
        resMsg.setFromUserName(myName);
        resMsg.setToUserName(replyToName);
        resMsg.setCreateTime(new Date().getTime());
        resMsg.setMsgType(MessageUtil.RES_MSG_TYPE_TEXT);
        resMsg.setContent(replyMsg);
            
        return XMLConvertor.textMsgToXML(resMsg);
    }
    
    /**
     * Get reply text from Robot
     * @param message
     * @param userId
     * @return
     */
    public static String getReplyFromRobot(String message, String userId) {
        String url = String.format("http://www.tuling123.com/openapi/api?key=%s&info=%s&userid=%s", API_KEY, message, userId); 
        LOG.debug("Get reply from robot: " + url);
        
        String replyStr = "";
        try {
            String replyJson = restTmpl.getForObject(url, String.class);
            RobotReply reply = new ObjectMapper().readValue(replyJson, RobotReply.class);
            replyStr = reply.getText();
            
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            replyStr = ROBOT_ERROR_MSG;
        }
        
        LOG.debug( String.format("Message: %s, Reply: %s", message, replyStr) );
        return replyStr;
    }
}

class RobotReply {
    private String code;
    private String text;
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}



