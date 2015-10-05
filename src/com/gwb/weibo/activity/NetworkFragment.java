package com.gwb.weibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.gwb.weibo.R;
import com.gwb.weibo.base.BaseActivity;
import com.gwb.weibo.base.BaseFragment;

/**
 * **********************************************************
 * 
 * @内容摘要 ：
 *       <p>
 * @author gwb
 * @文件名：NetworkFragment.java
 * @创建时间 ：2015-10-3 下午7:28:48
 * @当前版本号：v1.0
 * @修改人：
 */
public class NetworkFragment extends BaseActivity implements OnClickListener {
	private View view;
	private Button mBtnReload;
	private MainActivity mainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_network);
		mainActivity = new MainActivity();
		mBtnReload = (Button) view.findViewById(R.id.btn_reload);
		mBtnReload.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
