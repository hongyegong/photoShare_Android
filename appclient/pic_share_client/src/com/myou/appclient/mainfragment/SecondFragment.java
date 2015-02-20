package com.myou.appclient.mainfragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myou.appclient.activity.R;
import com.myou.appclient.base.Commons;
import com.myou.appclient.base.Paths;
import com.myou.appclient.base.RequestCode;
import com.myou.appclient.util.ConnectionUtils;
import com.myou.appclient.util.ExceptionHandle;
import com.myou.appclient.util.JsonUtils;
import com.myou.appclient.util.StringUtils;
import com.myou.appclient.util.file.FileUtils;
import com.myou.appclient.util.file.ImageUtils;
import com.myou.appclient.util.file.UpFileUtils;

/**
 * @description： 专题界面<br>
 * @author： txd
 * @update： 2013-4o
 * @version： 1.0
 * @email：
 */
@SuppressLint("ValidFragment")
public class SecondFragment extends BaseFragment {
	
	ImageView image1;
	View view;
	
	public SecondFragment(Activity activity) {
		super(activity);
	}

	public void seDO(View v) {
		if(v.getId() == R.id.upPic){
			toPhone();
		}else{
			if(StringUtils.isEmpty(Commons.getInstance().getUserPk())){
				show("请先登录后分享");
				return;
			}
			if(file == null){
				show("必须选择一张图片！");
				return;
			}
			url = "sharePic";
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("member", Commons.getInstance().getUserPk());
	    	map.put("userName", Commons.getInstance().getUserName());
			TextView picName = (TextView) view.findViewById(R.id.pic_name);
			TextView picMemo = (TextView) view.findViewById(R.id.pic_memo);
	    	map.put("picName", picName.getText());
	    	map.put("picMemo", picMemo.getText());
	    	run("sharePic", map);
		}
	}
	
	@Override
	protected void runEndDo(Object result) {
		Map<String, Object> map = JsonUtils.getMap((String) result);
		String str = (String)map.get("return");
		if(str != null && str.equals("0")){
			show("分享成功！");
			TextView picName = (TextView) view.findViewById(R.id.pic_name);
			TextView picMemo = (TextView) view.findViewById(R.id.pic_memo);
			picName.setText("");
			picMemo.setText("");
			image1.setImageDrawable(activity.getResources().getDrawable(R.drawable.zheng));
		}else{
			show("分享失败，请稍后重试！");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.second_main_map,container,false);

		TextView title = (TextView) view.findViewById(R.id.anzhi);
		title.setText("分享我的图片");
		
		image1 = (ImageView) view.findViewById(R.id.upPic);
		
		return view;
	}

/**
 * 显示缩放的activity
 * @param uri
 */
public void startPhotoZoom(Uri uri, File file) {
	Intent intent = new Intent("com.android.camera.action.CROP");
	intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
	intent.putExtra("crop", "true");
	intent.putExtra("return-data", false);
	intent.putExtra("noFaceDetection", true);

	intent.putExtra("output", Uri.fromFile(file));
	startActivityForResult(intent, RequestCode.PHOTORESOULT);
}



	/**
	 * 回调
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RequestCode.NONE){
			return;
		}
		// 拍照
		if (requestCode == RequestCode.PHOTOHRAPH) {
			// 获得随机的文件名
			String name = ImageUtils.getTempFileName() + ".jpg";
			file = new File(Paths.IMAGESIGNDIR, name);
			startPhotoZoom(Uri.fromFile(new File(Paths.IMAGESIGNDIR, fileName)), file);
		}
		// 选择图片
		if (requestCode == RequestCode.PHONESELECT) {
			Uri uri = data.getData();
			// 获得随机的文件名
		    picType = getPicType(uri);
			String name = ImageUtils.getTempFileName() + picType;
			file = new File(Paths.IMAGESIGNDIR, name);
			startPhotoZoom(uri, file);
		}
		// 处理结果
		if (requestCode == RequestCode.PHOTORESOULT) {
			Bitmap bitmap;
			try {
				bitmap = getLoacalBitmap(file);
			    image1 .setImageBitmap(bitmap);	//设置Bitmap
			} catch (FileNotFoundException e) {
				show("文件剪切失败，请稍后重试");
				e.printStackTrace();
				ExceptionHandle.handeException(activity, e);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
    public Object doInBackground(Map<String, Object> map, Object... arg0) {
		if(url.equals("sharePic")){
			try {
				String url = UpFileUtils.upFile(file.getAbsolutePath(), "" , ".jpg");
		    	map.put("url", url);
				return ConnectionUtils.DO(activity, url, getMethodType(), map);
			} catch (Exception e) {
				return "0";
			}
		}
		return ConnectionUtils.DO(activity, url, getMethodType(), map);
	}
	
}
