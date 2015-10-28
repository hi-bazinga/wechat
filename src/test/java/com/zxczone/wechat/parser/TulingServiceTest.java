package com.zxczone.wechat.parser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zxczone.wechat.service.TulingService;
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
        tulingService.getReply("好", "qsdasd");
    }
}
