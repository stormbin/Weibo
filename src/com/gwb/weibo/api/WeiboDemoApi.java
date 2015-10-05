package com.gwb.weibo.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.gwb.weibo.constants.AccessTokenKeeper;
import com.gwb.weibo.constants.Constants;
import com.gwb.weibo.constants.URLs;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.legacy.WeiboAPI;

public class WeiboDemoApi extends WeiboAPI {

	private Handler mainLooperHandler = new Handler(Looper.getMainLooper());

	public WeiboDemoApi(Context mContext, Oauth2AccessToken oauth2AccessToken) {
		super(mContext, oauth2AccessToken);
	}

	public WeiboDemoApi(Context context) {
		this(context, AccessTokenKeeper.readAccessToken(context));
	}

	public void requestInMainLooper(String url, WeiboParameters params,
			String httpMethod, final RequestListener requestListener) {
		request(url, params, httpMethod, new RequestListener() {

			@SuppressWarnings("unused")
			public void WeiboException(final WeiboException e) {
				mainLooperHandler.post(new Runnable() {
					@Override
					public void run() {
						requestListener.onWeiboException(e);
					}
				});
			}


			@SuppressWarnings("unused")
			public void onComplete4binary(final String responseOS) {
				mainLooperHandler.post(new Runnable() {
					@Override
					public void run() {
						requestListener.onComplete(responseOS);
					}
				});
			}

			@Override
			public void onComplete(final String response) {
				mainLooperHandler.post(new Runnable() {
					@Override
					public void run() {
						requestListener.onComplete(response);
					}
				});
			}

			@Override
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void request(String url, WeiboParameters params,
			String httpMethod, RequestListener listener) {
		// TODO Auto-generated method stub
		super.request(url, params, httpMethod, listener);
	}

	public void statusesHome_timeline(int page, RequestListener requestListener) {
		WeiboParameters parameters = new WeiboParameters(Constants.APP_KEY);
		parameters.put("page", page);
		requestInMainLooper(URLs.statusesHome_timeline, parameters,
				HTTPMETHOD_GET, requestListener);
	}
}
