package com.zxczone.wechat.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxczone.wechat.message.parser.XMLConvertor;
import com.zxczone.wechat.pojo.BaiduResponse;
import com.zxczone.wechat.pojo.CoordConvResult;
import com.zxczone.wechat.pojo.LocationInfo;
import com.zxczone.wechat.pojo.RobotReply;
import com.zxczone.wechat.pojo.response.ResTextMessage;
import com.zxczone.wechat.util.Config;
import com.zxczone.wechat.util.FaceUtil;
import com.zxczone.wechat.util.MessageUtil;

/**
 * Service for processing request message
 *
 * @author Jason Zhao
 */
@Service
public class CoreMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(CoreMessageService.class);
    private static final RestTemplate restTmpl = new RestTemplate();
   
    /**
     * Process text request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public static String processTextMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        String content = messageMap.get(MessageUtil.TAG_CONTENT);
        
        /* Handle wechat face content */
        if (FaceUtil.isFaceText(content)) {
            content = FaceUtil.faceToText(content);
        }
        
        String replyText = getReplyFromRobot(content, clientName);
        return textToXML(myName, clientName, replyText);
    }
    
    /**
     * Process voice request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public static String processVoiceMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        String recognition = messageMap.get(MessageUtil.TAG_RECOGNITION);
        
        String replyText = recognition == null ? MessageUtil.VOICE_RECOG_NOT_OPEN_MSG
                : getReplyFromRobot(recognition, clientName);
        return textToXML(myName, clientName, replyText);
    }
    
    /**
     * Process link request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public static String processLinkMsg(Map<String, String> messageMap) {
        //TODO
        return null;
    }
    
    /**
     * Process location request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public static String processLocationMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        String locX = messageMap.get(MessageUtil.TAG_LOCATION_X);
        String locY = messageMap.get(MessageUtil.TAG_LOCATION_Y);
        
        LocationInfo locInfo = getLocInfoByCoord(locX, locY);
        String replyText = locInfo != null ? String.format("哈哈,我认识这个地方,%s,%s",
                locInfo.getFormatted_address(),
                locInfo.getSematic_description()) : MessageUtil.MAP_ERROR_MSG;

        return textToXML(myName, clientName, replyText);
    }
    
    /**
     * Process image request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public static String processImageMsg(Map<String, String> messageMap) {
        //TODO
        return null;
    }
    
    /**
     * Process subscribe event, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public static String processSubScribeReply(Map<String, String> messageMap){
        String clientName = messageMap.get(MessageUtil.TAG_FROM_USER_NAME);
        String myName = messageMap.get(MessageUtil.TAG_TO_USER_NAME);
        
        return textToXML(myName, clientName, "熊孩子，怎么现在才关注我！");
    }
    
    /**
     * Build XML for text response
     * @param senderName sender ID
     * @param receiverName receiver ID
     * @param message message content
     * @return
     */
    public static String textToXML(String senderName, String receiverName, String message){
        ResTextMessage resMsg = new ResTextMessage();
        resMsg.setFromUserName(senderName);
        resMsg.setToUserName(receiverName);
        resMsg.setCreateTime(new Date().getTime());
        resMsg.setMsgType(MessageUtil.RES_MSG_TYPE_TEXT);
        resMsg.setContent(message);
            
        return XMLConvertor.textMsgToXML(resMsg);
    }
    
    /**
     * Get location information by wechat coordinate. <br><br>
     * Wechat coordinate needs to be converted to baidu coordinate before calling baidu API.
     * @param lat wechat latitude
     * @param lng wechat longitude
     * @return LocationInfo object, null if formatted address or sematic description is empty.
     */
    public static LocationInfo getLocInfoByCoord(String lat, String lng) {
        
        LocationInfo locInfo = null;
        try {
            /* Convert wechat coordinate to baidu coordinate */
            String convertURL = String.format("http://api.map.baidu.com/geoconv/v1/?ak=%s&coords=%s,%s&from=3&to=5&output=json", 
                    Config.BAIDU_MAP_API_KEY, lng, lat);
            LOG.debug("Convert coordinate URL: " + convertURL);
            
            String convResJson = restTmpl.getForObject(convertURL, String.class);

            BaiduResponse<List<CoordConvResult>> convResponse = new ObjectMapper().readValue(
                    convResJson, new TypeReference<BaiduResponse<List<CoordConvResult>>>(){});
            CoordConvResult convResult = convResponse.getResult().get(0);
        
            /* Get location information by baidu coordinate */
            String locationURL = String.format("http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%s,%s&output=json",
                    Config.BAIDU_MAP_API_KEY, convResult.getY(), convResult.getX());
            LOG.debug("Get location info URL: " + locationURL);
        
            String resJson = restTmpl.getForObject(locationURL, String.class);
            
            BaiduResponse<LocationInfo> baiduRes = new ObjectMapper().readValue(
                    resJson, new TypeReference<BaiduResponse<LocationInfo>>(){});
            LocationInfo tempLoc = baiduRes.getResult();
            
            if (tempLoc.getFormatted_address() != null 
                    && !tempLoc.getFormatted_address().isEmpty()
                    && tempLoc.getSematic_description() != null
                    && !tempLoc.getSematic_description().isEmpty()) {
                locInfo = tempLoc;
            }
           
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        
        return locInfo;
    }
    
    /**
     * Get reply text from Robot
     * @param message
     * @param userId
     * @return
     */
    public static String getReplyFromRobot(String message, String userId) {
        String url = String.format("http://www.tuling123.com/openapi/api?key=%s&info=%s&userid=%s", 
                Config.TULING_API_KEY, message, userId); 
        LOG.debug("Get reply from robot: " + url);
        
        String replyStr = "";
        try {
            String replyJson = restTmpl.getForObject(url, String.class);
            RobotReply reply = new ObjectMapper().readValue(replyJson, RobotReply.class);
            replyStr = reply.getText();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            replyStr = MessageUtil.ROBOT_ERROR_MSG;
        }
        
        LOG.debug( String.format("Message: %s, Reply: %s", message, replyStr) );
        return replyStr;
    }
}

