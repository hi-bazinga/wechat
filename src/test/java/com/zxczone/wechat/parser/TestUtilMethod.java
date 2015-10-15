package com.zxczone.wechat.parser;

import org.junit.Test;

import com.zxczone.wechat.util.FaceUtil;

public class TestUtilMethod {

    @Test
    public void testFace(){
        System.out.println(FaceUtil.isWXFace("/::)/::)/::)"));
        System.out.println(FaceUtil.faceToText("/::)"));
    }
}
