package com.zxczone.wechat.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Utility class for message verification
 *
 * @author Jason Zhao
 */
public class VerifyUtil {
    
    private static final String token = "zxczone";  
    
    public static boolean checkSignature(String signature, String timestamp, String nonce) {  
        String[] paramArr = new String[] {token, timestamp, nonce};  
        Arrays.sort(paramArr);
        
        StringBuilder content = new StringBuilder();  
        for (String param : paramArr) {  
            content.append(param);  
        }  
        String tmpSign = DigestUtils.sha1Hex(content.toString());
        
        return tmpSign != null ? tmpSign.equalsIgnoreCase(signature) : false;  
    }  
}
