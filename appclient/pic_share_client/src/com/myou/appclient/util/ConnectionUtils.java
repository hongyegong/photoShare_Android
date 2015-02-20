package com.myou.appclient.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.myou.appclient.util.file.FileUtils;

/**
 * Title: 爱花族网站平台<br>
 * Description: 手机调用后台<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: MYOU<br>
 * 
 * @version 1.0
 */
public class ConnectionUtils {

    /** baseurl */
    public final static String HOST = "http://58.221.66.11:8088/app/";
    
    public static void main(String[] args) {
    	
    }
    
    
    /**
     * 提交一个post请求，并获得请求的返回值
     * @return
     */
    public static Object doPost(Context context, String type, Map<String, Object> map) {
        return DO(context, type, "POST", map);
    }
    
    /**
     * 提交一个post请求，并获得请求的返回值
     * @return
     */
    public static Object doGet(Context context, String type, Map<String, Object> map) {
        return DO(context, type, "GET", map);
    }
    
    /**
     * 添加后台必须传输的数据 
     * @param type
     * @return
     */
    private static Map<String, Object> putEnd(String type, Map<String, Object> map) {
        if(map == null){
            map = new HashMap<String, Object>();
        }
        return map;
    }

    public static Object DO(Context context, String type, String postType, Map<String, Object> map){
        return BaseDO(context, HOST, postType, putEnd(type, map));
    }
    
    public static Object BaseDO(Context context, String weburl, String postType, Map<String, Object> map){
    	HttpURLConnection connection = null;
        ObjectInputStream ois = null;
        OutputStreamWriter out = null;
        ObjectOutputStream oos = null;
        postType = "POST";
        try {
		     String args = "";
        	if(postType.equals("POST")){
        		args =UrlUtils.addURLArgus(weburl, map, false);
        		weburl = weburl + map.get("app_base_type");
//            	FileUtils.writeFileToSD("尝试POST请求：" + weburl + "，参数：" + args);
      			 Log.i(ExceptionHandle.LOGNAME, "尝试POST请求：" + weburl + "，参数：" + args);
        	}else{
        		args =UrlUtils.formatURL(weburl, map);
        		weburl = weburl + map.get("app_base_type") + "?" + args;
            	FileUtils.writeFileToSD("尝试GET请求：" + weburl);
      			 Log.i(ExceptionHandle.LOGNAME, "尝试GET请求：" + weburl);
        	}
            URL url = new URL(weburl);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();  
		     connection.setDoOutput(true);// 打开写入属性  
		     connection.setDoInput(true);// 打开读取属性  
		     connection.setRequestMethod(postType);// 设置提交方法  
		     
		     connection.setUseCaches(false);
		     connection.setInstanceFollowRedirects(true);
		     connection.setConnectTimeout(50000 * 2);// 连接超时时间  
		     connection.setReadTimeout(50000);
		     connection.connect();  

         	if(postType.equals("POST")){
		         out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");  
	   		     out.write(args);
	   		     out.flush(); 
	   		     out.close(); 
         	}
		     
            if (200 == connection.getResponseCode()) {  
                InputStream instream = connection.getInputStream();  
                byte[] data = read(instream);  
                String jsonStr = new String(data); 
                return jsonStr;
            } else{
            	return "服务器返回状态：" + connection.getResponseCode();
            }
        } catch (Exception e) {
        	FileUtils.writeFileToSD(e.toString());
        	Log.e(ExceptionHandle.LOGNAME, e.getMessage(), e);
            return null;
        } finally {
            close(out);
            close(oos);
            close(ois);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /** 
     * 读取输入流为byte[]数组 
     */  
    public static byte[] read(InputStream instream) throws IOException {  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while ((len = instream.read(buffer)) != -1)  
        {  
            bos.write(buffer, 0, len);  
        }  
        return bos.toByteArray();  
    }  
    
    public static void close(Closeable out) {
        try {
            if(out != null){
                out.close();
            }
        } catch (IOException e) {
        }
    }
    
}
