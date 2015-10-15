package com.zxczone.wechat.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxczone.wechat.message.parser.XMLConvertor;
import com.zxczone.wechat.util.MessageUtil;

/**
 * Core service for processing request
 *
 * @author Jason Zhao
 */
@Service
public class CoreService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CoreService.class);
    
    public String processRequest(HttpServletRequest request){
        String responseXML = null;
        
        try {
            Map<String, String> messageMap = XMLConvertor.parseXMLFromRequest(request);
            LOG.debug("Request message map: " + messageMap.toString());
            
            String msgType = messageMap.get(MessageUtil.TAG_MSG_TYPE);
            switch (msgType) {
            
                /* Event Message */
                case MessageUtil.REQ_MSG_TYPE_EVENT: {
                    String eventType = messageMap.get(MessageUtil.TAG_EVENT);  
    
                    if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                        responseXML = MessageHandler.buildSubScribeReply(messageMap);
                    } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                        LOG.debug(String.format("User %s has unsubscribed!", messageMap.get(MessageUtil.TAG_FROM_USER_NAME)));
                    } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                        //TODO
                    }
                    break;
                }
                
                /* Voice Message */
                case MessageUtil.REQ_MSG_TYPE_VOICE: {
                    responseXML = MessageHandler.processVoiceMsg(messageMap);
                    break;
                }
                    
                /* Text Message*/
                case MessageUtil.REQ_MSG_TYPE_TEXT: {
                    responseXML = MessageHandler.processTextMsg(messageMap);
                    break;
                }
            }
        } catch (IOException | DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        
        return responseXML;
    }
}
