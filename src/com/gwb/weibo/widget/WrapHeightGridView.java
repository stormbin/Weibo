package com.gwb.weibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * 
 * **********************************************************
 * 
 * @内容摘要 ：GridView自适应宽高的设置
 *       <p>
 * @author gwb
 * @文件名：WrapHeightGridView.java
 * @创建时间 ：2015-10-1 下午3:30:53
 * @当前版本号：v1.0
 * @修改人：
 */
public class WrapHeightGridView extends GridView {

	public WrapHeightGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WrapHeightGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapHeightGridView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int heightSpec = MeasureSpec.makeMeasureSpec(
					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, heightSpec);
	}
}
