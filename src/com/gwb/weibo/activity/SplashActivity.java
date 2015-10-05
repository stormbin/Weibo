package com.gwb.weibo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gwb.weibo.R;
import com.gwb.weibo.base.BaseActivity;
import com.gwb.weibo.constants.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class SplashActivity extends BaseActivity {

	/**
	 * **********************************************************
	 * 
	 * @内容摘要 ：引导界面
	 *       <p>
	 *       package com.gwb.weibo.activity;
	 * @author gwb
	 * @文件名：SplashActivity
	 * @创建时间 ：2015-9-30 下午7:17:34
	 * @当前版本号：v1.0
	 * @日期 : 修改人： t
	 */

	private static final int WHAT_INTENT2LOGIN = 1;
	private static final int WHAT_INTENT2MAIN = 2;
	private static final long SPLASH_DUR_TIME = 1000;

	private Oauth2AccessToken accessToken;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case WHAT_INTENT2LOGIN:
				intentActivity(LoginActivity.class);
				finish();
				break;
			case WHAT_INTENT2MAIN:
				intentActivity(MainActivity.class);
				finish();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);

		accessToken = AccessTokenKeeper.readAccessToken(this);
		if (accessToken.isSessionValid()) {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2MAIN, SPLASH_DUR_TIME);
		} else {
			handler.sendEmptyMessageDelayed(WHAT_INTENT2LOGIN, SPLASH_DUR_TIME);
		}
	}
}
