package com.zxczone.littlefox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxczone.littlefox.helper.ResponseXMLBuilder;
import com.zxczone.littlefox.pojo.baidumap.BaiduResponse;
import com.zxczone.littlefox.pojo.baidumap.CoordConvResult;
import com.zxczone.littlefox.pojo.baidumap.LocationInfo;
import com.zxczone.littlefox.pojo.response.Article;
import com.zxczone.littlefox.pojo.tuling.BaseResponse;
import com.zxczone.littlefox.pojo.tuling.FlightInfo;
import com.zxczone.littlefox.pojo.tuling.LinkResponse;
import com.zxczone.littlefox.pojo.tuling.ListResponse;
import com.zxczone.littlefox.pojo.tuling.News;
import com.zxczone.littlefox.pojo.tuling.Recipe;
import com.zxczone.littlefox.pojo.tuling.TrainInfo;
import com.zxczone.littlefox.util.Constant;
import com.zxczone.littlefox.util.FaceUtil;
import com.zxczone.littlefox.util.StringUtil;

/**
 * Service for processing request message
 *
 * @author Jason Zhao
 */
@Service
public class CoreMessageService {
    
    @Autowired
    RestTemplate restTmpl;
    
    @Autowired
    TulingService tulingService;
    
    @Autowired
    ResponseXMLBuilder respXmlBuilder;

    @Value("${baidumap.api.key}")
    private String BAIDUMAP_API_KEY;

    private static final Logger LOG = LoggerFactory.getLogger(CoreMessageService.class);
   
    /**
     * Process text request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public String processTextMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(Constant.TAG_FROM_USER_NAME);
        String myName = messageMap.get(Constant.TAG_TO_USER_NAME);
        String content = messageMap.get(Constant.TAG_CONTENT);
        
        /* Handle littlefox face content */
        if (FaceUtil.isFaceText(content)) {
            content = FaceUtil.faceToText(content);
        }
        
        return getResponseXMLFromTuling(myName, clientName, content);
    }
    
    /**
     * Process voice request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public String processVoiceMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(Constant.TAG_FROM_USER_NAME);
        String myName = messageMap.get(Constant.TAG_TO_USER_NAME);
        String recognition = messageMap.get(Constant.TAG_RECOGNITION);
        
        return recognition == null ? Constant.VOICE_RECOG_NOT_OPEN_MSG
                : getResponseXMLFromTuling(myName, clientName, recognition);
    }
    
    /**
     * Process link request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public String processLinkMsg(Map<String, String> messageMap) {
        //TODO
        return null;
    }
    
    /**
     * Process location request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public String processLocationMsg(Map<String, String> messageMap) {
        String clientName = messageMap.get(Constant.TAG_FROM_USER_NAME);
        String myName = messageMap.get(Constant.TAG_TO_USER_NAME);
        String locX = messageMap.get(Constant.TAG_LOCATION_X);
        String locY = messageMap.get(Constant.TAG_LOCATION_Y);
        
        LocationInfo locInfo = getLocInfoByCoord(locX, locY);
        String replyText = locInfo != null ? String.format("哈哈,我认识这个地方,%s,%s",
                locInfo.getFormatted_address(),
                locInfo.getSematic_description()) : Constant.BAIDUMAP_ERROR_MSG;

        return respXmlBuilder.buildTextXML(myName, clientName, replyText);
    }
    
    /**
     * Process image request, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public String processImageMsg(Map<String, String> messageMap) {
        //TODO
        return null;
    }
    
    /**
     * Process subscribe event, get response message in XML
     * @param messageMap parameter map parsed from request
     * @return
     */
    public String processSubScribeReply(Map<String, String> messageMap){
        String clientName = messageMap.get(Constant.TAG_FROM_USER_NAME);
        String myName = messageMap.get(Constant.TAG_TO_USER_NAME);
        
        return respXmlBuilder.buildTextXML(myName, clientName, Constant.SUBSCRIBE_MSG);
    }
    
    /**
     * Get location information by littlefox coordinate. <br><br>
     * Wechat coordinate needs to be converted to baidu coordinate before calling baidu API.
     * @param lat littlefox latitude
     * @param lng littlefox longitude
     * @return LocationInfo object, null if formatted address or sematic description is empty.
     */
    public LocationInfo getLocInfoByCoord(String lat, String lng) {
        
        LocationInfo locInfo = null;
        try {
            /* Convert littlefox coordinate to baidu coordinate */
            String convertURL = String.format("http://api.map.baidu.com/geoconv/v1/?ak=%s&coords=%s,%s&from=3&to=5&output=json", BAIDUMAP_API_KEY, lng, lat);
            LOG.debug("Convert coordinate URL: " + convertURL);
            
            String convResJson = restTmpl.getForObject(convertURL, String.class);

            BaiduResponse<List<CoordConvResult>> convResponse = new ObjectMapper().readValue(
                    convResJson, new TypeReference<BaiduResponse<List<CoordConvResult>>>(){});
            CoordConvResult convResult = convResponse.getResult().get(0);
        
            /* Get location information by baidu coordinate */
            String locationURL = String.format("http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%s,%s&output=json",
                    BAIDUMAP_API_KEY, convResult.getLatitude(), convResult.getLongitude());
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
     * Get response from Tuling and return generated XML.
     * @param myName
     * @param clientName 
     * @param message
     * @return
     */
    public String getResponseXMLFromTuling(String myName, String clientName, String message) {
        BaseResponse response = tulingService.getResponse(message, clientName);
        String code = response.getCode();
        String text = response.getText();
        
        String responseXML = null;
        switch(code) {
            case TulingService.LINK: {
                String url = ((LinkResponse) response).getUrl();
                String respText = String.format("%s, <a href=\"%s\">%s</a>", text, url, url);
                responseXML = respXmlBuilder.buildTextXML(myName, clientName, respText);
                break;
            }
            case TulingService.RECIPE: {
                List<Article> articles = new ArrayList<Article>();
                
                @SuppressWarnings("unchecked")
                List<Recipe> recipeList = ((ListResponse<Recipe>) response).getList();
                for (Recipe recipe : recipeList) {
                    Article ar = new Article();
                    /* Don't display details of the first article */
                    String title = recipe.getName() + (articles.size() != 0 ? "\n" + recipe.getInfo() : "");
                    ar.setTitle(title);
                    ar.setUrl(recipe.getDetailurl());
                    ar.setPicUrl(recipe.getIcon());
                    ar.setDescription(recipe.getInfo());
                    articles.add(ar);
                }
                responseXML = respXmlBuilder.buildNewsXML(myName, clientName, articles, true);
                break;
            }
            case TulingService.FLIGHT: {
                List<Article> articles = new ArrayList<Article>();
                
                @SuppressWarnings("unchecked")
                List<FlightInfo> flightList = ((ListResponse<FlightInfo>) response).getList();
                for (FlightInfo flight : flightList) {
                    Article ar = new Article();
                    ar.setTitle(flight.getFlight());
                    ar.setUrl("");
                    ar.setPicUrl(flight.getIcon());
                    ar.setDescription(flight.getStarttime() + " ~ " + flight.getEndtime());
                    articles.add(ar);
                }
                responseXML = respXmlBuilder.buildNewsXML(myName, clientName, articles, true);
                break;
            }
            case TulingService.TRAIN: {
                @SuppressWarnings("unchecked")
                List<TrainInfo> trainList = ((ListResponse<TrainInfo>) response).getList();
            	
                List<Article> articles = new ArrayList<Article>();
				for (TrainInfo train : trainList) {
					if (articles.size() > 4)
						break;
					
					String titile = String.format("%s\n%s-%s\n%s-%s", train.getTrainnum(),
							train.getStarttime(), train.getEndtime(), train.getStart(), train.getTerminal());
					String detailUrl = String.format("%s/trainDetail?trainNum=%s&startStation=%s&endStation=%s",
							Constant.QUNAR_ROOT_URL, StringUtil.getTrainNum(train.getTrainnum()), train.getStart(),
							train.getTerminal());
					
					Article ar = new Article();
					ar.setTitle(titile);
					ar.setUrl(detailUrl);
					ar.setPicUrl(train.getIcon());
					ar.setDescription("");
					articles.add(ar);
				}
				
                Article banner = new Article();
				banner.setTitle(Constant.TRAIN_MSG_BANNER);
				banner.setUrl(Constant.QUNAR_ROOT_URL);
				banner.setPicUrl("");
				banner.setDescription("");
				articles.add(0, banner);
 
				String findAllUrl = String.format("%s/trainlist?&startStation=%s&endStation=%s&sort=0",
						Constant.QUNAR_ROOT_URL, trainList.get(0).getStart(), trainList.get(0).getTerminal());
				
				Article bottom = new Article();
				bottom.setTitle("不全？查看全部 >>>");
				bottom.setUrl(findAllUrl);
				bottom.setPicUrl("");
				bottom.setDescription("");
				articles.add(bottom);
				
                responseXML = respXmlBuilder.buildNewsXML(myName, clientName, articles, false);
                break;
            }
            case TulingService.NEWS: {
                List<Article> articles = new ArrayList<Article>();
                
                @SuppressWarnings("unchecked")
                List<News> newsList = ((ListResponse<News>) response).getList();
                for (News news : newsList) {
                    Article ar = new Article();
                    ar.setTitle(news.getArticle());
                    ar.setUrl(news.getDetailurl());
                    ar.setPicUrl(news.getIcon());
                    ar.setDescription(news.getSource());
                    articles.add(ar);
                }
                responseXML = respXmlBuilder.buildNewsXML(myName, clientName, articles, true);
                break;
            }
            default:
                responseXML = respXmlBuilder.buildTextXML(myName, clientName, text);
        }
        
        return responseXML;
    }
}

