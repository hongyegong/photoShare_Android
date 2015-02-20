package com.myou.appclient.base;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.myou.appclient.activity.WelcomeActivity;

public class CustomException implements UncaughtExceptionHandler {
    //获取application 对象； 
    private Context mContext; 
    
    private Thread.UncaughtExceptionHandler defaultExceptionHandler; 
    //单例声明CustomException; 
    private static CustomException customException; 
    
    private CustomException(){        
    } 
    
    public static CustomException getInstance(){ 
        if(customException == null){ 
            customException = new CustomException(); 
        } 
        return customException; 
    } 
    
    public void uncaughtException(Thread thread, Throwable exception) { 
        // TODO Auto-generated method stub 
        if(defaultExceptionHandler != null){ 
            
            Log.e("tag", "exception >>>>>>>"+exception.getLocalizedMessage()); 
            
            Intent intent = new Intent(mContext, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP    | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);

            if(mContext instanceof Activity){
            	Activity a = (Activity) mContext;
            	a.finish();
            }
            
            //将异常抛出，则应用会弹出异常对话框.这里先注释掉 
         //   defaultExceptionHandler.uncaughtException(thread, exception); 
            
        } 
    } 
    
     public void init(Context context) {   
            mContext = context;   
            defaultExceptionHandler  = Thread.getDefaultUncaughtExceptionHandler();   
           Thread.setDefaultUncaughtExceptionHandler(this);  
          }
}
