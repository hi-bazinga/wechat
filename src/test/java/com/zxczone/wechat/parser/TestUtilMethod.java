package com.zxczone.wechat.parser;

import org.junit.Test;

import com.zxczone.wechat.service.CoreMessageService;
import com.zxczone.wechat.service.TokenService;
import com.zxczone.wechat.util.FaceUtil;

public class TestUtilMethod {

    @Test
    public void testFace(){
        System.out.println(FaceUtil.isFaceText("/::)/::)/::)"));
        System.out.println(FaceUtil.faceToText("/::)"));
    }
    
    @Test
    public void testBaiduAPI(){
        CoreMessageService.getLocInfoByCoord("31.304907", "121.514397");
    }
    
    @Test
    public void testGetToken(){
        TokenService.getAccessToken();
    }
}
