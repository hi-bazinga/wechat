package com.zxczone.wechat.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for face
 *
 * @author Jason Zhao
 */
public class FaceUtil {

    private static final String faceRegex = "(/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>)+";  
    
    private static Map<String, String> map = new HashMap<String, String>();

    public static boolean isWXFace(String content) {  
        Pattern p = Pattern.compile(faceRegex);  
        Matcher m = p.matcher(content);  
        return m.matches();
    }  
    
    public static String faceToText(String face){
        return map.containsKey(face) ? map.get(face) : face;
    }
    
    static {
        map.put("/::)", "微笑");
        map.put("/::~", "撇嘴");
        map.put("/::B", "色");
        map.put("/::|", "发呆");
        map.put("/:8-)", "得意");
        map.put("/::<", "流泪");
        map.put("/::$", "害羞");
        map.put("/::X", "闭嘴");
        map.put("/::Z", "睡觉");
        map.put("/::'(", "大哭");
        map.put("/::-|", "尴尬");
        map.put("/::@", "发怒");
        map.put("/::P", "调皮");
        map.put("/::D", "呲牙");
        map.put("/::O", "惊讶");
        map.put("/::(", "难过");
        map.put("/::+", "酷");
        map.put("/:--b", "冷汗");
        map.put("/::Q", "抓狂");
        map.put("/::T", "吐");
        map.put("/:,@P", "偷笑");
        map.put("/:,@-D", "愉快");
        map.put("/::d", "白眼");
        map.put("/:,@o", "傲慢");
        map.put("/::g", "饥饿");
        map.put("/:|-)", "困");
        map.put("/::!", "惊恐");
        map.put("/::L", "流汗");
        map.put("/::>", "憨笑");
        map.put("/::,@", "休闲");
        map.put("/:,@f", "奋斗");
        map.put("/::-S", "咒骂");
        map.put("/:?", "疑问");
        map.put("/:,@x", "嘘");
        map.put("/:,@@", "晕");
        map.put("/::8", "疯了");
        map.put("/:,@!", "衰");
        map.put("/:!!!", "骷髅");
        map.put("/:xx", "打你");
        map.put("/:bye", "再见");
        map.put("/:wipe", "擦汗");
        map.put("/:dig", "抠鼻");
        map.put("/:handclap", "鼓掌");
        map.put("/:&-(", "糗大了");
        map.put("/:B-)", "坏笑");
        map.put("/:<@", "左哼哼");
        map.put("/:@>", "右哼哼");
        map.put("/::-O", "打哈欠");
        map.put("/:>-|", "鄙视");
        map.put("/:P-(", "委屈");
        map.put("/::'|", "快哭了");
        map.put("/:X-)", "阴险");
        map.put("/::*", "亲亲");
        map.put("/:@x", "吓");
        map.put("/:8*", "可怜");
        map.put("/:pd", "菜刀");
        map.put("/:<W>", "西瓜");
        map.put("/:beer", "啤酒");
        map.put("/:basketb", "篮球");
        map.put("/:oo", "乒乓球");
        map.put("/:coffee", "咖啡");
        map.put("/:eat", "吃饭");
        map.put("/:pig", "猪头");
        map.put("/:rose", "玫瑰");
        map.put("/:fade", "凋谢");
        map.put("/:showlove", "嘴唇");
        map.put("/:heart", "爱心");
        map.put("/:break", "心碎");
        map.put("/:cake", "蛋糕");
        map.put("/:li", "闪电");
        map.put("/:bome", "炸弹");
        map.put("/:kn", "小刀");
        map.put("/:footb", "足球");
        map.put("/:ladybug", "瓢虫");
        map.put("/:shit", "便便");
        map.put("/:moon", "月亮");
        map.put("/:sun", "太阳");
        map.put("/:gift", "礼物");
        map.put("/:hug", "拥抱");
        map.put("/:strong", "强");
        map.put("/:weak", "弱");
        map.put("/:share", "握手");
        map.put("/:v", "胜利");
        map.put("/:@)", "抱拳");
        map.put("/:jj", "勾引");
        map.put("/:@@", "拳头");
        map.put("/:bad", "差劲");
        map.put("/:lvu", "爱你");
        map.put("/:no", "不");
        map.put("/:ok", "OK");
        map.put("/:love", "爱情");
        map.put("/:<L>", "飞吻");
        map.put("/:jump", "跳跳");
        map.put("/:shake", "发抖");
        map.put("/:<O>", "怄火");
        map.put("/:circle", "转圈");
        map.put("/:kotow", "磕头");
        map.put("/:turn", "回头");
        map.put("/:skip", "跳绳");
        map.put("/:oY", "投降");
        map.put("/:#-0", "激动");
        map.put("/:hiphot", "乱舞");
        map.put("/:kiss", "献吻");
        map.put("/:<&", "左太极");
        map.put("/:&>", "右太极");
    }

}
