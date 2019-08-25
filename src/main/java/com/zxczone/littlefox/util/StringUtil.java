package com.zxczone.littlefox.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String getTrainNum(String str){
		String result = "";
		
		if (str != null) {
			String pattern = "[GDCZTKL]{0,1}[0-9]{1,5}";
			Pattern r = Pattern.compile(pattern);
	        Matcher m = r.matcher(str);
	        if (m.find()) {
	        	result = m.group(0);
	        }
	    }
        
        return result;
	}
}
