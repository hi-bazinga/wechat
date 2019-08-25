package com.zxczone.littlefox.service;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class for getting access token
 *
 * @author Jason Zhao
 */
@Service
public class TokenService {
    
    @Autowired
    RestTemplate restTmpl;

    @Value("${wechat.appid}")
    private String WECHAT_APPID;

    @Value("${wechat.appsecret}")
    private String WECHAT_APPSECRET;

    private static final Logger LOG = LoggerFactory.getLogger(CoreMessageService.class);
    
    public String getAccessToken() {
        String tokenURL = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", WECHAT_APPID, WECHAT_APPSECRET);
        
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

@Data
class TokenResult {
    private String access_token;
    private String expires_in;
}
