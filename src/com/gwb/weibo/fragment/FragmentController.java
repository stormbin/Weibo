package com.gwb.weibo.fragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 
 * **********************************************************
 * 
 * @内容摘要 ：首页、消息、发现和我四个Fragment的管理者
 *       <p>
 * @author gwb
 * @文件名：FragmentController.java
 * @创建时间 ：2015-10-1 下午4:01:35
 * @当前版本号：v1.0
 * @修改人：
 */
public class FragmentController {

	private int containerId;
	private FragmentManager fm;
	private ArrayList<Fragment> fragments;
	
	private static FragmentController controller;

	public static FragmentController getInstance(FragmentActivity activity, int containerId) {
		if (controller == null) {
			controller = new FragmentController(activity, containerId);
		}
		return controller;
	}

	private FragmentController(FragmentActivity activity, int containerId) {
		this.containerId = containerId;
		fm = activity.getSupportFragmentManager();
		initFragment();
	}

	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new MessageFragment());
		fragments.add(new SearchFragment());
		fragments.add(new UserFragment());
		
		FragmentTransaction ft = fm.beginTransaction();
		for(Fragment fragment : fragments) {
			ft.add(containerId, fragment);
		}
		ft.commit();
	}

	public void showFragment(int position) {
		hideFragments();
		Fragment fragment = fragments.get(position);
		FragmentTransaction ft = fm.beginTransaction();
		ft.show(fragment);
		ft.commit();
	}
	
	public void hideFragments() {
		FragmentTransaction ft = fm.beginTransaction();
		for(Fragment fragment : fragments) {
			if(fragment != null) {
				ft.hide(fragment);
			}
		}
		ft.commit();
	}
	
	public Fragment getFragment(int position) {
		return fragments.get(position);
	}
}