package com.myou.appclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.myou.appclient.base.ActivityManage;
import com.myou.appclient.base.Commons;
import com.myou.appclient.ui.CustomSimpleAdapter;
import com.myou.appclient.ui.PullToRefreshView;
import com.myou.appclient.ui.PullToRefreshView.OnFooterRefreshListener;
import com.myou.appclient.ui.PullToRefreshView.OnHeaderRefreshListener;
import com.myou.appclient.util.JsonUtils;

/**
 * Title: 智旅app<br>
 * Description: 我的图片<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class MyPicActivity extends BaseActivity  implements OnHeaderRefreshListener, OnFooterRefreshListener {


	List<Map<String, Object>> mdata;
    CustomSimpleAdapter adapter;
    
	/** 组件 */
	public GridView listView;

	PullToRefreshView mPullToRefreshView;

	/** 选择的删选对象 */
	public static final int PAGENUM = 1000;
	String type = "all";
	String search = "";
	int pageNo = 1;
	boolean isHasMore = true;
	
	@Override
	protected void runEndDo(Object result) {
		List<Map<String, Object>> list = JsonUtils.getList((String) result);
		if(list == null || list.size() == 0){
			if(pageNo == 1){
				mdata.clear();
				adapter.notifyDataSetChanged();
			}
			return;
		}
		mdata.addAll(list);
		adapter.notifyDataSetChanged();
		if(list.size() == PAGENUM){
	    	pageNo++;
		}else{
			adapter.notifyDataSetChanged();
			isHasMore = false;
		}
	}
	
	public void myRun(boolean isShowLoading){
		myRun(isShowLoading, false);
	}
	
	 public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			public void run() {
				System.out.println("上拉加载");
				myNoRunDo(false);
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 0);
	}

	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			public void run() {
				System.out.println("下拉更新");
				// 设置更新时间
				// mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
				myNoRunDo(true);
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 0);

	}
	
	public void myNoRunDo(boolean isSelect){
		if(isSelect){
			pageNo = 1;
			isHasMore = true;
			mdata.clear();
		}
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("imgType", type);
    	map.put("search", search);
    	map.put("pageNo", pageNo);
    	map.put("member", Commons.getInstance().getUserPk());
    	map.put("pageNum", PAGENUM);
    	myRun(false, true);
//    	noRunDo("getPic", map);
	}
	
	public void myRun(boolean isShowLoading, boolean isSelect){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("imgType", type);
    	map.put("search", search);
    	map.put("pageNo", pageNo);
    	map.put("member", Commons.getInstance().getUserPk());
    	map.put("pageNum", PAGENUM);
    	run("getPic", map, isShowLoading);
	}
	
	@Override
	protected void myCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
		TextView title = (TextView) findViewById(R.id.anzhi);
		
		addBackClick();

        // 主列表传过来的数据
        type = getIntent().getStringExtra("type");
        if(type.equals("myAdd")){
        	title.setText("我的分享");
        }else{
        	title.setText("我的收藏");
        }

		   // 设置adapter
			listView =  (GridView) findViewById(android.R.id.list);

			mdata = new ArrayList<Map<String,Object>>();
			//A.PK_PIC, A.PK_MEMBER, A.NICK_NAME, A.PIC_PATH, A.PIC_NAME, A.PIC_MEMO
			adapter = new CustomSimpleAdapter(this,
					mdata, R.layout.first_item_layout, new String[]{"PIC_PATH", "PIC_NAME"},
					new int[] { R.id.icon_layout, R.id.pic_name });
			listView.setAdapter(adapter);

			mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
			mPullToRefreshView.setOnHeaderRefreshListener(this);
//			mPullToRefreshView.setOnFooterRefreshListener(this);
			
	        // 添加点击后事件
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	Map<String, Object> seleteType = (Map<String, Object>) parent.getItemAtPosition(position);
	            	if(seleteType != null){
	            		Commons.getInstance().setYouhuiMap(seleteType);
	            		ActivityManage.showFirstDetail(MyPicActivity.this, "");
	            	}
	            }
	        });
			
			myRun(true);
	}
	
	public void setText(int id, String text){
		TextView  textview = (TextView) findViewById(id);
		textview.setText(textview.getText() + text);
	}
	
}
