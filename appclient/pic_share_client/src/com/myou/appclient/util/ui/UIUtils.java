package com.myou.appclient.util.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.myou.appclient.activity.R;

/**
 * Title: 爱花族网站平台<br>
 * Description: 和ui有关的常用的工具类，<br>
 * Copyright: Copyright (c) 2012<br>
 * Company: MYOU<br>
 * 
 * @author JJY
 * @version 1.0
 */
public class UIUtils {

	private static final String CLASSNAME = UIUtils.class.getSimpleName();
	
	/**
	 * 计算listview的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 查看是否联网
	 * @param context
	 * @return
	 */
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null){
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
			}
		}
		return false;
	}
	
	/**
	 * 调用系统的拨号服务实现电话拨打功能
	 * @param phoneNumber
	 */
	public static void call(Context context, String phoneNumber){
		try {
	        if(phoneNumber != null && !phoneNumber.trim().equals("")){
	            //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
	            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNumber.trim()));
	            context.startActivity(intent);
	        }
		} catch (Exception e) {
			Toast toast = Toast.makeText(context, "电话格式错误：" + phoneNumber, Toast.LENGTH_SHORT);
			toast.show();
			Log.e(CLASSNAME, e.getMessage(), e);
		}
	}

	/**
	 * 显示安装的界面
	 * @param context
	 * @param path
	 */
	public static void updateSoft(Activity context, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive"); 
		context.startActivity(intent);
		context.finish();
	}

	/**
	 * 调用系统的拨号服务实现电话拨打功能
	 * @param phoneNumber
	 */
	public static void sendSms(Context context, String phoneNumber){
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber)); 
		sendIntent.putExtra("sms_body", "【】"); 
		context.startActivity(sendIntent);
	}

	/**
	 * 调用系统分享按钮
	 */
	public static void share(Context context, String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		intent.putExtra(Intent.EXTRA_TEXT,  content);
		context.startActivity(Intent.createChooser(intent, "分享"));
	}
	

	/**
	 * 查看是否创建了快捷方式
	 * @param context
	 * @return
	 */
	public static boolean hasShortCut(Context context) {
		String url = "";
		System.out.println(getSystemVersion());
		if (getSystemVersion() < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
				new String[] { context.getString(R.string.app_name) }, null);

		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	/**
	 * 获得系统版本号
	 * @return
	 */
	public static int getSystemVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

}
