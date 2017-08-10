package com.le.mychat.http;

import android.os.Environment;
import android.util.Log;

import com.le.mychat.context.App;
import com.le.mychat.utils.FileUtils;
import com.le.mychat.utils.MD5;
import com.le.mychat.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;

/**
 * 缓存工具类
 */
public class ConfigCacheUtil {

    private static final String TAG = ConfigCacheUtil.class.getName();

    /**
     * 1分钟超时时间
     */
    public static final int CONFIG_CACHE_SHORT_TIMEOUT1 = 1000 * 60; // 1 分钟

    /**
     * 5分钟超时时间
     */
    public static final int CONFIG_CACHE_SHORT_TIMEOUT = 1000 * 60 * 5; // 5 分钟

    /**
     * 2小时超时时间
     */
    public static final int CONFIG_CACHE_MEDIUM_TIMEOUT = 1000 * 3600 * 2; // 2小时

    /**
     * 中长缓存时间
     */
    public static final int CONFIG_CACHE_ML_TIMEOUT = 1000 * 60 * 60 * 24 * 1; // 1天

    /**
     * 最大缓存时间
     */
    public static final int CONFIG_CACHE_MAX_TIMEOUT = 1000 * 60 * 60 * 24 * 7; // 7天

    /**
     * CONFIG_CACHE_MODEL_LONG : 长时间(7天)缓存模式 <br>
     * CONFIG_CACHE_MODEL_ML : 中长时间(12小时)缓存模式<br>
     * CONFIG_CACHE_MODEL_MEDIUM: 中等时间(2小时)缓存模式 <br>
     * CONFIG_CACHE_MODEL_SHORT : 短时间(5分钟)缓存模式
     */
    public enum ConfigCacheModel {
        CONFIG_NO_CACHE_MODEL, CONFIG_CACHE_MODEL_RELOAD, CONFIG_CACHE_MODEL_SHORT, CONFIG_CACHE_MODEL_MEDIUM, CONFIG_CACHE_MODEL_ML, CONFIG_CACHE_MODEL_LONG, ConfigCacheModel, CONFIG_FROM_CACHE
    }

    public static String CACHE_DIR = App.getHomeDir() + "/cache";

    public static String getKey(String url, String... identifers) {
        StringBuffer sb = new StringBuffer();
//		sb.append(App.getLoginCache().getUserInfo().getId())
        sb.append(url);
        if (identifers != null && identifers.length != 0) {
            for (int i = 0; i < identifers.length; i++) {
                sb.append(identifers[i]);
            }
        }
        return MD5.getMD5(sb.toString());
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return 缓存数据
     */
    public static String getCache(String key, ConfigCacheModel model) {
        if (key == null || ConfigCacheModel.CONFIG_NO_CACHE_MODEL == model) {
            return null;
        }

        String result = null;
        String path = CACHE_DIR + File.separator + key;
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.v(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime / 60000 + "min");
            // 1。如果系统时间是不正确的
            // 2。当网络是无效的,你只能读缓存
            if (NetWorkUtils.IsNetWorkEnable(App.getApp())) {
                if (expiredTime < 0 || model == ConfigCacheModel.CONFIG_CACHE_MODEL_RELOAD) {
                    return null;
                }
                if (model == ConfigCacheModel.CONFIG_FROM_CACHE) {
                    try {
                        return FileUtils.readTextFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (model == ConfigCacheModel.CONFIG_CACHE_MODEL_SHORT) {
                    if (expiredTime > CONFIG_CACHE_SHORT_TIMEOUT) {
                        return null;
                    }
                } else if (model == ConfigCacheModel.CONFIG_CACHE_MODEL_MEDIUM) {
                    if (expiredTime > CONFIG_CACHE_MEDIUM_TIMEOUT) {
                        return null;
                    }
                } else if (model == ConfigCacheModel.CONFIG_CACHE_MODEL_ML) {
                    if (expiredTime > CONFIG_CACHE_ML_TIMEOUT) {
                        return null;
                    }
                } else if (model == ConfigCacheModel.CONFIG_CACHE_MODEL_LONG) {
                    if (expiredTime > CONFIG_CACHE_MEDIUM_TIMEOUT) {
                        return null;
                    }
                } else {
                    if (expiredTime > CONFIG_CACHE_MAX_TIMEOUT) {
                        return null;
                    }
                }
            }

            try {
                result = FileUtils.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置缓存
     *
     * @param data
     * @param key
     */
    public static void setCache(String data, String key) {
        if (key == null || "".equals(key)) {
            return;
        }
        File dir = new File(CACHE_DIR);
        if (!dir.exists() /*&& Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)*/) {
            dir.mkdirs();
        }
        File file = new File(CACHE_DIR + File.separator + key);
        try {
            // 创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除历史缓存文件
     *
     * @param cacheFile
     */
    public static void clearCache(File cacheFile) {
        if (cacheFile == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    File cacheDir = new File(CACHE_DIR);
                    if (cacheDir.exists()) {
                        clearCache(cacheDir);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (cacheFile.isFile()) {
            cacheFile.delete();
        } else if (cacheFile.isDirectory()) {
            File[] childFiles = cacheFile.listFiles();
            if (childFiles != null && childFiles.length > 0) {
                for (int i = 0; i < childFiles.length; i++) {
                    clearCache(childFiles[i]);
                }
            }
        }
    }
}
