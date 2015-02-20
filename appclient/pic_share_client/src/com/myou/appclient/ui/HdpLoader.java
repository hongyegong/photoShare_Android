package com.myou.appclient.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.myou.appclient.activity.R;
import com.myou.appclient.ui.AsyncImageLoader.ImageCallback;

public class HdpLoader {

	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	private ImageView[] imageViews;
	// 包裹滑动图片LinearLayout
	private ViewGroup main;
	// 包裹小圆点的LinearLayout
	private ViewGroup group;
	/** 图片异步加载 */
	private AsyncImageLoader asyncImageLoader;
	
	public void loader(Activity activity, LayoutInflater inflater, ViewGroup view, List<Map<String, Object>> data, OnClickListener listener){
		this.main = view;
		
        // 初始化异步加载类
		asyncImageLoader = new AsyncImageLoader();
		pageViews = new ArrayList<View>();
		for (Map<String, Object> map : data) {
			View v1 = inflater.inflate(R.layout.common_hdp_item, null);
			pageViews.add(v1);
			final ImageView imageView = (ImageView) v1.findViewById(R.id.itemPic);
	    	final String value = (String)map.get("pic");
	    	final String tag = (String)map.get("tag");
	    	imageView.setTag(value);
	    	Drawable cachedImage = asyncImageLoader.loadDrawable(value, new ImageCallback() {
	            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
	                ImageView imageViewByTag = (ImageView) imageView.findViewWithTag(value);
	                if (imageViewByTag != null) {
	                    imageViewByTag.setImageDrawable(imageDrawable);
	                }
	            }
	        }, activity);
	    	imageView.setImageDrawable(cachedImage);
			// 监听子页面事件
			RelativeLayout layout = (RelativeLayout) v1.findViewById(R.id.linearLayout1);
			layout.setTag(tag);
			layout.setOnClickListener(listener);
			
		}

		imageViews = new ImageView[pageViews.size()];

		group = (ViewGroup) main.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) main.findViewById(R.id.guidePages);

		for (int i = 0; i < pageViews.size(); i++) {
			ImageView imageView = new ImageView(activity);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(20, 0, 20, 0);
			imageViews[i] = imageView;

			if (i == 0) {
				// 默认选中第一张图片
				imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.page_indicator);
			}

			group.addView(imageViews[i]);
		}

		viewPager.setAdapter(new GuidePageAdapter());
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	}
	

	// 指引页面数据适配器
	private class GuidePageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	// 指引页面更改事件监听器
	private class GuidePageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.page_indicator_focused);

				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.page_indicator);
				}
			}
		}
	}
}
