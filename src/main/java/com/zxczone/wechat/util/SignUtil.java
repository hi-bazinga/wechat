package com.zxczone.wechat.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Jason Zhao
 */
public class SignUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SignUtil.class);
    
    private static String token = "zxczone";  
    
    public static boolean checkSignature(String signature, String timestamp, String nonce) {  
        String[] arr = new String[] {token, timestamp, nonce};  
        Arrays.sort(arr);  
        
        StringBuilder content = new StringBuilder();  
        for (int i = 0; i < arr.length; i++) {  
            content.append(arr[i]);  
        }  
        
        String tmpStr = DigestUtils.sha1Hex(content.toString());
        
        boolean isValid = tmpStr != null ? tmpStr.equalsIgnoreCase(signature) : false;  
        LOG.debug("Check signature: " + isValid);
        
        return isValid;
    }  
}
