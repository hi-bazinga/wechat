package com.zxczone.littlefox.helper;

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
import com.zxczone.littlefox.pojo.response.Article;
import com.zxczone.littlefox.pojo.response.ResNewsMessage;
import com.zxczone.littlefox.pojo.response.ResTextMessage;

/**
 * XML converter
 *
 * @author Jason Zhao
 */
public class XMLConverter {
    
    private static XStream stream = new XStream(new XppDriver(){
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                boolean cdata = true;  
      
                public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {  
                    super.startNode(name, clazz);  
                }  
      
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[" + text + "]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };
        }
    });

    /**
     * Read XML data from request
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
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
    
    /**
     * Convert text message object to XML format
     * @param message
     * @return
     */
    public static String textMsgToXML(ResTextMessage message) {
        stream.alias("xml", ResTextMessage.class);
        return stream.toXML(message);
    }
    
    /**
     * Convert news message object to XML format
     * @param message
     * @return
     */
    public static String newsMsgToXML(ResNewsMessage newsMsg) {
        stream.alias("xml", ResNewsMessage.class);  
        stream.alias("item", Article.class);  
        return stream.toXML(newsMsg);  
    }
   
}
