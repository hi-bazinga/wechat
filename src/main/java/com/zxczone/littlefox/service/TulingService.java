package com.zxczone.littlefox.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxczone.littlefox.pojo.tuling.BaseResponse;
import com.zxczone.littlefox.pojo.tuling.FlightInfo;
import com.zxczone.littlefox.pojo.tuling.LinkResponse;
import com.zxczone.littlefox.pojo.tuling.ListResponse;
import com.zxczone.littlefox.pojo.tuling.News;
import com.zxczone.littlefox.pojo.tuling.Recipe;
import com.zxczone.littlefox.pojo.tuling.TrainInfo;
import com.zxczone.littlefox.util.Constant;

/**
 * This class provide methods for Tuling API.
 *
 * @author Jason Zhao
 */
@Service
public class TulingService {

    @Autowired
    RestTemplate restTmpl;

    @Value("${tuling.api.key}")
    private String TULING_API_KEY;
    
    private static final Logger LOG = LoggerFactory.getLogger(TulingService.class);
    
    private static final String API_URL_TEMPLATE = "http://www.tuling123.com/openapi/api?key=%s&info=%s&userid=%s";
    
    public static final String TEXT = "100000";
    public static final String LINK = "200000";
    public static final String NEWS = "302000";
    public static final String TRAIN = "305000";
    public static final String FLIGHT = "306000";
    public static final String RECIPE = "308000";
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Get response from Tuling API.
     * @param message
     * @param userId
     * @return response, never be null.
     */
    public BaseResponse getResponse(String message, String userId){
        String url = String.format(API_URL_TEMPLATE, TULING_API_KEY, message, userId);
        LOG.debug("Get response from tuling API: " + url);
        
        BaseResponse response = null;
        try {
            /* 
             * Tuling API response is text/javascript, RestTemplate can only parse json data. 
             * So we need to receive response as String first, then parse it by jackson.
             */
            String responseJson = restTmpl.getForObject(url, String.class);   
            response = new ObjectMapper().readValue(responseJson, BaseResponse.class);
            response = completeResponseDetail(response, responseJson);
            
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            response = new BaseResponse();
            response.setCode("000000");
            response.setText(Constant.TULING_ERROR_MSG);
        }
        
        return response;
    }
    
    /**
     * Complete detail information for base response
     * @param baseResp base response with only code and text
     * @param json response json with detail information
     * @return response with detail information
     * @throws IOException
     */
    private BaseResponse completeResponseDetail(BaseResponse baseResp, String json) throws IOException {
        String code = baseResp.getCode();
        String text = baseResp.getText();
        
        BaseResponse resultResp = null;
        switch(code) {
            case TEXT:
                resultResp = baseResp;
                break;
            case LINK:
                resultResp = mapper.readValue(json, LinkResponse.class);
                break;
            case NEWS:
                resultResp = mapper.readValue(json, new TypeReference<ListResponse<News>>(){});
                break;
            case TRAIN:
                resultResp = mapper.readValue(json, new TypeReference<ListResponse<TrainInfo>>(){});
                break;
            case FLIGHT:
                resultResp = mapper.readValue(json, new TypeReference<ListResponse<FlightInfo>>(){});
                break;
            case RECIPE:
                resultResp = mapper.readValue(json, new TypeReference<ListResponse<Recipe>>(){});
                break;
            default:
                if ("40004".equals(code)) {
                    resultResp.setCode("000000");
                    resultResp.setText(Constant.TULING_CALL_LIMIT_MSG);
                    LOG.warn("Tuling call exceeds max limit!");
                } else {
                    throw new IOException(String.format("Error Code: %s, Message: %s", code, text));
                }
        }
        
        return resultResp;
    }
}
