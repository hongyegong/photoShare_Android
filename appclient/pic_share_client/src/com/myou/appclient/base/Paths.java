package com.myou.appclient.base;

import android.os.Environment;
import android.util.Log;

import com.myou.appclient.util.file.FileUtils;

/**
 * Title: 智旅app<br>
 * Description:  本地数据的路径<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class Paths {
	
	/** sd卡目录 */
	public final static String SDDIR = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	/** 花讯文件目录 */
	public final static String BASEDIR = SDDIR + "/zhilv/";
	
	/** 花讯图片基本目录 */
	public final static String BASEIMGDIR = BASEDIR + "image/";
	
	/** 花讯数据目录 */
	public final static String DATADIR = BASEDIR + "data/";
	
	/** 花讯签收图片目录 */
	public final static String IMAGESIGNDIR = BASEIMGDIR + "sign/";
	
	/** 花讯缓存图片目录 */
	public final static String IMAGETEMPDIR = BASEIMGDIR + "temp/";
	
	static{
		// 创建目录
		if(FileUtils.checkSaveLocationExists()){
			Log.d(Paths.class.getSimpleName(), "创建目录中...");
			FileUtils.createDirectory(Paths.BASEDIR);
			FileUtils.createDirectory(Paths.DATADIR);
			FileUtils.createDirectory(Paths.BASEIMGDIR);
			FileUtils.createDirectory(Paths.IMAGESIGNDIR);
			FileUtils.createDirectory(Paths.IMAGETEMPDIR);
			Log.d(Paths.class.getSimpleName(), "目录创建成功...");
		}
	}
	
}
