package com.myou.appclient.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.myou.appclient.activity.R;
import com.myou.appclient.ui.CustomDialog;

/**
 * Title: 智旅app<br>
 * Description:  封装了一些dialog的activity<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class DialogActivity extends Activity {

	/** 客户端的icon */
	public final static int BASEICON = R.drawable.ic_launcher;
	
	/**
	 * 简单的提示信息
	 */
	public void show(int id) {
		show(id, false);
	}
	public void show(Object obj) {
		show(obj, false);
	}
	public void show(int id, boolean isLong) {
		try {
			show(getString(id), isLong);
		} catch (Exception e) {
			show(id + "", isLong);
		}
	}
	public void show(Object obj, boolean isLong) {
		Toast toast = Toast
				.makeText(DialogActivity.this, String.valueOf(obj), (isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
		toast.show();
	}
	
	/**
	 * 有输入框的弹出框
	 * @param text
	 * @param posListener
	 * @param negListener
	 */

	public void show_TEXT_DLG(String text, 
			DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener){
		show_TEXT_DLG(text, "确定", "取消", posListener, negListener);
	}
	public void show_TEXT_DLG(String text, String posText, String negText, 
			DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener){
		new AlertDialog.Builder(this)
		.setTitle(text)
		.setIcon(android.R.drawable.ic_dialog_info)
		.setView(new EditText(this))
		.setPositiveButton(posText, posListener)
		.setNegativeButton(negText, negListener)
		.show();
	}

	/**
	 * 有按钮的提示界面
	 * @param buttonText
	 * @param message
	 * @param posListener
	 */
	public void show_MSG_DLG(String buttonText, String message,  OnClickListener posListener) {
		CustomDialog dialog = new CustomDialog(DialogActivity.this);
		dialog.createAlert(message, BASEICON, buttonText, posListener);
		dialog.show();
	}
	public void show_MSG_DLG(String message, OnClickListener posListener) {
		show_MSG_DLG("确定", message, posListener);
	}

	/**
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (Drawable)图标资源
	 * @param PosButtonText
	 *            确定按钮文字，不设置默认为 “确定”， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param NegButtonText
	 *            取消按钮文字，不设置默认为 "取消"， 如果没有 “取消” 按钮，则将此参数设置为：null
	 * @param posListener
	 *            确定按钮响应事件
	 * @param negListener
	 *            取消按钮相应事件
	 */
	public void show_YES_NO(String message, String title, int icon,
			String PosButtonText, String NegButtonText,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		CustomDialog dialog = new CustomDialog(DialogActivity.this);
		dialog.initTitle(title);
		dialog.createAlert(message, icon, PosButtonText, NegButtonText, posListener, negListener);
		dialog.show();
	}

	/**
	 * @param message
	 *            显示的主题内容
	 * @param title
	 *            标题
	 * @param posListener
	 *            确定按钮响应事件
	 * @param negListener
	 *            取消按钮相应事件
	 */
	public void show_YES_NO(String message, String title, 
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		show_YES_NO(message, title, BASEICON, getString(R.string.dialog_yes_msg), getString(R.string.dialog_no_msg), posListener, negListener);
	}
	
	/**
	 * @param message
	 *            显示的主题内容
	 * @param posListener
	 *            确定按钮响应事件
	 * @param negListener
	 *            取消按钮相应事件
	 */
	public void show_YES_NO(String message, 
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		show_YES_NO(message, getString(R.string.dialog_base_title), posListener, negListener);
	}

}
