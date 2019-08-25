package com.zxczone.littlefox.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.NioEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zxczone.littlefox.helper.XMLConverter;
import com.zxczone.littlefox.service.CoreMessageService;
import com.zxczone.littlefox.util.Constant;
import com.zxczone.littlefox.util.ValidateUtil;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handle GET and POST request
 *
 * @author Jason Zhao
 */

@RestController
public class MessageController {
    
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    CoreMessageService coreService;
    
    /**
     * Validate signature in request
     * @param request
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String checkSignature(HttpServletRequest request){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");
        
        logger.info(String.format("[checkSignature] signature: %s, timestamp: %s, nonce: %s, echostr: %s",
                signature, timestamp, nonce, echostr));
        
        String responseStr = null;
        if (signature != null && timestamp != null && nonce != null && echostr != null
                && ValidateUtil.checkSignature(signature, timestamp, nonce)) {
            responseStr = echostr;
            logger.info("[checkSignature] Validation completed!");
        } else {
            logger.error("[checkSignature] Validation failed!");
        }
        
        return responseStr;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE)
    public String handleMessage(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");
        
        logger.info(String.format("[handleMessage] signature: %s, timestamp: %s, nonce: %s",
                signature, timestamp, nonce));
        
//        if (!(signature != null && timestamp != null && nonce != null
//                && ValidateUtil.checkSignature(signature, timestamp, nonce))) {
//            logger.error("Validation failed!");
//            return null;
//        }
        
        String resXML = processRequest(request);
        logger.info("[handleMessage] Response XML: \n" + resXML);
        
        return resXML;
    }
    
    /**
     * Dispatch message according to message type.
     * @param request
     * @return
     */
    private String processRequest(HttpServletRequest request){
        String responseXML = null;
        
        try {
            Map<String, String> messageMap = XMLConverter.parseXMLFromRequest(request);
            logger.info("Request message map: " + messageMap.toString());
            
            String msgType = messageMap.get(Constant.TAG_MSG_TYPE);
            logger.info("Message Type: " + msgType);
            
            switch (msgType) {
                /* Event Message */
                case Constant.REQ_MSG_TYPE_EVENT: {
                    String eventType = messageMap.get(Constant.TAG_EVENT);  
    
                    if (eventType.equals(Constant.EVENT_TYPE_SUBSCRIBE)) {
                        responseXML = coreService.processSubScribeReply(messageMap);
                        logger.info(String.format("User %s has subscribed!", messageMap.get(Constant.TAG_FROM_USER_NAME)));
                    } else if (eventType.equals(Constant.EVENT_TYPE_UNSUBSCRIBE)) {  
                        logger.info(String.format("User %s has unsubscribed!", messageMap.get(Constant.TAG_FROM_USER_NAME)));
                    } else if (eventType.equals(Constant.EVENT_TYPE_CLICK)) {  
                        //TODO
                    }
                    break;
                }
                
                /* Voice Message */
                case Constant.REQ_MSG_TYPE_VOICE: {
                    responseXML = coreService.processVoiceMsg(messageMap);
                    break;
                }
                    
                /* Text Message */
                case Constant.REQ_MSG_TYPE_TEXT: {
                    responseXML = coreService.processTextMsg(messageMap);
                    break;
                }
                
                /* Link Message */
                case Constant.REQ_MSG_TYPE_LINK: {
                    responseXML = coreService.processLinkMsg(messageMap);
                    break;
                }
                
                /* Location Message */
                case Constant.REQ_MSG_TYPE_LOCATION: {
                    responseXML = coreService.processLocationMsg(messageMap);
                    break;
                }
                
                /* Image Message */
                case Constant.REQ_MSG_TYPE_IMAGE: {
                    responseXML = coreService.processImageMsg(messageMap);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        return responseXML;
    }
    
}
