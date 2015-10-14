package com.zxczone.wechat.util;

/**
 * TODO
 *
 * @author Jason Zhao
 */
public class MessageUtil {
   
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
    
}
