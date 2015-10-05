package com.gwb.weibo.activity;

import com.gwb.weibo.R;
import com.gwb.weibo.fragment.FragmentController;
import com.gwb.weibo.utils.ToastUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * **********************************************************
 * 
 * @内容摘要 ：
 *       <p>
 * @author gwb
 * @文件名：MainActivity.java
 * @创建时间 ：2015-9-29 下午5:57:25
 * @当前版本号：v1.0
 * @修改人：
 */
public class MainActivity extends FragmentActivity implements OnClickListener,
		OnCheckedChangeListener {
	private ViewPager mViewPager;
	private RadioGroup rg_tab;
	private ImageView iv_add;
	private FragmentController controller;
	private FragmentPagerAdapter mAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 指定竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		controller = FragmentController.getInstance(this, R.id.fl_content);
		controller.showFragment(0);

		initView();

	}

	private void initView() {
//		mViewPager = (ViewPager) findViewById(R.id.vp_content);
		rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
		iv_add = (ImageView) findViewById(R.id.iv_add);

		rg_tab.setOnCheckedChangeListener(this);
		iv_add.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home:
			controller.showFragment(0);
			break;
		case R.id.rb_meassage:
			controller.showFragment(1);
			break;
		case R.id.rb_search:
			controller.showFragment(2);
			break;
		case R.id.rb_user:
			controller.showFragment(3);
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add:
			ToastUtils.showToast(this, "add", Toast.LENGTH_SHORT);
			break;

		default:
			break;
		}
	}

}
