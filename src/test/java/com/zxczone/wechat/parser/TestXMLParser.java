package com.zxczone.wechat.parser;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zxczone.wechat.message.parser.XMLConvertor;
import com.zxczone.wechat.pojo.response.ResTextMessage;
import com.zxczone.wechat.util.MessageUtil;

/**
 * TODO
 *
 * @author Jason Zhao
 */
public class TestXMLParser {

    @Test
    public void testParse(){
        ResTextMessage textMsg = new ResTextMessage();
        textMsg.setContent("123");
        textMsg.setFromUserName("Jason");
        textMsg.setToUserName("Chloe");
        textMsg.setMsgType(MessageUtil.RES_MSG_TYPE_TEXT);
        
        String xml = XMLConvertor.textMsgToXML(textMsg);
        System.out.println(xml);
    }
    
    @Test
    public void testRobot() throws JsonParseException, JsonMappingException, IOException{
    }
}
