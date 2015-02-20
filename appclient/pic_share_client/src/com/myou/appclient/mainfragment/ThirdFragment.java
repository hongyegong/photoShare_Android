package com.myou.appclient.mainfragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myou.appclient.activity.R;
import com.myou.appclient.base.ActivityManage;
import com.myou.appclient.base.Commons;
import com.myou.appclient.base.KeyNames;
import com.myou.appclient.base.Paths;
import com.myou.appclient.base.RequestCode;
import com.myou.appclient.util.ConnectionUtils;
import com.myou.appclient.util.ExceptionHandle;
import com.myou.appclient.util.JsonUtils;
import com.myou.appclient.util.MD5;
import com.myou.appclient.util.StringUtils;
import com.myou.appclient.util.file.ImageUtils;
import com.myou.appclient.util.file.UpFileUtils;

/**
 * Title: 智旅app<br>
 * Description:   第4个界面<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
@SuppressLint("ValidFragment")
public class ThirdFragment extends BaseFragment {

	RelativeLayout login_wrap;
	RelativeLayout zhuce_wrap;
	RelativeLayout info__wrap;

	/** 用户名，密码，是否保存密码 组件  */
    private CheckBox isSavePs;
    private EditText email;
    private EditText password;
    private EditText password2;
    
	String buttonText = "";
	String emailText;
	
	public ThirdFragment(Activity activity) {
		super(activity);
	}

    
	public void memberDO(View view){
		if(view.getId() == R.id.icon_layout){
			toPhone();
			return;
		}else if(view.getId() == R.id.my_share){
			ActivityManage.showMyPic(activity, "myAdd");
			return;
		}else if(view.getId() == R.id.my_shoucang){
			ActivityManage.showMyPic(activity, "mySc");
			return;
		}
		TextView textView = (TextView) view;
		buttonText = textView.getText().toString().trim();
		if(buttonText.equals("登陆")){
			emailText = email.getText().toString();
			String passwordText = password.getText().toString();
			if(StringUtils.isEmpty(emailText) || StringUtils.isEmpty(passwordText)){
				show("用户名或密码不能为空");
			}else{
				Map<String, Object> argus = new HashMap<String, Object>();
				argus.put("name", emailText);
				argus.put("password", MD5.MD5(passwordText));
				run("login", argus);
			}
		}else if(buttonText.equals("新用户注册")){
			changeView(zhuce_wrap);
	        isSavePs = (CheckBox) zhuce_wrap.findViewById(R.id.isSavePs);
			email = (EditText) zhuce_wrap.findViewById(R.id.loginEmail);
			password = (EditText) zhuce_wrap.findViewById(R.id.loginPassword);
			password2 = (EditText) zhuce_wrap.findViewById(R.id.loginPassword2);
		}else if(buttonText.equals("马上注册")){
			zhuce();
		}else if(buttonText.equals("返回登陆界面")){
			toLogin();
		}
	}
	
	public String getMyText(int id){
		EditText text = (EditText) zhuce_wrap.findViewById(id);
		return text.getText().toString() + "";
	}
	
	public void zhuce(){
		emailText = email.getText().toString();
		String passwordText = password.getText().toString();
		String password2Text = password2.getText().toString();
		String truename = getMyText(R.id.truename);
		if(StringUtils.isEmpty(emailText) || StringUtils.isEmpty(passwordText) || StringUtils.isEmpty(password2Text)){
			show("用户名或密码不能为空");
		}else if(!passwordText.equals(password2Text)){
			show("两次密码输入不一致");
		}else{
			Map<String, Object> argus = new HashMap<String, Object>();
			argus.put("name", emailText);
			argus.put("password", MD5.MD5(passwordText));
			argus.put("truename", truename);
			buttonText = "zhuce";
			run("register", argus);
		}
	}
	
	public void toLogin(){
		if(login_wrap != null){
	        isSavePs = (CheckBox) login_wrap.findViewById(R.id.isSavePs);
			email = (EditText) login_wrap.findViewById(R.id.loginEmail);
			password = (EditText) login_wrap.findViewById(R.id.loginPassword);
			changeView(login_wrap);
		}
	}
	
	@Override
	protected void runEndDo(Object result) {
		if(url.equals("uphead")){
			Map<String, Object> map = JsonUtils.getMap((String) result);
			String str = (String)map.get("return");
			if(str != null && str.equals("0")){
				show("头像修改成功！");
			}else{
				show("头像上传失败，请稍后重试！");
			}
			return;
		}
		if(buttonText.equals("登陆")){
			Map<String, Object> map = JsonUtils.getMap((String) result);
			String str = (String)map.get("return");
			if(str.equals("0")){
				Commons.getInstance().setUserPk((String)map.get("PK_MEMBER"));
		        saveData();
				changeView(info__wrap);
				Commons.getInstance().setUserName((String) map.get("NICK_NAME"));
				
				TextView txt_nc = (TextView) info__wrap.findViewById(R.id.txt_nc);
				txt_nc.setText("昵称：" + Commons.getInstance().getUserName());
				
				TextView name = (TextView) info__wrap.findViewById(R.id.txt_name);
				name.setText("账户：" + emailText);
				
				// 设置头像
				final ImageView imageView = (ImageView) info__wrap.findViewById(R.id.icon_layout);
				final String value = (String) map.get("LOGO_PATH");
				setImage(imageView, value);
			}else{
				show("用户名不存在或者是用户名密码错误");
			}
		}else if(buttonText.equals("马上注册")){
	        saveData();
			changeView(info__wrap);
		}else if(buttonText.equals("zhuce")){
			Map<String, Object> map = JsonUtils.getMap((String) result);
			String str = (String)map.get("return");
			if(str != null && str.equals("0")){
				Commons.getInstance().setUserPk((String)map.get("PK_MEMBER"));
		        saveData();
				changeView(info__wrap);
				Commons.getInstance().setUserName((String) map.get("NICK_NAME"));
				
				TextView txt_nc = (TextView) info__wrap.findViewById(R.id.txt_nc);
				txt_nc.setText("昵称：" + Commons.getInstance().getUserName());
				
				TextView name = (TextView) info__wrap.findViewById(R.id.txt_name);
				name.setText("账户：" + emailText);
			}else{
				show(map.get("errorMsg"));
			}
		}
	}
	
	public void changeView(RelativeLayout layout){
		login_wrap.setVisibility(8);
		zhuce_wrap.setVisibility(8);
		info__wrap.setVisibility(8);
		layout.setVisibility(0);
		
		// 初始化数据
		if(login_wrap.getVisibility() == 0){
			
		}else if(info__wrap.getVisibility() == 0){
		}
	}

    /**
     * 保存最后登录的用户。
     */
    private void saveData(){
    	Editor deitor = getEditor();
    	deitor.putString(KeyNames.LASTUSEREMAIL, email.getText().toString());
    	deitor.putString(KeyNames.LASTUSERPASSWORD, password.getText().toString());
    	if(isSavePs != null){
    		deitor.putBoolean(KeyNames.ISSAVEPASSWORD, isSavePs.isChecked());
    	}
    	deitor.commit();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fourth_layout,container,false);

		TextView title = (TextView) view.findViewById(R.id.anzhi);
		title.setText("会员信息");
		
		login_wrap = (RelativeLayout) view.findViewById(R.id.login_wrap);
		zhuce_wrap = (RelativeLayout) view.findViewById(R.id.zhuce_wrap);
		info__wrap = (RelativeLayout) view.findViewById(R.id.info__wrap);
		
        isSavePs = (CheckBox) login_wrap.findViewById(R.id.isSavePs);
		email = (EditText) login_wrap.findViewById(R.id.loginEmail);
		password = (EditText) login_wrap.findViewById(R.id.loginPassword);
		
		// 如果上次登录成功，设置上次登录的值。
		SharedPreferences preferences = getSharedPreferences();

		if(preferences.contains(KeyNames.LASTUSEREMAIL)){
			// 设置 email。
			email.setText(preferences.getString(KeyNames.LASTUSEREMAIL, ""));

			// 设置 密码。
			isSavePs.setChecked(preferences.getBoolean(KeyNames.ISSAVEPASSWORD, true));
			if(isSavePs.isChecked()){
				password.setText(preferences.getString(KeyNames.LASTUSERPASSWORD, ""));
			}
		}
		
		// 添加是否保存的提示信息
		isSavePs.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton btn, boolean value) {
				show("登陆后自动保存");
			}
		});
		
		return view;
	}
	
	/**
	 * 显示缩放的activity
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri, File file) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("return-data", false);
		intent.putExtra("noFaceDetection", true);

		intent.putExtra("output", Uri.fromFile(file));
		startActivityForResult(intent, RequestCode.PHOTORESOULT);
	}
	
	/**
	 * 回调
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RequestCode.NONE){
			return;
		}
		// 拍照
				if (requestCode == RequestCode.PHOTOHRAPH) {
					// 获得随机的文件名
					String name = ImageUtils.getTempFileName() + ".jpg";
					file = new File(Paths.IMAGESIGNDIR, name);
					startPhotoZoom(Uri.fromFile(new File(Paths.IMAGESIGNDIR, fileName)), file);
				}
				// 选择图片
				if (requestCode == RequestCode.PHONESELECT) {
					Uri uri = data.getData();
					// 获得随机的文件名
				    picType = getPicType(uri);
					String name = ImageUtils.getTempFileName() + picType;
					file = new File(Paths.IMAGESIGNDIR, name);
					startPhotoZoom(uri, file);
				}
		// 处理结果
		if (requestCode == RequestCode.PHOTORESOULT) {
			try {
				Bitmap bitmap = getLoacalBitmap(file);
				ImageView image1 = (ImageView) info__wrap.findViewById(R.id.icon_layout);
			    image1 .setImageBitmap(bitmap);	//设置Bitmap
		    	Map<String, Object> map = new HashMap<String, Object>();
				url = "uphead";
		    	map.put("member", Commons.getInstance().getUserPk());
		    	map.put("userName", Commons.getInstance().getUserName());
		    	run("uphead", map);
			} catch (FileNotFoundException e) {
				show("文件剪切失败，请稍后重试");
				ExceptionHandle.handeException(activity, e);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
    public Object doInBackground(Map<String, Object> map, Object... arg0) {
		if(url.equals("uphead")){
			try {
				String url = UpFileUtils.upFile(file.getAbsolutePath(), "" , ".jpg");
		    	map.put("url", url);
				return ConnectionUtils.DO(activity, url, getMethodType(), map);
			} catch (Exception e) {
				return "0";
			}
		}
		return ConnectionUtils.DO(activity, url, getMethodType(), map);
	}

}
