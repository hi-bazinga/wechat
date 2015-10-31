package com.zxczone.wechat.parser;

import org.junit.Test;

import com.zxczone.wechat.helper.XMLConverter;
import com.zxczone.wechat.pojo.response.ResTextMessage;
import com.zxczone.wechat.util.Constant;
import com.zxczone.wechat.util.StringUtil;

/**
 *
 * @author Jason Zhao
 */
public class XMLConvertorTest {

    @Test
    public void testParse(){
        ResTextMessage textMsg = new ResTextMessage();
        textMsg.setContent("123");
        textMsg.setFromUserName("Jason");
        textMsg.setToUserName("Chloe");
        textMsg.setMsgType(Constant.RES_MSG_TYPE_TEXT);
        
        String xml = XMLConverter.textMsgToXML(textMsg);
        System.out.println(xml);
    }
    
    @Test
    public void testTrainNum(){
    	//String str = "Z21(直达特快)";
    	String str = "K75/K78(快速)";
    	String result = StringUtil.getTrainNum(str);
    	System.out.println(result);
    }
}
