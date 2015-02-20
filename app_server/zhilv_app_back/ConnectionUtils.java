package com.test;

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


/**
 * Title: ��������վƽ̨<br>
 * Description: �ֻ����ú�̨<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: MYOU<br>
 * 
 * @version 1.0
 */
public class ConnectionUtils {

    /** baseurl */
    public final static String HOST = "http://127.0.0.1:8088/"; 
    
    public static void main(String[] args) {
    	Map<String, Object> map = new HashMap<String, Object>();

    	map.put("name","�Ƽ��ǰ�");
    	map.put("password","201002");
    	Object o = doPost("app/login", map);
    	System.out.println(o);
		Map<String, Object> list = JsonUtils.getMap((String) o);
		System.out.println(list);
	}
    
    /**
     * �ύһ��post���󣬲��������ķ���ֵ
     * @return
     */
    public static Object doPost(String type, Map<String, Object> map) {
        return DO(type, "POST", map);
    }
    
    /**
     * �ύһ��post���󣬲��������ķ���ֵ
     * @return
     */
    public static Object doGet(String type, Map<String, Object> map) {
        return DO(type, "GET", map);
    }
    
    /**
     * ��Ӻ�̨���봫������� 
     * @param type
     * @return
     */
    private static Map<String, Object> putEnd(String type, Map<String, Object> map) {
        if(map == null){
            map = new HashMap<String, Object>();
        }
		map.put("app_base_type", type);
		map.put("app_base_pkUser", "");
        return map;
    }

    private static Object DO(String type, String postType, Map<String, Object> map){
        return BaseDO(HOST, postType, putEnd(type, map));
    }
    
    public static Object BaseDO(String weburl, String postType, Map<String, Object> map){
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
        		System.out.println(args);
        	}else{
        		args =UrlUtils.formatURL(weburl, map);
        		weburl = weburl + map.get("app_base_type") + "?" + args;
        	}
            URL url = new URL(weburl);
            // �½�����ʵ��
            connection = (HttpURLConnection) url.openConnection();  
		     connection.setDoOutput(true);// ��д������  
		     connection.setDoInput(true);// �򿪶�ȡ����  
		     connection.setRequestMethod(postType);// �����ύ����  
		     
		     connection.setUseCaches(false);
		     connection.setInstanceFollowRedirects(true);
		     connection.setConnectTimeout(50000 * 2);// ���ӳ�ʱʱ��  
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
            	return "����������״̬��" + connection.getResponseCode();
            }
        } catch (Exception e) {
        	e.printStackTrace();
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
     * ��ȡ������Ϊbyte[]���� 
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
