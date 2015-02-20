package com.myou.appclient.base;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.myou.appclient.activity.AboutActivity;
import com.myou.appclient.activity.AppAllActivity;
import com.myou.appclient.activity.FirstDetailActivity;
import com.myou.appclient.activity.LoginDialogActivity;
import com.myou.appclient.activity.MyPicActivity;
import com.myou.appclient.activity.ThirdDetailActivity;
import com.myou.appclient.activity.YjfkActivity;

/**
 * Title: 智旅app<br>
 * Description:  所有的activity跳转<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class ActivityManage {

	private static Stack<Activity> activityStack;

	/**
	 * 添加Activity到堆栈
	 */
	public static void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 结束指定的Activity
	 */
	public static void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}

	/**
	 * 结束所有Activity
	 */
	public static void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 退出应用程序
	 */
	public static void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
		} catch (Exception e) {
			
		}
	}

	/**
	 * 显示主界面
	 * @param activity
	 * @param orderType 
	 */
	public static void showHome(Activity activity) {
		Intent intent = new Intent (activity, AppAllActivity.class);
		activity.startActivity(intent);
		addActivity(activity);
    }
	
	/**
	 * 显示FirstDetailActivity
	 * @param activity
	 * @param id 
	 * @param orderType 
	 */
	public static void showFirstDetail(Activity activity, String id) {
		Intent intent = new Intent (activity, FirstDetailActivity.class);
		activity.startActivity(intent);
		addActivity(activity);
	}
	
	/**
	 * 显示FirstDetailActivity
	 * @param activity
	 * @param id 
	 * @param orderType 
	 */
	public static void showMyPic(Activity activity, String type) {
			Intent intent = new Intent (activity, MyPicActivity.class);
			intent.putExtra("type", type);
			activity.startActivityForResult(intent, RequestCode.ADDSHOPCARD);
	    }

	/**
	 * 显示添加登陆界面
	 * @param activity
	 */
	public static void showLoginDialog(Activity activity) {
		Intent intent = new Intent (activity,LoginDialogActivity .class);
		activity.startActivityForResult(intent, RequestCode.LOGIN);
		addActivity(activity);
	}
	
	/**
	 * 显示第3个详情界面
	 * @param activity
	 */
	public static void showThirdDetail(Activity activity) {
		Intent intent = new Intent (activity,ThirdDetailActivity.class);
		activity.startActivityForResult(intent, RequestCode.LOGIN);
		addActivity(activity);
	}

	/**
	 * @param activity
	 */
	public static void showYjfk(Activity activity){
		Intent intent = new Intent(activity, YjfkActivity.class);
		activity.startActivity(intent);
		addActivity(activity);
	}
	
	/**
	 * @param activity
	 */
	public static void showAbout(Activity activity){
		Intent intent = new Intent(activity, AboutActivity.class);
		activity.startActivity(intent);
		addActivity(activity);
	}
}
