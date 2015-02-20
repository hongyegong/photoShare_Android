package com.myou.appclient.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.myou.appclient.base.ActivityManage;
import com.myou.appclient.base.RequestCode;
import com.myou.appclient.mainfragment.FourthFragment;
import com.myou.appclient.mainfragment.FristFragment;
import com.myou.appclient.mainfragment.SecondFragment;
import com.myou.appclient.mainfragment.ThirdFragment;
import com.myou.appclient.util.DummyTabContent;

/**
 * Description: 框架主界面<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class AppAllActivity extends BaseActivity {

	private TabHost mTabHost;
	TabWidget tabWidget;
	private FragmentTransaction mFt;
	private RelativeLayout mTabIndicator_home, mTabIndicator_app,
			mTabIndicator_game, mTabIndicator_top;
	private FristFragment mFristFragment;
	private SecondFragment mSecondFragment;
	private ThirdFragment mThirdFragment;
	private FourthFragment mFourthFragment;
	private FragmentManager mFm = getSupportFragmentManager();
	
	/**
	 * 回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RequestCode.CITY) {
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void runEndDo(Object result) {
		
	}
	
	/** button事件 */
	public void setDO(View v) {
		if (R.id.set_cancel == v.getId()) {
	        finish();
	        ActivityManage.AppExit(this);
	        System.exit(0);  
		} else {
			RelativeLayout wrap = (RelativeLayout) v;
			TextView textView = (TextView) wrap.getChildAt(0);
			String text = textView.getText().toString().trim();
			if (text.equals("清空缓存")) {
				OnClickListener posListener = new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						show("清理完成");
					}

				};
				show_YES_NO("确定要清空缓存么?", posListener, null);
			}else {
				mFourthFragment.setDO(v);
			}
		}
	}
	
	public void seDO(View v) {
		mSecondFragment.seDO(v);
	}
	
	public void memberDO(View v) {
		mThirdFragment.memberDO(v);
	}

	/** 
	 * 菜单、返回键响应 
	 */  
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	/** 
	 * 双击退出函数 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;
	    if (isExit == false) {  
	        isExit = true; // 准备退出  
	        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	    } else {
	        finish();
	        ActivityManage.AppExit(this);
	        System.exit(0);  
	    }  
	}  
	
	@Override
	protected void myCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findTabView();
		mTabHost.setup();

		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

			public void onTabChanged(String tabId) {

				mFt = mFm.beginTransaction();

				// TODO Auto-generated method stub
				if (mFristFragment != null) {
					mFt.hide(mFristFragment);
				}
				if (mSecondFragment != null) {
					mFt.hide(mSecondFragment);
				}
				if (mThirdFragment != null) {
					mFt.hide(mThirdFragment);
				}
				if (mFourthFragment != null) {
					mFt.hide(mFourthFragment);
				}

				if (tabId.equalsIgnoreCase("home")) {
					attachTabHome();
				} else if (tabId.equalsIgnoreCase("app")) {
					attachTabApp();
				} else if (tabId.equalsIgnoreCase("game")) {
					attachTabGame();
				} else if (tabId.equalsIgnoreCase("top")) {
					attachTabTop();
				}
				// 执行Fragment事务（添加。移除，替换fragment）
				mFt.commit();
			}
		};
		mTabHost.setOnTabChangedListener(tabChangeListener);
		initTab();
		//默认跳到第几个选项卡
		mTabHost.setCurrentTab(0);
	}

	/**
	 * 找到TabHost的相关布局，并绘制每一个选项卡<br>
	 */
	private void findTabView() {

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		LinearLayout layout = (LinearLayout) mTabHost.getChildAt(0);
		TabWidget tabWidget = (TabWidget) layout.getChildAt(1);

		mTabIndicator_home = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tabWidget, false);
		ImageView ivTab1 = (ImageView) mTabIndicator_home.getChildAt(0);
		ivTab1.setImageResource(R.drawable.selector_menu_home_btu);
		TextView tvTab1 = (TextView) mTabIndicator_home.getChildAt(1);
		tvTab1.setText(R.string.home);

		mTabIndicator_app = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tabWidget, false);
		ImageView ivTab2 = (ImageView) mTabIndicator_app.getChildAt(0);
		ivTab2.setImageResource(R.drawable.selector_menu_app_btu);
		TextView tvTab2 = (TextView) mTabIndicator_app.getChildAt(1);
		tvTab2.setText(R.string.app);

		mTabIndicator_game = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tabWidget, false);
		ImageView ivTab3 = (ImageView) mTabIndicator_game.getChildAt(0);
		ivTab3.setImageResource(R.drawable.selector_menu_game_btu);
		TextView tvTab3 = (TextView) mTabIndicator_game.getChildAt(1);
		tvTab3.setText(R.string.game);

		mTabIndicator_top = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tabWidget, false);
		ImageView ivTab4 = (ImageView) mTabIndicator_top.getChildAt(0);
		ivTab4.setImageResource(R.drawable.selector_menu_top_btu);
		TextView tvTab4 = (TextView) mTabIndicator_top.getChildAt(1);
		tvTab4.setText(R.string.top);

	}

	/**
	 * 以下一系列方法都是判断fragment是否已经创建，防止重复add进FragmentTransaction <br>
	 * FragmentTransaction中提供了show,add,replace,hide等一系列方法，可以根据不同的需求 <br>
	 * 使用,这里demo使用了show方法，是因为避免使用replace之后会重新创建fragment,重新加载数据 <br>
	 */
	private void attachTabHome() {

		if (mFristFragment == null) {
			mFristFragment = new FristFragment(this);
			mFt.add(R.id.realtabcontent, mFristFragment, "home");
		} else {
			mFt.show(mFristFragment);
			
		}
	}

	private void attachTabApp() {

		if (mSecondFragment == null) {
			mSecondFragment = new SecondFragment(this);
			mFt.add(R.id.realtabcontent, mSecondFragment, "app");
		} else {
			mFt.show(mSecondFragment);
		}
	}

	private void attachTabGame() {

		if (mThirdFragment == null) {
			mThirdFragment = new ThirdFragment(this);
			mFt.add(R.id.realtabcontent, mThirdFragment, "game");
		} else {
			mFt.show(mThirdFragment);
		}
	}

	private void attachTabTop() {

		if (mFourthFragment == null) {
			mFourthFragment = new FourthFragment(this);
			mFt.add(R.id.realtabcontent, mFourthFragment, "top");
		} else {
			mFt.show(mFourthFragment);
		}
	}

	/**
	 * 初始化选项卡 <br>
	 */
	private void initTab() {

		TabHost.TabSpec tSpecHome = mTabHost.newTabSpec("home");
		tSpecHome.setIndicator(mTabIndicator_home);
		tSpecHome.setContent(new DummyTabContent(getBaseContext()));
		mTabHost.addTab(tSpecHome);

		TabHost.TabSpec tSpecApp = mTabHost.newTabSpec("app");
		tSpecApp.setIndicator(mTabIndicator_app);
		tSpecApp.setContent(new DummyTabContent(getBaseContext()));
		mTabHost.addTab(tSpecApp);

		TabHost.TabSpec tSpecGame = mTabHost.newTabSpec("game");
		tSpecGame.setIndicator(mTabIndicator_game);
		tSpecGame.setContent(new DummyTabContent(getBaseContext()));
		mTabHost.addTab(tSpecGame);

		TabHost.TabSpec tSpecTop = mTabHost.newTabSpec("top");
		tSpecTop.setIndicator(mTabIndicator_top);
		tSpecTop.setContent(new DummyTabContent(getBaseContext()));
		mTabHost.addTab(tSpecTop);

	}
}
