package com.le.mychat.context;

import android.content.Context;
import android.widget.Toast;

public class ShowPrompt {
	static String promptString = "未知错误";

	private static String getMsg(int i) {
		switch (i) {
		case 0:
			promptString = "网络连接失败";
			break;
		default:
			break;
		}
		return promptString;
	}

	public static void showError(Context context, int statusCode) {
		try {
			Toast.makeText(context, getMsg(statusCode), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示服务器返回消息
	 * @param context
	 * @param code
	 * @param msg
	 */
	public static void showError(Context context, String code, String msg) {
		if ("99".equals(code)) {
			Toast.makeText(context, "服务器异常", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showShort(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
