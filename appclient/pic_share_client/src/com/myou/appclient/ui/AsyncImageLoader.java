package com.myou.appclient.ui;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.myou.appclient.activity.R;
import com.myou.appclient.base.Paths;
import com.myou.appclient.util.ExceptionHandle;
import com.myou.appclient.util.StringUtils;
import com.myou.appclient.util.file.FileUtils;
import com.myou.appclient.util.file.ImageUtils;


/**
 * Title: 智旅app<br>
 * Description:   图片异步加载<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
@SuppressLint("HandlerLeak")
public class AsyncImageLoader {
	
	private final static String CLASSNAME = ExceptionHandle.LOGNAME;
	
	private static HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();  
   
    public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback, final Context context) {  
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
            }
        };
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl, context);  
                if(imageUrl.contains("_4444.") || !imageUrl.startsWith("http://58")){
                	imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));  
                }
                Message message = handler.obtainMessage(0, drawable);  
                handler.sendMessage(message);  
            }
        }.start();
        return null;
    }  

	/* 获得真正的URL */
	public static String getImgeUrl(String url) {
		String newUrl = null;
		if (url.startsWith(",")) {
			url = url.substring(1, url.length());
		}
		if (url.contains("http://")) {
			newUrl = url;
		} else if (url.contains("uploadphoto")) {
			newUrl = "http://tupian.kaihuadian.com/" + url;
		} else {
			newUrl = "http://tupian.kaihuadian.com/uploadphoto/6060" + url;
		}
		return newUrl;
	}

	@SuppressWarnings("deprecation")
	public static Drawable loadImageFromUrl(String url, Context context) {
		// 查看图片是否为 暂无图片，或者为空
		if(StringUtils.isEmpty(url) || url.contains("res/goodsAlter/noPic.png")){
//			return context.getResources().getDrawable(R.drawable.no_pic);
		}
		// 查询是否存在本地缓存
		String bdPath = Paths.IMAGETEMPDIR + FileUtils.getFileName(url);
		File file = new File(bdPath);
		if(file.isFile()){
			Log.i(CLASSNAME, "成功读取本地缓存..." + bdPath);
			return new BitmapDrawable(ImageUtils.getBitmapByFile(file));
		}
		// 处理4中网络图片格式，包括tupian.kaihuadian.com
		URL m;
		InputStream i = null;
		try {
			m = new URL(getImgeUrl(url));
			i = (InputStream) m.getContent();
			//向SD卡中写入图片缓存
			Drawable d = Drawable.createFromStream(i, "src");
			ImageUtils.saveImageToSD(bdPath, ((BitmapDrawable)d).getBitmap(), 100);
			Log.i(CLASSNAME, "文件保存成功..." + bdPath);
			return d;
		} catch (Exception e) {
			Log.e(CLASSNAME, "图片加载异常..." + url + "   " + e.getMessage(), e);
			// TODO:此处可放置异常图片
			return context.getResources().getDrawable(R.drawable.detailpic_no);
		} finally{
			FileUtils.close(i);
		}
	}

    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable, String imageUrl);  
    }  
}  