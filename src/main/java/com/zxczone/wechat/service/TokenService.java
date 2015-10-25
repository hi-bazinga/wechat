package com.zxczone.wechat.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxczone.wechat.util.Config;

/**
 * Class for getting access token
 *
 * @author Jason Zhao
 */
@Service
public class TokenService {

    private static final Logger LOG = LoggerFactory.getLogger(CoreMessageService.class);
    private static final RestTemplate restTmpl = new RestTemplate();
    
    public static String getAccessToken() {
        String tokenURL = String.format(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                Config.WECHAT_APPID, 
                Config.WECHAT_APPSECRET);
        
        String token = null;
        try {
            String tokenJson = restTmpl.getForObject(tokenURL, String.class);
            TokenResult tokenRes = new ObjectMapper().readValue(tokenJson, TokenResult.class);
            token = tokenRes.getAccess_token();
            LOG.debug("Access Token: " + token);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        
        return token;
    }
}

class TokenResult {
    private String access_token;
    private String expires_in;
    
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getExpires_in() {
        return expires_in;
    }
    public void setExpired_in(String expires_in) {
        this.expires_in = expires_in;
    }
    
}
