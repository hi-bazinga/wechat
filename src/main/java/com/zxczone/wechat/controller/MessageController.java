package com.zxczone.wechat.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxczone.wechat.helper.XMLConverter;
import com.zxczone.wechat.service.CoreMessageService;
import com.zxczone.wechat.util.Constant;
import com.zxczone.wechat.util.ValidateUtil;

/**
 * Handle GET and POST request
 *
 * @author Jason Zhao
 */
@Controller
public class MessageController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);
    
    @Autowired
    CoreMessageService coreService;
    
    /**
     * Validate signature in request
     * @param request
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody public String checkSignature(HttpServletRequest request){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");
        
        LOG.debug(String.format("signature: %s, timestamp: %s, nonce: %s, echostr: %s",
                signature, timestamp, nonce, echostr));
        
        String responseStr = null;
        if (signature != null && timestamp != null && nonce != null && echostr != null
                && ValidateUtil.checkSignature(signature, timestamp, nonce)) {
            responseStr = echostr;
            LOG.info("Validation completed!");
        } else {
            LOG.error("Validation failed!");
        }
        
        return responseStr;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    @ResponseBody public String handleMessage(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");
        
        LOG.debug(String.format("signature: %s, timestamp: %s, nonce: %s",
                signature, timestamp, nonce));
        
        if (!(signature != null && timestamp != null && nonce != null
                && ValidateUtil.checkSignature(signature, timestamp, nonce))) {
            LOG.error("Validation failed!");
            return null;
        }
        
        String resXML = processRequest(request);
        LOG.debug("Response XML: \n" + resXML);
        
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
            LOG.debug("Request message map: " + messageMap.toString());
            
            String msgType = messageMap.get(Constant.TAG_MSG_TYPE);
            LOG.debug("Message Type: " + msgType);
            
            switch (msgType) {
                /* Event Message */
                case Constant.REQ_MSG_TYPE_EVENT: {
                    String eventType = messageMap.get(Constant.TAG_EVENT);  
    
                    if (eventType.equals(Constant.EVENT_TYPE_SUBSCRIBE)) {
                        responseXML = coreService.processSubScribeReply(messageMap);
                        LOG.info(String.format("User %s has subscribed!", messageMap.get(Constant.TAG_FROM_USER_NAME)));
                    } else if (eventType.equals(Constant.EVENT_TYPE_UNSUBSCRIBE)) {  
                        LOG.info(String.format("User %s has unsubscribed!", messageMap.get(Constant.TAG_FROM_USER_NAME)));
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
        } catch (IOException | DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        
        return responseXML;
    }
    
}
