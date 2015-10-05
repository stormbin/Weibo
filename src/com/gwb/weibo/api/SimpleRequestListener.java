package com.gwb.weibo.api;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.gwb.weibo.utils.Logger;
import com.gwb.weibo.utils.ToastUtils;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class SimpleRequestListener implements RequestListener {

	private Context context;
	private Dialog progressDialog;

	public SimpleRequestListener(Context context, Dialog progressDialog) {
		this.context = context;
		this.progressDialog = progressDialog;
	}

	@Override
	public void onWeiboException(WeiboException e) {
		onAllDone();
		ToastUtils.showToast(context, e.getMessage(), Toast.LENGTH_SHORT);
		Logger.show("REQUEST onError", e.toString());

	}

	public void onComplete(String response) {
		onAllDone();
		Logger.show("REQUEST onComplete", response);
	}

	public void onAllDone() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

}
