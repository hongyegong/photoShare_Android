package com.myou.appclient.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description： 统一处理url<br>
 * @author： jiujiya
 * @update： 2013-4
 * @version： 1.0
 * @email：136336790@qq.com
 */
public class UrlUtils implements Serializable {
	
	/**
	 * 序列号 
	 */
	private static final long serialVersionUID = -4502193483183066187L;
	
	/** baseurl */
	public final static String HOST = ConnectionUtils.HOST;

	/**
	 * 添加后台必须传输的数据 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> putEnd(String type, Map<String, Object> map) {
		if(map == null){
			map = new HashMap<String, Object>();
		}
		map.put("app_base_type", type);
//		map.put("app_base_pkUser", Commons.getInstance().getUserPk());
//		map.put("app_base_version", Commons.getInstance().getVersion());
		return map;
	}
	
	
	/**
	 * 对URL进行格式处理 前面加http://， 后面加用户pk和 花店pk
	 * @param path
	 * @return
	 */
	public static String formatURL(String path) {
		return formatEndURL(addURLArgus(path, null));
	}
	
	/**
	 * 对URL进行格式处理 前面加http://， 后面加用户pk和 花店pk
	 * @param path
	 * @return
	 */
	public static String formatURL(String path, Map<String, Object> argus) {
		return formatEndURL(addURLArgus(path, argus));
	}
	
	/**
	 * 对URL进行格式处理（前面加http://）
	 * @param path
	 * @return
	 */
	public final static String formatStartURL(String path) {
		if(path.startsWith("http://") || path.startsWith("https://"))
			return path;
		if(path.endsWith("/")){
			path = path.substring(0, path.length() - 1);
		}
		return HOST + path;
	}
	
	/**
	 * 对URL进行格式处理（后面加用户pk和 花店pk） 
	 * @param path
	 * @return
	 */
	private final static String formatEndURL(String path) {
		Map<String, Object> map = new HashMap<String, Object>();
		return addURLArgus(path, map);
	}

	/**
	 * 对URL进行格式处理 添加参数,前面加http://
	 * @param path
	 * @return
	 */

	public static String addURLArgus(String path, Map<String, Object> argus1) {
		return addURLArgus(path, argus1, true);
	}
	
	public static String addURLArgus(String path, Map<String, Object> argus1, boolean isEscape) {
		// 前面加http://
		Map<String, Object> argus = new HashMap<String, Object>();
		argus.putAll(argus1);
//		path = formatStartURL(path);
		if(argus == null || argus.size() == 0) return path;
//		path = path + argus.get("app_base_type");
		argus.remove("app_base_type");
		if(path.contains("=")){
			path += "&";
		}else if(!path.endsWith("?")){
			path += "?";
		}
		String argusUrl = "";
        for(Map.Entry<String, Object> entry: argus.entrySet()){
        	if(entry.getValue() != null){
        		if(isEscape){
        			argusUrl += "&" + entry.getKey() + "=" + EscapeUnescape.escape(entry.getValue() + "");
        		}else{
        			argusUrl += "&" + entry.getKey() + "=" + entry.getValue();
        		}
        	}
        }
        if(argusUrl.equals("")){
        	path = "";
        }else{
        	path = argusUrl.substring(1, argusUrl.length());
        }
		return path;
	}


	public static String getImageUrl(String string) {
		if(!string.startsWith("http://tallapp.com")){
			return "http://tallapp.com/" +  string;
		}
		return string;
	}

}
