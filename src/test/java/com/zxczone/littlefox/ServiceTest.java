package com.zxczone.littlefox;

import com.zxczone.littlefox.pojo.tuling.BaseResponse;
import com.zxczone.littlefox.service.CoreMessageService;
import com.zxczone.littlefox.service.MenuService;
import com.zxczone.littlefox.service.TokenService;
import com.zxczone.littlefox.service.TulingService;
import com.zxczone.littlefox.util.FaceUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTest extends LittlefoxApplicationTests{

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
