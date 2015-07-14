package org.apache.nutch.jabong;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;


public class JUtil {
	
	
	public static String getValueByNameSpace(Configuration conf,String key){
		return getValue(conf, key, JabongKey.NAMESPACE);
	}
	
	public static String getValue(Configuration conf,String nutchKey,String key){
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(conf.get(key))){
			sb.append(conf.get(key)).append(".");
		}
		sb.append(nutchKey);
		return conf.get(sb.toString());
	}
	
	
	public static boolean isValidType(String type){
		try{
			JabongKey.TYPE_ITEM.valueOf(type.toUpperCase());
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}

}
