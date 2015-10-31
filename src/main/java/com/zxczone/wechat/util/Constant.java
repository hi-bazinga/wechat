package com.zxczone.wechat.util;

/**
 * Utility class for message constant
 *
 * @author Jason Zhao
 */
public class Constant {
   
    /* Message Type */
    public static final String REQ_MSG_TYPE_TEXT = "text";
    public static final String REQ_MSG_TYPE_LINK = "link";  
    public static final String REQ_MSG_TYPE_IMAGE = "image";  
    public static final String REQ_MSG_TYPE_LOCATION = "location";
    public static final String REQ_MSG_TYPE_VOICE = "voice";  
    public static final String REQ_MSG_TYPE_EVENT = "event";  
    
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
    public static final String EVENT_TYPE_CLICK = "CLICK";
    
    public static final String RES_MSG_TYPE_TEXT = "text";
    public static final String RES_MSG_TYPE_MUSIC = "music";  
    public static final String RES_MSG_TYPE_NEWS = "news";  
    
    /* XML Tag Name */
    public static final String TAG_MSG_TYPE = "MsgType";
    public static final String TAG_TO_USER_NAME = "ToUserName";
    public static final String TAG_FROM_USER_NAME = "FromUserName";
    public static final String TAG_CREATE_TIME = "CreateTime";
    public static final String TAG_CONTENT = "Content";
    public static final String TAG_EVENT = "Event";
    public static final String TAG_RECOGNITION = "Recognition";
    public static final String TAG_LOCATION_X = "Location_X";
    public static final String TAG_LOCATION_Y = "Location_Y";
    
    /* Default Text */
    public static final String TULING_ERROR_MSG = "你在说啥，有点听不懂。。。";
    public static final String BAIDUMAP_ERROR_MSG = "我不认识这个地方呀";
    public static final String VOICE_RECOG_NOT_OPEN_MSG = "未开通语音识别功能";
    
    public static final String TRAIN_MSG_BANNER = "已帮您找到以下车次:";
    public static final String QUNAR_ROOT_URL = "http://touch.qunar.com/h5/train";
    
}
