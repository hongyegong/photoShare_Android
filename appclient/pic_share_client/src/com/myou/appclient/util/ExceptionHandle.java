package com.myou.appclient.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * @description： 统一处理界面异常（打印错误堆栈信息或提示信息）<br>
 * @author： jiujiya
 * @update： 2013-4
 * @version： 1.0
 * @email：136336790@qq.com
 */
public class ExceptionHandle {

	/** 日志tag */
	public final static String LOGNAME = "zhilv";
	
	/**
	 * 统一处理异常
	 * 
	 * @param e1
	 */
	public static void handeException(Context context, Throwable e) {
		try {
			final ByteArrayOutputStream bout = new ByteArrayOutputStream(200);
			e.printStackTrace(new PrintStream(bout));
			Toast.makeText(context, bout.toString(), Toast.LENGTH_LONG).show();
			Thread.sleep(3000);
			Toast.makeText(context, bout.toString(), Toast.LENGTH_LONG).show();
			Thread.sleep(3000);
			Toast.makeText(context, bout.toString(), Toast.LENGTH_LONG).show();
			Thread.sleep(3000);
			Toast.makeText(context, bout.toString(), Toast.LENGTH_LONG).show();
			Log.e(LOGNAME, e.getMessage(), e);
		} catch (Exception e2) {
		}
	}
}
