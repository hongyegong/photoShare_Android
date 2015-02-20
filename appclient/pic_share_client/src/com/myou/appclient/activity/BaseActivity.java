package com.myou.appclient.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myou.appclient.base.Commons;
import com.myou.appclient.base.RequestCode;
import com.myou.appclient.ui.AsyncImageLoader;
import com.myou.appclient.ui.AsyncImageLoader.ImageCallback;
import com.myou.appclient.ui.CustomDialog;
import com.myou.appclient.util.ConnectionUtils;
import com.myou.appclient.util.ExceptionHandle;
import com.myou.appclient.util.StringUtils;
import com.myou.appclient.util.UrlUtils;
import com.myou.appclient.util.ui.UIUtils;

/**
 * Title: 智旅app<br>
 * Description: Activity基类<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
/**
 * @author Administrator
 *
 */
public abstract class BaseActivity extends FragmentActivity {

	/** 类名 */
    protected static String LOGNAME = ExceptionHandle.LOGNAME;
    
    /** 加载界面 */
    private ProgressDialog progressDialog;
    /** 加载界面是否销毁 */
    private boolean destroyed = false;
    /** 加载界面的结果 */
    protected Object result;
    /** 加载界面的url */
    protected String url = UrlUtils.HOST;
	public String urlType = "";

	protected abstract void myCreate(Bundle savedInstanceState);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			myCreate(savedInstanceState);
		} catch (Exception e) {
//			show("系统异常，请稍后重试。");
//			finish();
		}
	}
	
	/**
	 * 回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RequestCode.EWM  && data!= null &&  data.getExtras() != null) {
			String orderNo = data.getExtras().getString("orderNo");
			String capture = data.getExtras().getString("capture");
			if(StringUtils.isEmpty(Commons.getInstance().getUserPk())){
				show("二维码扫描有效，请登陆后查询是否可以使用");
				return;
			}
			urlType = "ewm";
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("content", capture);
	    	map.put("id", orderNo);
	    	map.put("member", Commons.getInstance().getUserPk());
	        run("usercoupon.ashx", map);
		}
		 
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void to_phone(View v) {
		String str = ((TextView)v).getText().toString().replaceAll("电话：", "");
		UIUtils.call(this, str);
	}
    
	public void detailButtonDo( int i2){
		detailButtonDo(R.id.share_btn_wrap, i2);
	}

	public void detailButtonDo(int i1, int i2){
		detailButtonDo(i1, i2, 0);
	}

    public void showAlertDialog(String[] items,  DialogInterface.OnClickListener listener){
		new AlertDialog.Builder(this).setTitle("温馨提示：请选择")
		                                .setItems(items, listener).show();
    }
    
	public void detailButtonDo(int i1, int i2, int i3){
		View old1View = (View) findViewById(R.id.share_btn_wrap);
		old1View.setVisibility(8);
		View i1View = (View) findViewById(i1);
		View i2View = (View) findViewById(i2);
		i1View.setVisibility(0);
		i2View.setVisibility(0);
		if(i3 != 0){
			View view = (View) findViewById(i3);
			view.setVisibility(0);
		}
	}
	
    public void addBackClick(){
    	View ab_back = findViewById(R.id.ab_back);
    	ab_back.setVisibility(0);
    }

    public void addShare(){
    	View ab_back = findViewById(R.id.title_share);
    	ab_back.setVisibility(0);
    	View title_search = findViewById(R.id.title_search);
    	title_search.setVisibility(8);
    }

    public void addSearch(){
    	View ab_back = findViewById(R.id.title_search);
    	ab_back.setVisibility(0);
    }
    
    public void ab_back(View v){
    	finish();
    }

	public void share(View v) {
		UIUtils.share(this, "试试");
	}

	public void setText(int id, String text){
		TextView  textview = (TextView) findViewById(id);
		textview.setText(text);
	}
	
    /**
     * 设置图片
     * @param imageView
     * @param value
     */
    public void setImage(final ImageView imageView, String value){
    	AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
    	imageView.setTag(value);
    	Drawable cachedImage = asyncImageLoader.loadDrawable(value, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) imageView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        }, this);
    	imageView.setImageDrawable(cachedImage);
    }
    
    /**
     * 获得数据
     * @param xmlName
     * @return
     */
    protected SharedPreferences getSharedPreferences(String xmlName){
    	return this.getSharedPreferences(xmlName, Activity.MODE_APPEND);
    }
    protected SharedPreferences getSharedPreferences(){
    	return getSharedPreferences("typeInfo");
    }
    
    /**
	 * 保存数据
	 * @param xmlName
	 * @param key
	 * @param value
	 * @return
	 */
	protected Editor getEditor(String xmlName){
		return getSharedPreferences(xmlName).edit();
    }
	protected Editor getEditor(){
		return getSharedPreferences("typeInfo").edit();
    }
	

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
				.makeText(this, String.valueOf(obj), (isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
		toast.show();
	}
	
    /**
     * 稍微简单的封装一下 task
     */
    public void showLoadingProgressDialog() {
        this.showProgressDialog(getString(R.string.loading2));
    }
    
	@Override
	public void onDestroy() {
		super.onDestroy();
		destroyed = true;
	}

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

	protected void runEndDo(Object result) {
		
	}

    public String getUrl() {
		return url;
	}
    
	/**
	 * @param path 路径 (想见枚举ServletType)
	 * @param map 请求的参数
	 */
	public void run(String type, Map<String, Object> map) {
		run(type, map, true);
	}
    
    public void run(String type, Map<String, Object> map, boolean isShowLoading){
		new BaseTask(isShowLoading, UrlUtils.putEnd(type, map)).execute("");
    }

	public Object getResult() {
		return result;
	}

	
    public class BaseTask extends AsyncTask<Object, Object, Object> {
    	
    	boolean isShowLoading;
    	protected Map<String, Object> map;

		public BaseTask(boolean isShowLoading, Map<String, Object> map) {
			this.isShowLoading = isShowLoading;
			this.map = map;
		}

		@Override
		protected void onPreExecute() {
			if(isShowLoading){
				showLoadingProgressDialog();
			}
		}

		@Override
		protected Object doInBackground(Object... arg0) {
			return BaseActivity.this.doInBackground(map, arg0);
		}
		
		@Override
		protected void onPostExecute(Object result1) {
			BaseActivity.this.onPostExecute(result1);
		}
		
		protected void runEndDo(Object obj){
			try {
				BaseActivity.this.runEndDo(obj);
			} catch (Exception e) {
				Log.e(LOGNAME,  e.getMessage(), e);
			}
		}
	}

    public String getMethodType(){
    	return "GET";
    }
    
    public void noRunDo(String type, Map<String, Object> map){
    	onPostExecute(doInBackground(UrlUtils.putEnd(type, map), "")) ;
    }
    
    public Object doInBackground(Map<String, Object> map, Object... arg0) {
		return ConnectionUtils.DO(this, url, getMethodType(), map);
	}
    
    public void onPostExecute(Object result1) {
		if(result1 == null){
			show(R.string.dialog_net_err_msg);
		// 如果是 业务异常，显示异常msg
		}else if(result1.equals("0")){
			show("服务器异常，请稍后重试！");
		}else if(result1.toString().startsWith("服务器返回状态")){
			show(result1 + "  " + getResources().getString(R.string.dialog_net_err_msg));
		}else{
			// 处理真正的空数据
			Log.i(LOGNAME, "请求数据成功：" + url + "，数据：" + result1);
			result = result1;
			runEndDo(result1);
		}
		dismissProgressDialog();
	}
	
    /**
     * 是否显示退出提示 默认不显示
     * @return
     */
    public boolean isShowEsc(){
    	return false;
    }
    
    /** 客户端的icon */
	public final static int BASEICON = R.drawable.ic_launcher;
	
	
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
		CustomDialog dialog = new CustomDialog(BaseActivity.this);
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
		CustomDialog dialog = new CustomDialog(BaseActivity.this);
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
