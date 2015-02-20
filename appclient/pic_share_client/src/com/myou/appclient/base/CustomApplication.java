package com.myou.appclient.base;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.myou.appclient.activity.AppAllActivity;

public class CustomApplication extends Application implements   Thread.UncaughtExceptionHandler{
	
    @Override 
    public void onCreate() { 
        
        super.onCreate(); 
        Thread.setDefaultUncaughtExceptionHandler(this);
        
    }  
    
    public void uncaughtException(Thread thread, Throwable exception) { 
        // TODO Auto-generated method stub 
            
            Log.e("tag", "exception >>>>>>>"+exception.getLocalizedMessage()); 
            
            Intent intent = new Intent(this, AppAllActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            
            //将异常抛出，则应用会弹出异常对话框.这里先注释掉 
         //   defaultExceptionHandler.uncaughtException(thread, exception); 
            
    } 
    
}
