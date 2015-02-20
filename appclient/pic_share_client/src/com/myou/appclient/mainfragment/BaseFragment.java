package com.myou.appclient.mainfragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.myou.appclient.activity.R;
import com.myou.appclient.base.Paths;
import com.myou.appclient.base.RequestCode;
import com.myou.appclient.ui.AsyncImageLoader;
import com.myou.appclient.ui.AsyncImageLoader.ImageCallback;
import com.myou.appclient.util.ConnectionUtils;
import com.myou.appclient.util.ExceptionHandle;
import com.myou.appclient.util.UrlUtils;
import com.myou.appclient.util.file.FileUtils;
import com.myou.appclient.util.file.ImageUtils;
import com.myou.appclient.util.ui.UIUtils;

/**
 * Title: 智旅app<br>
 * Description:   Fragment基类<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {

	/** 类名 */
    protected static String LOGNAME = ExceptionHandle.LOGNAME;

	/** 拍照用的 */
    public String fileName = "";
	public File file = null;
	public String picType = ".jpg";
    public static final String IMAGE_UNSPECIFIED = "image/*";

    /** 加载界面 */
    private ProgressDialog progressDialog;
    /** 加载界面是否销毁 */
    private boolean destroyed = false;
    /** 加载界面的结果 */
    protected Object result;
    /** 加载界面的url */
    protected String url = UrlUtils.HOST;
    
    protected Activity activity;
    
    public BaseFragment(Activity activity){
    	this.activity = activity;
    }
    
    public void toPhone(){
    	final String[] items = new String[]{"拍照", "选择本地图片"};
    	showAlertDialog(items,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	if(which == 0){
        			phoneDO("");
            	}else{
            		phoneSelect();
            	}
            }
        });
    }
    
    /**
     * 具体的拍照
     * @param pkOrder
     */
    protected void phoneDO(String orderNo){
    	if(!FileUtils.checkSaveLocationExists()){
    		show("SD卡不存在，无法使用拍照服务");
    		return;
    	}
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	fileName = ImageUtils.getTempFileName() + ".jpg";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Paths.IMAGESIGNDIR, fileName)));
        startActivityForResult(intent, RequestCode.PHOTOHRAPH);
    }
    
	/**
	 * 回调
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RequestCode.NONE){
			return;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getPicType(Uri photoUri){
		String[] pojo = {MediaStore.Images.Media.DATA};  
		String picPath = null;
        Cursor cursor = activity.managedQuery(photoUri, pojo, null, null,null);     
        if(cursor != null )  
        {  
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);  
            cursor.moveToFirst();  
            picPath = cursor.getString(columnIndex);  try  
            {  
                //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)  
                if(Integer.parseInt(Build.VERSION.SDK) < 14)  
                {  
                    cursor.close();  
                }  
            }catch(Exception e)  
            {  
            }  
        }  
        picPath = picPath.substring(picPath.lastIndexOf(".") , picPath.length());
        Log.i("ceshi", "imagePath = "+picPath);  
        return picPath;
	}
	
	/**
	* 加载本地图片
	* http://bbs.3gstdy.com
	* @param url
	* @return
	 * @throws FileNotFoundException 
	*/
	public static Bitmap getLoacalBitmap(File file) throws FileNotFoundException {
	          FileInputStream fis = new FileInputStream(file);
	          return BitmapFactory.decodeStream(fis);
	}

/**
 * 获得字节
 * @param filePath
 * @return
 */
private byte[] getSelectedFileByte(File file){
	if(file == null) return null;
    InputStream is;
    byte[] body = null;
    try {
        is = new FileInputStream(file);
        body = new byte[(int)file.length()];
        is.read(body);
        is.close();
        return body;
    } catch (Exception e1) {
    	ExceptionHandle.handeException(activity, e1);
        return null;
    }
}


    public void phoneSelect() {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT); 
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, RequestCode.PHONESELECT);
	}
    
    
    
    
	protected void myCreate(Bundle savedInstanceState){
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			myCreate(savedInstanceState);
		} catch (Exception e) {
//			show("系统异常，请稍后重试。");
//			finish();
		}
	}

    
    public void showAlertDialog(String[] items,  DialogInterface.OnClickListener listener){
		new AlertDialog.Builder(activity).setTitle("温馨提示：请选择")
		                                .setItems(items, listener).show();
    }

	public void share(View v) {
		UIUtils.share(activity, "试试");
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
        }, activity);
    	imageView.setImageDrawable(cachedImage);
    }
    
    /**
     * 获得数据
     * @param xmlName
     * @return
     */
    protected SharedPreferences getSharedPreferences(String xmlName){
    	return activity.getSharedPreferences(xmlName, Activity.MODE_APPEND);
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
				.makeText(activity, String.valueOf(obj), (isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
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
            progressDialog = new ProgressDialog(activity);
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
			return BaseFragment.this.doInBackground(map, arg0);
		}
		
		@Override
		protected void onPostExecute(Object result1) {
			BaseFragment.this.onPostExecute(result1);
		}
		
		protected void runEndDo(Object obj){
			try {
				BaseFragment.this.runEndDo(obj);
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
		return ConnectionUtils.DO(activity, url, getMethodType(), map);
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
    
}
