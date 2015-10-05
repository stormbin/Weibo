package com.gwb.weibo.base;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;


import com.google.gson.Gson;
import com.gwb.weibo.constants.CommonConstants;
import com.gwb.weibo.utils.Logger;
import com.gwb.weibo.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends Activity {

	protected String TAG;

	protected BaseApplication application;
	protected SharedPreferences sp;
	protected ImageLoader imageLoader;
	protected Gson gson;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TAG = this.getClass().getSimpleName();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
		
		imageLoader = ImageLoader.getInstance();
		gson = new Gson();
	}
	
	protected void intentActivity(Class<? extends Activity> tarActivity) {
		Intent intent = new Intent(this, tarActivity);
		startActivity(intent);
	}
	
	protected void showToast(String msg) {
		ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
	}

	protected void showLog(String msg) {
		Logger.show(TAG, msg);
	}

}
