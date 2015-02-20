package com.myou.appclient.mainfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myou.appclient.activity.R;
import com.myou.appclient.base.ActivityManage;

/**
 * Title: 智旅app<br>
 * Description:   第5个界面<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
@SuppressLint("ValidFragment")
public class FourthFragment extends BaseFragment {
	
	public FourthFragment(Activity activity) {
		super(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fifth_layout,container,false);

		TextView title = (TextView) view.findViewById(R.id.anzhi);
		title.setText("设置");
		
		return view;
	}
	
	public void setDO(View v) {
		RelativeLayout wrap = (RelativeLayout) v;
		TextView textView = (TextView) wrap.getChildAt(0);
		String text = textView.getText().toString().trim();
		if(text.equals("关于我们")){
			ActivityManage.showAbout(activity);
		}else if(text.equals("提交反馈")){
			ActivityManage.showYjfk(activity);
		}
	}

}
