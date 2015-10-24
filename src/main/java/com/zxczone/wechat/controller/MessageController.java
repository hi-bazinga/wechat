package com.zxczone.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxczone.wechat.service.MessageDispatcher;
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
    MessageDispatcher msgDispatcher;
    
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
        
        String resXML = msgDispatcher.processRequest(request);
        LOG.debug("Response XML: \n" + resXML);
        
        return resXML;
    }
    
}
