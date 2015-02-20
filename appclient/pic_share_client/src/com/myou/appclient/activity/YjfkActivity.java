package com.myou.appclient.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myou.appclient.base.Commons;

/**
 * @description： <br>
 * @author： jiujiya
 * @update： 2013-4-10
 * @version： 1.0
 * @email：136336790@qq.com
 */
public class YjfkActivity extends BaseActivity {

	EditText help_feedback = null;

	@Override
	protected void myCreate(Bundle savedInstanceState) {
		setContentView(R.layout.yjfk);
		TextView title = (TextView) findViewById(R.id.anzhi);
		title.setText("意见反馈");

		addBackClick();

		// 获取按钮
		Button but_help_feedback = (Button) findViewById(R.id.but_help_feedback);
		help_feedback = (EditText) findViewById(R.id.help_feedback);

		// 添加点击事件 ，保存文本信息，并生成提示，同时跳转到主界面
		but_help_feedback.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String Context = help_feedback.getText().toString();
		    	Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("content", Context);
		    	map.put("member", Commons.getInstance().getUserPk());
		        run("comment", map);
			}
		});

	}
	
	@Override
	protected void runEndDo(Object result) {
		show("感谢您的反馈,我们会尽快处理您的意见。");
		finish();
	}

}
