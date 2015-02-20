package com.myou.appclient.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Title: 智旅app<br>
 * Description:    Dialog工具类<br>
 * Copyright: Copyright (c) 2013<br>
 * Company: MYOU<br>
 * 
 * @author jjy
 * @version 1.0
 */
public class CustomDialog {
	
	private AlertDialog.Builder builder;
	private String posBtntxt, negBtntxt;
	private View view;
	private Context context;

	public CustomDialog(Context context) {
		this.context = context;
		onCreateBuilder(context);
	}

	private void onCreateBuilder(Context context) {
		if (builder == null) {
			builder = new AlertDialog.Builder(context);
		}
	}

	private void initBtn(String PosButtonText, String NegButtonText) {
		if ("".equals(PosButtonText)) {
			posBtntxt = "确定";
		} else {
			posBtntxt = PosButtonText;
		}
		if ("".equals(NegButtonText)) {
			negBtntxt = "取消";
		} else {
			negBtntxt = NegButtonText;
		}
	}

	private void initIcon(int icon) {
		if (icon > 0) {
			builder.setIcon(icon);
		}
	}

	private void initIcon(Drawable icon) {
		if (icon != null) {
			builder.setIcon(icon);
		}
	}

	public void initListener(String PosButtonText, String NegButtonText,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(PosButtonText, NegButtonText);
		if (posBtntxt != null) {
			builder.setPositiveButton(posBtntxt, posListener);
		}
		if (negBtntxt != null) {
			builder.setNegativeButton(negBtntxt, negListener);
		}
	}

	public void initMessage(String message) {
		if (message != null && !"".equals(message)) {
			builder.setMessage(message);
		}
	}

	public void initTitle(String title) {
		if (title != null && !"".equals(title)) {
			builder.setTitle(title);
		}
	}

	/**
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (int)图标资源
	 * @param PosButtonText
	 *            确定按钮文字，不设置默认为 “确定”
	 * @param NegButtonText
	 *            取消按钮文字，不设置默认为 "取消"
	 * @param posListener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param negListener
	 *            取消按钮相应事件， 如果没有 “取消” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String title, String message, int icon,
			String PosButtonText, String NegButtonText,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(PosButtonText, NegButtonText);
		initTitle(title);
		initIcon(icon);
		initMessage(message);
		initListener(PosButtonText, NegButtonText, posListener, negListener);
	}

	/**
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (Drawable)图标资源
	 * @param PosButtonText
	 *            确定按钮文字，不设置默认为 “确定”
	 * @param NegButtonText
	 *            取消按钮文字，不设置默认为 "取消"
	 * @param posListener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param negListener
	 *            取消按钮相应事件， 如果没有 “取消” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String title, String message, Drawable icon,
			String PosButtonText, String NegButtonText,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(PosButtonText, NegButtonText);
		initTitle(title);
		initIcon(icon);
		initMessage(message);
		initListener(PosButtonText, NegButtonText, posListener, negListener);
	}

	/**
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param posListener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param negListener
	 *            取消按钮相应事件， 如果没有 “取消” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String message, String title,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(null, null);
		initTitle(title);
		initMessage(message);
		initListener(null, null, posListener, negListener);
	}

	/**
	 * @param message
	 *            显示的主题内容
	 * @param posListener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param negListener
	 *            取消按钮相应事件， 如果没有 “取消” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String message,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(null, null);
		initMessage(message);
		initListener(null, null, posListener, negListener);
	}

	/**
	 * @param icon
	 *            (int)图片资源
	 * @param message
	 *            显示的主题内容
	 * @param posListener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param negListener
	 *            取消按钮相应事件， 如果没有 “取消” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String message, int icon,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(null, null);
		initIcon(icon);
		initMessage(message);
		initListener(null, null, posListener, negListener);
	}

	/**
	 * @param icon
	 *            (Drawable)图片资源
	 * @param message
	 *            显示的主题内容， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param posListener
	 *            确定按钮响应事件， 如果没有 “取消” 按钮，则将此参数设置为：null
	 * @param negListener
	 *            取消按钮相应事件
	 */
	public void createAlert(String message, Drawable icon,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(null, null);
		initIcon(icon);
		initMessage(message);
		initListener(null, null, posListener, negListener);
	}

	/**
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (Drawable)图标资源
	 * @param PosButtonText
	 *            确定按钮文字，不设置默认为 “确定”， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param NegButtonText
	 *            取消按钮文字，不设置默认为 "取消"， 如果没有 “取消” 按钮，则将此参数设置为：null
	 * @param posListener
	 *            确定按钮响应事件
	 * @param negListener
	 *            取消按钮相应事件
	 */
	public void createAlert(String message, Drawable icon,
			String PosButtonText, String NegButtonText,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(PosButtonText, NegButtonText);
		initIcon(icon);
		initMessage(message);
		initListener(PosButtonText, NegButtonText, posListener, negListener);
	}

	/**
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (int)图标资源
	 * @param PosButtonText
	 *            确定按钮文字，不设置默认为 “确定”， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param NegButtonText
	 *            取消按钮文字，不设置默认为 "取消"， 如果没有 “确定” 按钮，则将此参数设置为：null
	 * @param posListener
	 *            确定按钮响应事件
	 * @param negListener
	 *            取消按钮相应事件
	 */
	public void createAlert(String message, int icon, String PosButtonText,
			String NegButtonText, DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(PosButtonText, NegButtonText);
		initIcon(icon);
		initMessage(message);
		initListener(PosButtonText, NegButtonText, posListener, negListener);
	}

	/**
	 * @只显示一个按钮
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (int)图标资源
	 * @param ButtonText
	 *            按钮文字
	 * @param Listener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String title, String message, int icon,
			String ButtonText, DialogInterface.OnClickListener Listener) {
		initBtn(ButtonText, null);
		initTitle(title);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, Listener, null);
	}

	/**
	 * @只显示一个按钮
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (int)图标资源
	 * @param ButtonText
	 *            按钮文字
	 * @param Listener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String title, String message, int icon,
			String ButtonText) {
		initBtn(ButtonText, null);
		initTitle(title);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, null, null);
	}

	/**
	 * @只显示一个按钮
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (Drawable)图标资源
	 * @param ButtonText
	 *            按钮文字
	 * @param Listener
	 *            确定按钮响应事件， 如果没有 “确定” 按钮，则将此参数设置为：null
	 */
	public void createAlert(String title, String message, Drawable icon,
			String ButtonText) {
		initBtn(ButtonText, null);
		initTitle(title);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, null, null);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (Drawable)图标资源
	 * @param ButtonText
	 *            按钮文字
	 * @param istener
	 *            该按钮默认为"确定",也可与自己将文字设置为“取消”
	 */
	public void createAlert(String message, Drawable icon, String ButtonText,
			DialogInterface.OnClickListener Listener) {
		initBtn(ButtonText, null);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, Listener, null);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (Drawable)图标资源
	 * @param ButtonText
	 *            按钮文字
	 */
	public void createAlert(String message, Drawable icon, String ButtonText) {
		initBtn(ButtonText, null);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, null, null);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (int)图标资源
	 * @param ButtonText
	 *            按钮文字
	 */
	public void createAlert(String message, int icon, String ButtonText) {
		initBtn(ButtonText, null);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, null, null);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 * @param icon
	 *            (int)图标资源
	 * @param ButtonText
	 *            按钮文字
	 * @param Listener
	 *            该按钮默认为"确定",也可与自己将文字设置为“取消”
	 */
	public void createAlert(String message, int icon, String ButtonText,
			DialogInterface.OnClickListener Listener) {
		initBtn(ButtonText, null);
		initIcon(icon);
		initMessage(message);
		initListener(ButtonText, null, Listener, null);
	}

	/**
	 * @只显示一个按钮
	 * @param title
	 *            标题
	 * @param message
	 *            显示的主题内容
	 * @param ButtonText
	 *            按钮文字
	 * @param Listener
	 *            该按钮默认为"确定",也可与自己将文字设置为“取消”
	 */
	public void createAlert(String title, String message, String ButtonText,
			DialogInterface.OnClickListener Listener) {
		initBtn(ButtonText, null);
		initTitle(title);
		initMessage(message);
		initListener(ButtonText, null, Listener, null);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 * @param ButtonText
	 *            按钮文字
	 * @param Listener
	 *            该按钮默认为"确定",也可与自己将文字设置为“取消”
	 */
	public void createAlert(String message, String ButtonText,
			DialogInterface.OnClickListener Listener) {
		initBtn(ButtonText, null);
		initMessage(message);
		initListener(ButtonText, null, Listener, null);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 * @param Listener
	 *            该按钮默认为"确定",也可与自己将文字设置为“取消”
	 */
	public void createAlert(String message,
			DialogInterface.OnClickListener Listener) {
		createAlert(message, "确定", Listener);
	}

	/**
	 * @只显示一个按钮
	 * @param message
	 *            显示的主题内容
	 */
	public void createAlert(String message) {
		createAlert(message, "确定", null);
	}

	/**
	 * 
	 * @param title
	 * @param icon
	 * @param viewLayout
	 * @param posButtonText
	 * @param negButtonText
	 * @param posListener
	 * @param negListener
	 */
	public void createAlert(String title, int icon, int viewLayout,
			String posButtonText, String negButtonText,
			DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(posButtonText, negButtonText);
		initTitle(title);
		initIcon(icon);
		view = View.inflate(context, viewLayout, null);
		builder.setView(view);
		initListener(posButtonText, negButtonText, posListener, negListener);
	}

	/**
	 * @param icon
	 * @param viewLayout
	 * @param posButtonText
	 * @param negButtonText
	 * @param posListener
	 * @param negListener
	 */
	public void createAlert(int icon, int viewLayout, String posButtonText,
			String negButtonText, DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(posButtonText, negButtonText);
		initIcon(icon);
		view = View.inflate(context, viewLayout, null);
		builder.setView(view);
		initListener(posButtonText, negButtonText, posListener, negListener);
	}

	/**
	 * 
	 * @param title
	 * @param viewLayout
	 * @param posButtonText
	 * @param negButtonText
	 * @param posListener
	 * @param negListener
	 */
	public void createAlert(String title, View view, String posButtonText,
			String negButtonText, DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(posButtonText, negButtonText);
		initTitle(title);
		builder.setView(view);
		initListener(posButtonText, negButtonText, posListener, negListener);
	}

	/**
	 * @param viewLayout
	 * @param posButtonText
	 * @param negButtonText
	 * @param posListener
	 * @param negListener
	 */
	public void createAlert(View view, String posButtonText,
			String negButtonText, DialogInterface.OnClickListener posListener,
			DialogInterface.OnClickListener negListener) {
		initBtn(posButtonText, negButtonText);
		builder.setView(view);
		initListener(posButtonText, negButtonText, posListener, negListener);
	}

	/**
	 * 显示
	 */
	public void show() {
		builder.show();
	}

}
