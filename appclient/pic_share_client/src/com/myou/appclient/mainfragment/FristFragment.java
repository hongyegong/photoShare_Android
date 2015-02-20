package com.myou.appclient.mainfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.myou.appclient.activity.R;
import com.myou.appclient.base.ActivityManage;
import com.myou.appclient.base.Commons;
import com.myou.appclient.ui.CustomSimpleAdapter;
import com.myou.appclient.ui.PullToRefreshView;
import com.myou.appclient.ui.PullToRefreshView.OnFooterRefreshListener;
import com.myou.appclient.ui.PullToRefreshView.OnHeaderRefreshListener;
import com.myou.appclient.util.JsonUtils;

/**
 * Title: 智旅app<br>
 * Description:   第1个界面<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
@SuppressLint("ValidFragment")
public class FristFragment extends BaseFragment  implements OnHeaderRefreshListener, OnFooterRefreshListener {

	List<Map<String, Object>> mdata;
    CustomSimpleAdapter adapter;
    
	/** 组件 */
	public GridView listView;

	PullToRefreshView mPullToRefreshView;

	/** 选择的删选对象 */
	public static final int PAGENUM = 10;
	String type = "all";
	String search = "";
	int pageNo = 1;
	boolean isHasMore = true;
	boolean isAdd = true;
	
	public FristFragment(Activity activity) {
		super(activity);
	}
	
	@Override
	protected void runEndDo(Object result) {
		List<Map<String, Object>> list = JsonUtils.getList((String) result);
		if(list.size() ==0 && !isHasMore && isAdd){
			show("没有更多了");
			return;
		}
		if(!isAdd){
			mdata.clear();
		}
		mdata.addAll(list);
		adapter.notifyDataSetChanged();
		if(list.size() == PAGENUM){
	    	pageNo++;
		}else{
			pageNo++;
			adapter.notifyDataSetChanged();
			isHasMore = false;
		}
	}
	
	 public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			public void run() {
				System.out.println("上拉加载");
				myRun(false, true);
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
				myRun(false, false);
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 0);

	}
	
	public void myRun(boolean isShowLoading, boolean isAdd){
		this.isAdd = isAdd;
		if(!isAdd){
			pageNo = 1;
		}
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("imgType", type);
    	map.put("search", search);
    	map.put("pageNo", pageNo);
    	map.put("pageNum", PAGENUM);
    	run("getPic", map, isShowLoading);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.first_layout,container,false);

		// 设置adapter
		listView =  (GridView) view.findViewById(android.R.id.list);

		mdata = new ArrayList<Map<String,Object>>();
		//A.PK_PIC, A.PK_MEMBER, A.NICK_NAME, A.PIC_PATH, A.PIC_NAME, A.PIC_MEMO
		adapter = new CustomSimpleAdapter(view.getContext(),
				mdata, R.layout.first_item_layout, new String[]{"PIC_PATH", "PIC_NAME"},
				new int[] { R.id.icon_layout, R.id.pic_name });
		listView.setAdapter(adapter);

		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		
        // 添加点击后事件
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	Map<String, Object> seleteType = (Map<String, Object>) parent.getItemAtPosition(position);
            	if(seleteType != null){
            		Commons.getInstance().setYouhuiMap(seleteType);
            		ActivityManage.showFirstDetail(activity, "");
            	}
            }
        });
		
		myRun(true, true);
		return view;
	}

	public void homeDO(View v) {
		
	}
}
