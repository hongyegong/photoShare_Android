package com.myou.appclient.util.file;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Title: 爱花族网站平台<br>
 * Description: 上传文件<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: MYOU<br>
 * 
 * @version 1.0
 */
public class UpFileUtils {

	/** 新截图上传地址 */
	public final static String newJietuWeb = "http://58.221.66.11:8088/appUp";
    
    /**
     * 上传文件
     * @param bdFilePath 本地文件绝对地址
     * @param urlPath 服务器中的文件名 或者是文件夹路径(例如：up/ 就是 在uploadphoto 文件夹中 的up 文件中 传文件)
     * @param suffix 文件后缀 例如：.jpg
     * @return
     * @throws Exception 
     */
    public static String upFile(String bdFilePath, String urlPath, String suffix) throws Exception {
        HttpURLConnection connection = null;
        OutputStream out = null;
        InputStream in = null;
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File(bdFilePath);
            URL url = null;
            if(suffix == null || suffix.trim().equals("")){
                url = new URL(newJietuWeb + "?path=" + urlPath);
            }else{
                url = new URL(newJietuWeb + "?path=" + urlPath + "&suffix=" + suffix);
            }
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            connection.setFixedLengthStreamingMode((int)file.length());
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("POST");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.setConnectTimeout(10 * 1000);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            out = connection.getOutputStream();
            is = new FileInputStream(file);
            byte [] buffer = new byte[1048576];
            int size = is.read(buffer);
            while (size != -1) {
                out.write(buffer, 0, size);
                size = is.read(buffer);
            }
            out.flush();
            ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
            String path = (String) ois.readObject();
            System.out.println("文件上传完毕：" + path);
            return path;
        } catch (Exception e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            close(out);
            close(in);
            close(bos);
            close(is);
        }
    }
    
    private static void close(Closeable out) {
        try {
            if(out != null){
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
  public static void main(String[] args) {
      try {
		upFile("D://11.jpg", "" , ".jpg");
	} catch (Exception e) {
		e.printStackTrace();
	}
  	//upFile("flower/change/logo/more.jpg", "huaxun/jietu/moren.jpg");
  	//httpUploadJietu("flower/change/logo/moren.jpg", "huaxun/jietu/moren.jpg");
//  	File file = new File("flower/change/logo/moren.jpg");
//  	System.out.println(file.getPath());
  }
}
