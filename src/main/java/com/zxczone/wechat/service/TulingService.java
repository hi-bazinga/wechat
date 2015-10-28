package com.zxczone.wechat.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxczone.wechat.tuling.pojo.BaseResponse;
import com.zxczone.wechat.util.Config;

/**
 * This class provide methods for Tuling API
 *
 * @author Jason Zhao
 */
@Service
public class TulingService {

    @Autowired
    RestTemplate restTmpl;
    
    private static final Logger LOG = LoggerFactory.getLogger(TulingService.class);
    
    private static final String API_URL_TEMPLATE = "http://www.tuling123.com/openapi/api?key=%s&info=%s&userid=%s";
    
    public BaseResponse getReply(String message, String userId){
        String url = String.format(API_URL_TEMPLATE, Config.TULING_API_KEY, message, userId);
        LOG.debug("Get response from tuling API: " + url);
        
        BaseResponse response = null;
        try {
            /* 
             * Tuling API response is text/javascript, RestTemplate can only parse json data. 
             * So we need to receive response as String first, then parse it by jackson.
             */
            String responseJson = restTmpl.getForObject(url, String.class);   
            response = new ObjectMapper().readValue(responseJson, BaseResponse.class);
            
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        
        return response;
    }
}
