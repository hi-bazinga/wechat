package com.zxczone.littlefox.util;

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
    public static final String TULING_CALL_LIMIT_MSG = "小狐狸今天累了，不想聊天了。。。";
    public static final String BAIDUMAP_ERROR_MSG = "我不认识这个地方呀";
    public static final String VOICE_RECOG_NOT_OPEN_MSG = "未开通语音识别功能";
    public static final String SUBSCRIBE_MSG =  
    		  "你好！我是小狐狸，很高兴认识你！\n"
    		+ "你可以跟我聊天，另外也可以问我天气、火车票、机票、新闻甚至是菜谱哦！\n"
    		+ "比如：\n"
    		+ "“上海天气”\n"
    		+ "“北京到上海火车（或飞机）”\n"
    		+ "“体育新闻”\n"
    		+ "“鱼香肉丝怎么做”\n"
    		+ "......\n"
    		+ "对了，还可以跟我语音聊天哦～";
    
    public static final String TRAIN_MSG_BANNER = "已帮您找到以下车次:";
    public static final String QUNAR_ROOT_URL = "http://touch.qunar.com/h5/train";
    
}
