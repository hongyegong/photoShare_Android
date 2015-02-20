package com.myou.appclient.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.myou.appclient.base.Commons;
import com.myou.appclient.base.KeyNames;
import com.myou.appclient.base.RequestCode;
import com.myou.appclient.util.JsonUtils;
import com.myou.appclient.util.MD5;
import com.myou.appclient.util.StringUtils;

/**
 * Title: 智旅app<br>
 * Description: 登陆弹出框<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class LoginDialogActivity extends BaseActivity {

	/** 用户名，密码，是否保存密码 组件  */
    private CheckBox isSavePs;
    private EditText email;
    private EditText password;
	
	/** 类别列表数据 */
	String[] typeItems = new String[]{""};
	String[] typeItemIds = new String[]{""};
	
	@Override
	protected void runEndDo(Object result) {
		Map<String, Object> map = JsonUtils.getMap((String) result) ;
		String str = (String)map.get("result");
		if(str.equals("0")){
			Commons.getInstance().setUserName((String) map.get("name"));
			Commons.getInstance().setUserPk((String)map.get("key"));
	        saveData();
	        myFinish();
		}else{
			show("用户名不存在或者是用户名密码错误");
		}
	}
	
	public void myFinish(){
        Bundle bundle = new Bundle();
        bundle.putBoolean("isOk", true);
        setResult(RequestCode.LOGIN, this.getIntent().putExtras(bundle));
		finish();
	}

	@Override
	protected void myCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fourth_login_dialog);
		
        isSavePs = (CheckBox) findViewById(R.id.isSavePs);
		email = (EditText) findViewById(R.id.loginEmail);
		password = (EditText) findViewById(R.id.loginPassword);
		
		findViewById(R.id.to_zhuce).setVisibility(8);
		
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
	}
	

	public void memberDO(View view){
		String emailText = email.getText().toString();
		String passwordText = password.getText().toString();
		if(StringUtils.isEmpty(emailText) || StringUtils.isEmpty(passwordText)){
			show("用户名或密码不能为空");
		}else{
			Map<String, Object> argus = new HashMap<String, Object>();
			argus.put("name", emailText);
			argus.put("password", MD5.MD5(passwordText));
			run("login.ashx", argus);
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
	
}
