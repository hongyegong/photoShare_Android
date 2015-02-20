package com.myou.appclient.util.adpater;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPageAdpate extends PagerAdapter{
	private List<View> data;
	private Activity activity;
	public ViewPageAdpate(List<View> data, Activity activity){
		this.data=data;
		this.activity=activity;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(data.get(position));
		return data.get(position);
	}
}
