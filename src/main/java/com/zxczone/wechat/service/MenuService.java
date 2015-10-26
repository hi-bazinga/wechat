package com.zxczone.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jason Zhao
 */
public class MenuService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CoreMessageService.class);
    private static RestTemplate restTmpl = new RestTemplate();

    private static String menuJson = 
            " { " +
            "   \"button\":[ " +
            "     {     " +
            "       \"type\":\"click\", " +
            "       \"name\":\"聊天\", " +
            "       \"key\":\"chat_with_robot\" " +
            "     }, " +
            "     { " +
            "       \"type\":\"location_select\", " +
            "       \"name\":\"发送位置\", " +
            "       \"key\":\"send_location\" " +
            "     }, " +
            "     { " +
            "       \"name\":\"关于\", " +
            "       \"sub_button\":[ " +
            "         { " +
            "           \"type\":\"click\", " +
            "           \"name\":\"我\", " +
            "           \"key\":\"about_me\" " +
            "         }, " +
            "         { " +
            "           \"type\":\"view\", " +
            "           \"name\":\"ZXCZONE\", " +
            "           \"view\":\"http://blog.zxczone.com\" " +
            "         } " +
            "       ] " +
            "     } " +
            "   ] " +
            " } ";
    
    /**
     * Not supported for personal account
     */
    public static void createMenu(){
        String menuURL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TokenService.getAccessToken();
        
        ResponseEntity<String> response = restTmpl.exchange(menuURL,
                HttpMethod.POST, new HttpEntity<>(menuJson), String.class);
        
        LOG.debug(response.getBody());
    }
    
}
