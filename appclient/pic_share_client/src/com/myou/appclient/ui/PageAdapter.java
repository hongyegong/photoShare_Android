package com.myou.appclient.ui;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.myou.appclient.activity.R;

public class PageAdapter extends BaseAdapter {
	
    private List<Map<String, Object>> list;
    LayoutInflater inflater;
    
    public PageAdapter(Context context, List<Map<String, Object>> list) {
        this.list=list;
        this.inflater=LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.third_item_page, null);
//            cacheView.tv_des=(TextView) convertView.findViewById(R.id.tv_des);
//            //cacheView.imgv_img=(ImageView) convertView.findViewById(R.id.imageView1);
//            convertView.setTag(cacheView);
        }
        return convertView;
    }
    
    private static class CacheView{
        TextView tv_des;
    }
}