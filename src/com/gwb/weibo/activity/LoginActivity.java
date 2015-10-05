package com.gwb.weibo.activity;

import com.gwb.weibo.R;
import com.gwb.weibo.base.BaseActivity;
import com.gwb.weibo.constants.AccessTokenKeeper;
import com.gwb.weibo.constants.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * **********************************************************
 * 
 * @内容摘要 ：登录页面
 *       <p>
 * @author gwb
 * @文件名：LoginActivity.java
 * @创建时间 ：2015-9-30 上午9:24:35
 * @当前版本号：v1.0
 * @修改人：
 */
public class LoginActivity extends BaseActivity {
	private AuthInfo mAuthInfo;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	private Button mLoginBtnDefault;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);

		// all In one方式授权
		mLoginBtnDefault = (Button) findViewById(R.id.btn_login);
		mLoginBtnDefault.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// AidInfo aidInfo = AidTask4Plug.getInstance(MainActivity.this,
				// "123456").getAid4PlugSync( "123456", "pak", "hash");
				mSsoHandler.authorize(new AuthListener());
			}
		});
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						mAccessToken);
				showToast("auth_success");
				intentActivity(MainActivity.class);
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = "auth_failed";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				showToast(message);
			}
		}

		@Override
		public void onCancel() {
			showToast("auth_canceled");
		}

		@Override
		public void onWeiboException(WeiboException e) {
			showToast("Auth exception : " + e.getMessage());
		}
	}
}
