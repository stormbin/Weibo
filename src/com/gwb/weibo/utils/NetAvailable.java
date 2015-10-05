package com.gwb.weibo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * **********************************************************
 * 
 * @内容摘要 ：网络状态工具类
 *       <p>
 * @author gwb
 * @文件名：NetVaailable.java
 * @创建时间 ：2015-10-4 上午11:09:41
 * @当前版本号：v1.0
 * @修改人：
 */
public class NetAvailable {
	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return (info != null && info.isAvailable());
	}
}
