package com.zxczone.wechat.parser;

import org.junit.Test;

import com.zxczone.wechat.service.CoreMessageHandler;
import com.zxczone.wechat.util.FaceUtil;

public class TestUtilMethod {

    @Test
    public void testFace(){
        System.out.println(FaceUtil.isFaceText("/::)/::)/::)"));
        System.out.println(FaceUtil.faceToText("/::)"));
    }
    
    @Test
    public void testBaiduAPI(){
        CoreMessageHandler.getLocInfoByCoord("31.304907", "121.514397");
    }
}
