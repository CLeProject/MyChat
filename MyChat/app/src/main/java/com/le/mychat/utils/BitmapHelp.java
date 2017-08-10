package com.le.mychat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.le.mychat.R;
import com.lidroid.xutils.BitmapUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author: wyouflf Date: 13-11-12 Time: 上午10:24
 */
public class BitmapHelp {
	private BitmapHelp() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(appContext);
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loadfail_picture);
		}
		return bitmapUtils;
	}

	public static Bitmap getBitmap(Context context, Uri uri, int width, int height) {
		Bitmap bitmap = null;
		try {
			Bitmap b = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
			bitmap = ThumbnailUtils.extractThumbnail(b, width, height);
			b.recycle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}


}
