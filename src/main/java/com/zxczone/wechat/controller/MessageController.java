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
import com.zxczone.wechat.util.SignUtil;

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
        
        if (signature == null || timestamp == null || nonce == null || echostr == null)
            return null;
        
        LOG.debug("Check signature...");

        return SignUtil.checkSignature(signature, timestamp, nonce) ? echostr : null;
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
    @ResponseBody public String receiveMsg(HttpServletRequest request) {

        String resXML = coreService.processRequest(request);
        LOG.debug("Response XML: \n" + resXML);
        
        return resXML;
    }
    
}
