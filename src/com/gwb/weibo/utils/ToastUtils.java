package com.gwb.weibo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	private static Toast mToast;
	
	/**
	 * 防止多条Toast显示等待时间过长
	 * 显示Toast
	 */
	public static void showToast(Context context, CharSequence text, int duration) {
		if(mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
	}

}
