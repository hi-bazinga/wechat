package com.zxczone.wechat.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zxczone.wechat.pojo.tuling.BaseResponse;
import com.zxczone.wechat.service.CoreMessageService;
import com.zxczone.wechat.service.MenuService;
import com.zxczone.wechat.service.TokenService;
import com.zxczone.wechat.service.TulingService;
import com.zxczone.wechat.util.FaceUtil;
import com.zxczone.wechat.util.SpringBasedTest;

public class ServiceTest extends SpringBasedTest{

    @Autowired
    TulingService tulingService;
    
    @Autowired
    CoreMessageService coreService;
    
    @Autowired
    TokenService tokenService;
    
    @Autowired
    MenuService menuService;
    
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
        tokenService.getAccessToken();
    }
    
    @Test
    public void testCreateMenu(){
        menuService.createMenu();
    }
    
    @Test
    public void testGetResponseXML(){
        coreService.getResponseXMLFromTuling("Jason", "Client", "鱼香肉丝怎么做");
    }
    
    @Test
    public void testTuling(){
        BaseResponse response = tulingService.getResponse("菜谱", "qsdasd");
        System.out.println(response.getCode());
    }

}
