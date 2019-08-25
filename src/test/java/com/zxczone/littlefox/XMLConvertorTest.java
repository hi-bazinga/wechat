package com.zxczone.littlefox;

import com.zxczone.littlefox.helper.XMLConverter;
import com.zxczone.littlefox.pojo.response.ResTextMessage;
import com.zxczone.littlefox.util.Constant;
import com.zxczone.littlefox.util.StringUtil;
import org.junit.Test;

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
