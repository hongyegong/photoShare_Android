package com.myou.appclient.activity;

import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.myou.appclient.base.Commons;
import com.myou.appclient.util.StringUtils;
import com.myou.appclient.util.ui.UIUtils;

/**
 * Title: 智旅app<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class FirstDetailActivity extends BaseActivity {
	
	Map<String, Object> myMap;
	
	@Override
	protected void myCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.first_detail);
		TextView title = (TextView) findViewById(R.id.anzhi);
		title.setText("图片详情");
		
		addBackClick();

		// A.PK_PIC, A.PK_MEMBER, A.NICK_NAME, A.PIC_PATH, A.PIC_NAME, A.PIC_MEMO, A.TS
		myMap = Commons.getInstance().getYouhuiMap();
		// 设置图片
		final ImageView imageView = (ImageView) findViewById(R.id.icon_layout);
		String picPath =  (String) myMap.get("PIC_PATH");
		if(picPath.startsWith("http://58")){
			picPath = picPath.substring(0, picPath.lastIndexOf("_4444"));
		}
		setImage(imageView,picPath);
		setText(R.id.pic_name, (String) myMap.get("PIC_NAME"));
		String picMemo = "上传者：" + myMap.get("NICK_NAME") + "\n上传时间：" + myMap.get("TS") + "\n" + myMap.get("PIC_MEMO") ;
		setText(R.id.pic_memo, picMemo);
	}
	
	public void share(View v){
		UIUtils.share(this, "看到一张很好的图片，大家快来围观喽：" + myMap.get("PIC_PATH"));
	}
	
	public void to_sc(View v){
		if(StringUtils.isEmpty(Commons.getInstance().getUserPk())){
			show("请登录后收藏！");
			return;
		}
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("member", Commons.getInstance().getUserPk());
    	map.put("userName", Commons.getInstance().getUserName());
    	map.put("PK_PIC",  (String) myMap.get("PK_PIC"));
    	run("picSc", map);
	}
	
	@Override
	protected void runEndDo(Object result) {
		show("收藏成功！");
		super.runEndDo(result);
	}

	public void setText(int id, String text){
		TextView  textview = (TextView) findViewById(id);
		textview.setText(textview.getText() + text);
	}
	
}
