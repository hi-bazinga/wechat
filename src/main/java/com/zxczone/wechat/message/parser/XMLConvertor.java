package com.zxczone.wechat.message.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.zxczone.wechat.pojo.response.ResTextMessage;

/**
 * TODO
 *
 * @author Jason Zhao
 */
public class XMLConvertor {

    public static Map<String, String> parseXMLFromRequest(HttpServletRequest request) throws IOException, DocumentException{
        
        InputStream inputStream = request.getInputStream();  
        
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();  
        
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();  
      
        Map<String, String> map = new HashMap<String, String>();  
        for (Element e : elementList) {  
            map.put(e.getName(), e.getText()); 
        }
      
        inputStream.close();  
      
        return map;  
    }
    
    public static String textMsgToXML(ResTextMessage message) {
        XStream stream = new XStream(new XppDriver(){
            public HierarchicalStreamWriter createWriter(Writer out) {  
                return new PrettyPrintWriter(out) {  

                    boolean cdata = true;  
          
                    public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {  
                        super.startNode(name, clazz);  
                    }  
          
                    protected void writeText(QuickWriter writer, String text) {  
                        if (cdata) {  
                            writer.write("<![CDATA[");  
                            writer.write(text);  
                            writer.write("]]>");  
                        } else {  
                            writer.write(text);  
                        }  
                    }  
                };
            }
        });
        
        stream.alias("xml", ResTextMessage.class);
        
        return stream.toXML(message);
    }
    
}
