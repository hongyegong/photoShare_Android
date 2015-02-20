package com.myou.appclient.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Title: 智旅app<br>
 * Description:    自定义登陆的EditText<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class CustomEditText extends EditText{  
  
    private Paint mPaint;  
    
    private int color = Color.argb(128, 128, 128, 128);
      
    public CustomEditText(Context context, AttributeSet attrs){  
        super(context, attrs);  
        mPaint = new Paint();  
          
        mPaint.setStyle(Paint.Style.STROKE);  
        mPaint.setColor(color);
    }  
      
    @Override  
    protected void onDraw(Canvas canvas){  
        super.onDraw(canvas);
//        TODO:4边的凹线未实现
//        int width = this.getWidth();
//        int height = this.getHeight();
//        int spec = 16;
//        
//        // 上面第一条线
//        canvas.drawLine(spec, 0, width - spec, 0, mPaint);
//        // 下面最后一条线
//        canvas.drawLine(spec, height - 1, width - spec, height - 1, mPaint);
//        // 左边的线
//        canvas.drawLine(0, spec, 0, height, mPaint);
//        // 右边的线
//        canvas.drawLine(width - 1, spec, width - 1, height, mPaint);
//        // 画圆角的线
//        canvas.drawRoundRect(new RectF(0, 0, this.getWidth(), 10000), spec/2, spec/2, mPaint);
        // 画底线  
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1, mPaint);  
    }  
}  
