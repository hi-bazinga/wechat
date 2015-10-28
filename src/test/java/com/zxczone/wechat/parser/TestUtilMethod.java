package com.zxczone.wechat.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zxczone.wechat.service.CoreMessageService;
import com.zxczone.wechat.service.MenuService;
import com.zxczone.wechat.service.TokenService;
import com.zxczone.wechat.service.TulingService;
import com.zxczone.wechat.util.FaceUtil;

public class TestUtilMethod {

    @Autowired
    TulingService tulingService;
    
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
    
    @Test
    public void testCreateMenu(){
        MenuService.createMenu();
    }

}
