package com.myou.appclient.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.google.gson.stream.JsonReader;

/**
 * @description： 解析json<br>
 * @author： jiujiya
 * @update： 2013-4
 * @version： 1.0
 * @email：136336790@qq.com
 */
public class JsonUtils {
	
	/**
	 * 统一处理异常
	 * @param e
	 */
	public static void hiddleException(Exception e){
		e.printStackTrace();
		Log.e(ExceptionHandle.LOGNAME, e.getMessage(), e);
	}
	
	/**
	 * 把 json 转换成 list
	 * @param jsonData
	 * @return
	 */
	public static List<Map<String, Object>> getList(String jsonData){
            //如果需要解析JSON数据，首要要生成一个JsonReader对象
            JsonReader reader = getJsonReader((jsonData));
//        	try {
//				reader.beginObject();
//            	reader.nextName();
//			} catch (IOException e) {
//				hiddleException(e);
//			}
            return getList(reader);
	}

	/**
	 * 把 json 转换成 map
	 * 
	 * @param jsonData
	 * @return
	 */
	public static Map<String, List<Map<String, Object>>> getMapList(String jsonData, int size) {
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String,Object>>>();
		//如果需要解析JSON数据，首要要生成一个JsonReader对象
		JsonReader reader = getJsonReader((jsonData));
		try {
			reader.beginObject();
		} catch (IOException e) {
			hiddleException(e);
		}
		for (int i = 0; i < size; i++) {
			try {
				String name = reader.nextName();
				map.put(name, getList(reader));
			} catch (IOException e) {
				hiddleException(e);
			}
		}
    	return map;
	}
	
	/**
	 * 把 json 转换成 list
	 * @param reader
	 * @return
	 */
	public static List<Map<String, Object>> getList(JsonReader reader){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
            reader.beginArray();
            while(reader.hasNext()){
                list.add(getMap(reader));
            }
            reader.endArray();
        }
        catch(Exception e){
            hiddleException(e);
        }
		return list;
	}
	
	/**
	 * 获得JsonReader
	 * @param jsonData
	 * @return
	 */
	public static JsonReader getJsonReader(String jsonData){
		// 如果需要解析JSON数据，首要要生成一个JsonReader对象
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		return reader;
	}
	
	/**
	 * 把 json 转换成 map
	 * 
	 * @param jsonData
	 * @return
	 */
	public static Map<String, Object> getMap(String jsonData) {
		// 如果需要解析JSON数据，首要要生成一个JsonReader对象
		JsonReader reader = getJsonReader(jsonData);
		return getMap(reader);
	}

	/**
	 * 把 json 转换成 map
	 * @param reader
	 * @return
	 */
	public static Map<String, Object> getMap(JsonReader reader) {
		Map<String, Object> map = new HashMap<String, Object>();
		String tagName = "";
		try {
			// 如果需要解析JSON数据，首要要生成一个JsonReader对象
			reader.beginObject();
			while (reader.hasNext()) {
				tagName = reader.nextName();
				map.put(tagName, EscapeUnescape.unescape(reader.nextString()));
			}
			reader.endObject();
		} catch (Exception e) {
			hiddleException(e);
		}
		return map;
	}

}
