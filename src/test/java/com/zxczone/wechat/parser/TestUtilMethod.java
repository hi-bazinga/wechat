package com.zxczone.wechat.parser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zxczone.wechat.service.CoreMessageService;
import com.zxczone.wechat.service.MenuService;
import com.zxczone.wechat.service.TokenService;
import com.zxczone.wechat.service.TulingService;
import com.zxczone.wechat.util.FaceUtil;
import com.zxczone.wechat.util.SpringBasedTest;

public class TestUtilMethod extends SpringBasedTest{

    @Autowired
    TulingService tulingService;
    
    @Autowired
    CoreMessageService coreService;
    
    @Test
    public void testFace(){
        System.out.println(FaceUtil.isFaceText("/::)/::)/::)"));
        System.out.println(FaceUtil.faceToText("/::)"));
    }
    
    @Test
    public void testBaiduAPI(){
        coreService.getLocInfoByCoord("31.304907", "121.514397");
    }
    
    @Test
    public void testGetToken(){
        TokenService.getAccessToken();
    }
    
    @Test
    public void testCreateMenu(){
        MenuService.createMenu();
    }
    
    @Test
    public void testGetResponseXML(){
        coreService.getResponseXMLFromRobot("Jason", "Client", "鱼香肉丝怎么做");
    }

}
