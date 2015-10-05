package com.gwb.weibo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gwb.weibo.R;
import com.gwb.weibo.adapter.StatusAdapter;
import com.gwb.weibo.api.SimpleRequestListener;
import com.gwb.weibo.api.WeiboDemoApi;
import com.gwb.weibo.base.BaseFragment;
import com.gwb.weibo.entity.Status;
import com.gwb.weibo.entity.response.StatusTimeLineResponse;
import com.gwb.weibo.utils.Logger;
import com.gwb.weibo.utils.NetAvailable;
import com.gwb.weibo.utils.TitleBuilder;
import com.gwb.weibo.utils.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

/**
 * **********************************************************
 * 
 * @内容摘要 ：首页界面
 *       <p>
 * @author gwb
 * @文件名：HomeFragment.java
 * @创建时间 ：2015-10-1 下午4:13:28
 * @当前版本号：v1.0
 * @修改人：
 */
public class HomeFragment extends BaseFragment {
	private View view;

	private PullToRefreshListView plv_home;
	private View footView;

	private StatusAdapter adapter;
	private List<Status> statuses = new ArrayList<Status>();
	private int curPage = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initView();
		loadData(1);
		return view;
	}

	private void initView() {
		view = View.inflate(activity, R.layout.fragment_home, null);

		new TitleBuilder(view).setTitleText("首页");

		plv_home = (PullToRefreshListView) view.findViewById(R.id.lv_home);
		adapter = new StatusAdapter(activity, statuses);
		plv_home.setAdapter(adapter);
		plv_home.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (NetAvailable.isNetAvailable(activity)) {
					loadData(1);
				} else {
					ToastUtils.showToast(activity, "网络出错啦，请检查网络",
							Toast.LENGTH_SHORT);
					plv_home.onRefreshComplete();
				}
			}
		});
		plv_home.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				if (NetAvailable.isNetAvailable(activity)) {
					loadData(curPage + 1);
				} else {
					ToastUtils.showToast(activity, "网络出错啦，请检查网络",
							Toast.LENGTH_SHORT);
					removeFootView(plv_home, footView);
				}
			}
		});

		footView = View.inflate(activity, R.layout.footview_loading, null);
	}

	private void loadData(final int page) {
		WeiboDemoApi api = new WeiboDemoApi(activity);

		api.statusesHome_timeline(page, new SimpleRequestListener(activity,
				null) {

			@Override
			public void onComplete(String response) {
				super.onComplete(response);

				if (page == 1) {
					statuses.clear();
				}

				curPage = page;
				// Logger.show("response", response);
				addData(new Gson().fromJson(response,
						StatusTimeLineResponse.class));
			}

			@Override
			public void onAllDone() {
				super.onAllDone();

				plv_home.onRefreshComplete();
			}

		});
	}

	private void addData(StatusTimeLineResponse resBean) {
		for (Status status : resBean.getStatuses()) {
			Logger.show("response", resBean.getStatuses() + "");
			if (!statuses.contains(status)) {
				statuses.add(status);

			}
		}

		adapter.notifyDataSetChanged();
		if (curPage < resBean.getTotal_number()) {
			addFootView(plv_home, footView);
		} else {
			removeFootView(plv_home, footView);
		}
	}

	private void addFootView(PullToRefreshListView plv, View footView) {
		ListView lv = plv.getRefreshableView();
		if (lv.getFooterViewsCount() == 1) {
			lv.addFooterView(footView);
		}
	}

	private void removeFootView(PullToRefreshListView plv, View footView) {
		ListView lv = plv.getRefreshableView();
		if (lv.getFooterViewsCount() > 1) {
			lv.removeFooterView(footView);
		}
	}

}
