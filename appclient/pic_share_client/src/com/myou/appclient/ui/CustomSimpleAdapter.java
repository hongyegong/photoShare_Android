package com.myou.appclient.ui;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.myou.appclient.ui.AsyncImageLoader.ImageCallback;
import com.myou.appclient.util.ExceptionHandle;

/**
 * Title: 智旅app<br>
 * Description:    自定义的listview的 SimpleAdapter （实现图片的异步加载）<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class CustomSimpleAdapter extends SimpleAdapter{

	public final static String CLASSNAME = ExceptionHandle.LOGNAME;
	
	private AsyncImageLoader asyncImageLoader;
	
	private Context context;
	
    private int[] mTo;  
    private String[] mFrom;  
    private ViewBinder mViewBinder;  
    public List<? extends Map<String, ?>> mData;  
    private int mResource;  
    private LayoutInflater mInflater;  
	
	public CustomSimpleAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		mData = data;  
        mResource = resource;  
        mFrom = from;  
        mTo = to;  
		asyncImageLoader = new AsyncImageLoader();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }  
	
	/** 
     * @see android.widget.Adapter#getView(int, View, ViewGroup) 
     */  
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {  
        return createViewFromResource(position, convertView, parent, mResource);  
    }  
	
    private View createViewFromResource(int position, View convertView,  
            ViewGroup parent, int resource) {  
        View v;  
        if (convertView == null) {  
            v = mInflater.inflate(resource, parent, false);  
  
            final int[] to = mTo;  
            final int count = to.length;  
            final View[] holder = new View[count];  
  
            for (int i = 0; i < count; i++) {  
                holder[i] = v.findViewById(to[i]);  
            }  
  
            v.setTag(holder);  
        } else {  
            v = convertView;  
        }  
        bindView(position, v);  
  
        return v;  
    }  
      
    private void bindView(int position, View view) {  
        final Map<?, ?> dataSet = mData.get(position);  
        if (dataSet == null) {  
            return;  
        }  
        final ViewBinder binder = mViewBinder;  
        final View[] holder = (View[]) view.getTag();  
        final String[] from = mFrom;  
        final int[] to = mTo;  
        final int count = to.length;  
  
        for (int i = 0; i < count; i++) {  
            final View v = holder[i];         
            if (v != null) {  
                final Object data = dataSet.get(from[i]);  
                String text = data == null ? "" : data.toString();  
                if (text == null) {  
                    text = "";  
                }  
                boolean bound = false;  
                if (binder != null) {  
                    bound = binder.setViewValue(v, data, text);  
                }  
                if (!bound) {  
                    if (v instanceof Checkable) {  
                        if (data instanceof Boolean) {  
                            ((Checkable) v).setChecked((Boolean) data);  
                        } else {  
                            throw new IllegalStateException(v.getClass().getName() +  
                                    " should be bound to a Boolean, not a " + data.getClass());  
                        }  
                    }  else if (v instanceof Button) {
                    	String[] strs = ((String) data).split(",", 2);
                    	((Button) v).setText(strs[0].equals("1") ? "下载" : "更新");
                    	((Button) v).setTag(strs[1]);
                    } else if (v instanceof TextView) {  
                        ((TextView) v).setText(text);  
                    } else if (v instanceof ImageView) {
                    	if(data == null){
                    		return;
                    	}
                    	if(data instanceof Integer){
                        	setViewImage((ImageView) v, (Integer) data); 
                    	}else{
                    		setViewImage((ImageView) v, (String) data); 
                    	}
                    }
                    else if(v instanceof RatingBar){  
                        float score = Float.parseFloat(data.toString());  //备注2  
                        ((RatingBar)v).setRating(score);  
                    }  
                    else {  
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +  
                                " view that can be bounds by this SimpleAdapter");  
                    }  
                }  
            }  
        }  
    }  
	
	
	@Override
	public void setViewImage(final ImageView v, final String value) {
        v.setTag(value);
        Drawable cachedImage = asyncImageLoader.loadDrawable(value, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) v.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        }, context);
        v.setImageDrawable(cachedImage);
	}
	
}
