package com.myou.appclient.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.TextView;

import com.myou.appclient.util.JsonUtils;

/**
 * Title: 智旅app<br>
 * Description: 第3个界面详情 - 导览详情<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class ThirdDetailActivity extends BaseActivity {
	
	@Override
	protected void myCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.third_detail);
		TextView title = (TextView) findViewById(R.id.anzhi);
		title.setText("大公园");
		
		addBackClick();
		addShare();

		run("aboutUs.ashx", null);
        
	}
	
	@Override
	protected void runEndDo(Object result) {
		List<Map<String, Object>> list = JsonUtils.getList((String) result);
		Map<String, Object> map = list.get(0);
		setText(R.id.au_content, (String) map.get("content"));
		setText(R.id.au_connetiontitle, (String) map.get("phone"));
		setText(R.id.au_web, (String) map.get("webUrl"));
	}

	public void setText(int id, String text){
//		TextView  textview = (TextView) findViewById(id);
//		textview.setText(textview.getText() + text);
	}
	
}
