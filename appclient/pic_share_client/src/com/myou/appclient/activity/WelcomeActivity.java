package com.myou.appclient.activity;

import java.util.List;
import java.util.Map;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.myou.appclient.base.ActivityManage;
import com.myou.appclient.base.Commons;
import com.myou.appclient.base.KeyNames;
import com.myou.appclient.util.JsonUtils;
import com.myou.appclient.util.ui.UIUtils;

/**
 * Title: 智旅app<br>
 * Description: 欢迎界面<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    public void myCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);

    	 shortCut();
    }
    
    private void cutEnd() {

    	Editor deitor = getEditor();
    	deitor.putString(KeyNames.FIRSTMARK, "");
    	deitor.commit();
    	
    	 // 查看是否联网
        if(!UIUtils.isConnectingToInternet(this)){
			show_MSG_DLG(getString(R.string.dialog_net_err_msg), new OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	ActivityManage.showHome(WelcomeActivity.this);
                }
            });
        }else{
//    		Map<String, Object> argus = new HashMap<String, Object>();
//    		run("ppt.ashx", argus, false);
    		 ActivityManage.showHome(WelcomeActivity.this);
        }
	}
    
    @Override
    protected void runEndDo(Object result) {

 	   List<Map<String, Object>> list = JsonUtils.getList((String) result);
 	   if(list.size()  == 0) return;
 	   if(list.size() < 6){
 		   Map<String, Object> map = list.get(0);
 		   while(true){
 			   list.add(map);
 			   if(list.size() == 6) break;
 		   }
 	   }
 	   for (Map<String, Object> map : list) {
 		   map.put("pic", map.get("picPath"));
 		   map.put("tag", map.get("picPath") + "," + map.get("content"));
 	   }
 	   Commons.getInstance().setHdp(list);
   	   ActivityManage.showHome(WelcomeActivity.this);
    }
    
    /**
     * 查看用户是否为第一次登陆，如果没有快捷方式，就提示用户 创建快捷方式
     */
    private void shortCut(){
        // 保存的email不存在，即为第一次登陆
		if(!getSharedPreferences().contains(KeyNames.FIRSTMARK) && !UIUtils.hasShortCut(this)){
    		show_YES_NO("是否创建桌面图标？", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	// 创建快捷方式
                    addShortcut();
                    cutEnd();
                }
            }, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    cutEnd();
                }
            });
		}else{
            cutEnd();
		}
    }
    
    /**
     * 为程序创建桌面快捷方式
     */
     private void addShortcut(){
         Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
         //快捷方式的名称
         shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
         shortcut.putExtra("duplicate", false); //不允许重复创建
         //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
         //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
         ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
         shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
         //快捷方式的图标
         ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, BASEICON);
         shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
         sendBroadcast(shortcut);
     }
	
}
