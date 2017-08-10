package com.le.mychat.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class SizeUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 获取屏幕的宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics mt = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(mt);
		return mt.widthPixels;
	}

	// 获取屏幕的高度
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics mt = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(mt);
		return mt.heightPixels;
	}

	public static void setDisplay(Activity activity, View view) {
		// 图片格式 1024 * 768 4:3
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		lp.height = (int) (0.75 * dm.widthPixels);
		view.setLayoutParams(lp);
	}

	public static void setDisplay2(Activity activity, View view) {
		// 图片格式 1024 * 768 纵向屏幕
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		lp.height = (int) (dm.widthPixels * 4 / 3.0);
		view.setLayoutParams(lp);
	}
}
