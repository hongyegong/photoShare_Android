package com.myou.appclient.activity;

import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;

import com.myou.appclient.util.JsonUtils;

/**
 * Description: 关于我们<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class AboutActivity extends BaseActivity {
	
	@Override
	protected void myCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
		TextView title = (TextView) findViewById(R.id.anzhi);
		title.setText("关于我们");
		
		addBackClick();

		run("about", null);
        
	}
	
	@Override
	protected void runEndDo(Object result) {
		Map<String, Object> map = JsonUtils.getMap((String) result);
		setText(R.id.au_content, (String) map.get("CONTENT"));
		setText(R.id.au_connetiontitle, (String) map.get("PHONE"));
		setText(R.id.au_web, (String) map.get("WEB_URL"));
	}

	public void setText(int id, String text){
		TextView  textview = (TextView) findViewById(id);
		textview.setText(textview.getText() + text);
	}
	
}
