package com.zxczone.wechat.parser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zxczone.wechat.service.TulingService;
import com.zxczone.wechat.tuling.pojo.BaseResponse;
import com.zxczone.wechat.util.SpringBasedTest;

/**
 *
 * @author Jason Zhao
 */
public class TulingServiceTest extends SpringBasedTest{

    @Autowired
    TulingService tulingService;
    
    @Test
    public void testTuling(){
        BaseResponse response = tulingService.getResponse("菜谱", "qsdasd");
        System.out.println(response.getCode());
    }
}
