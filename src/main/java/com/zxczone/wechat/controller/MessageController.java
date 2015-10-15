package com.zxczone.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxczone.wechat.service.CoreService;
import com.zxczone.wechat.util.VerifyUtil;

/**
 * Handle GET and POST request
 *
 * @author Jason Zhao
 */

@Controller
public class MessageController {
    
    @Autowired
    CoreService coreService;
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody public String getToken(HttpServletRequest request){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");
        
        LOG.debug(String.format("signature: %s, timestamp: %s, nonce: %s, echostr: %s",
                signature, timestamp, nonce, echostr));
        
        String responseStr = null;
        if (signature != null && timestamp != null && nonce != null && echostr != null
                && VerifyUtil.checkSignature(signature, timestamp, nonce)) {
            responseStr = echostr;
            LOG.info("Verification completed!");
            
        } else {
            responseStr = "Verification failed!";
            LOG.error(responseStr);
        }
        
        return responseStr;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    @ResponseBody public String receiveMsg(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");
        
        LOG.debug(String.format("signature: %s, timestamp: %s, nonce: %s",
                signature, timestamp, nonce));
        
        if (!(signature != null && timestamp != null && nonce != null
                && VerifyUtil.checkSignature(signature, timestamp, nonce))) {
            LOG.error("Verification failed!");
            return null;
        }
        
        String resXML = coreService.processRequest(request);
        
        LOG.debug("Response XML: \n" + resXML);
        
        return resXML;
    }
    
}
