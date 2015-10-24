package com.zxczone.wechat.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zxczone.wechat.message.parser.XMLConvertor;
import com.zxczone.wechat.util.MessageUtil;

/**
 * Service for dispatch request
 *
 * @author Jason Zhao
 */
@Service
public class MessageDispatcher {
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageDispatcher.class);
    
    public String processRequest(HttpServletRequest request){
        String responseXML = null;
        
        try {
            Map<String, String> messageMap = XMLConvertor.parseXMLFromRequest(request);
            LOG.debug("Request message map: " + messageMap.toString());
            
            String msgType = messageMap.get(MessageUtil.TAG_MSG_TYPE);
            LOG.debug("Message Type: " + msgType);
            
            switch (msgType) {
                /* Event Message */
                case MessageUtil.REQ_MSG_TYPE_EVENT: {
                    String eventType = messageMap.get(MessageUtil.TAG_EVENT);  
    
                    if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                        responseXML = CoreMessageHandler.processSubScribeReply(messageMap);
                        LOG.info(String.format("User %s has subscribed!", messageMap.get(MessageUtil.TAG_FROM_USER_NAME)));
                    } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                        LOG.info(String.format("User %s has unsubscribed!", messageMap.get(MessageUtil.TAG_FROM_USER_NAME)));
                    } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                        //TODO
                    }
                    break;
                }
                
                /* Voice Message */
                case MessageUtil.REQ_MSG_TYPE_VOICE: {
                    responseXML = CoreMessageHandler.processVoiceMsg(messageMap);
                    break;
                }
                    
                /* Text Message */
                case MessageUtil.REQ_MSG_TYPE_TEXT: {
                    responseXML = CoreMessageHandler.processTextMsg(messageMap);
                    break;
                }
                
                /* Link Message */
                case MessageUtil.REQ_MSG_TYPE_LINK: {
                    responseXML = CoreMessageHandler.processLinkMsg(messageMap);
                    break;
                }
                
                /* Location Message */
                case MessageUtil.REQ_MSG_TYPE_LOCATION: {
                    responseXML = CoreMessageHandler.processLocationMsg(messageMap);
                    break;
                }
                
                /* Image Message */
                case MessageUtil.REQ_MSG_TYPE_IMAGE: {
                    responseXML = CoreMessageHandler.processImageMsg(messageMap);
                    break;
                }
            }
        } catch (IOException | DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        
        return responseXML;
    }
}
