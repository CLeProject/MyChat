package com.le.mychat.context;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.le.mychat.utils.FileUtil;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class App extends Application {

    private static final String TAG = App.class.getName();

    private static LoginCache loginCache;

    /**
     * application重建标识
     */
    private static boolean isRecreted = true;
    private static String cacheDir;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        cacheDir = getCacheDir().getAbsolutePath() + "/";
        CrashHandler crashHandler = CrashHandler.getInstance(); // 开启异常捕获
        crashHandler.init(getApplicationContext());
        LogUtils.allowV = false;
        LogUtils.allowD = true;
        LogUtils.allowI = true;
//		AsyncHttpClient.log.setLoggingLevel(LogInterface.WARN);
        requestLocation(this);
        instance = this;
    }



    public static App getApp() {
        return instance;
    }

    public static String getHomeDir() {
        return Environment.getExternalStorageDirectory() + Constant.WORK_DIR;
    }

    public static String getCameraCacheDir() {
        return Environment.getExternalStorageDirectory() + Constant.WORK_DIR + "/cameraCache/";
    }

//    public static boolean hasRights(String... rights) {
//        for (String r : rights) {
//            if (App.getLoginCache().getRights().contains(r)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean isRole(String... roles) {
        for (String r : roles) {
            if (App.getLoginCache().getUserInfo().getRole().equals(r)) {
                return true;
            }
        }
        return false;
    }

    public void requestLocation(Context context) {
//        if (mLocationClient != null) {
//            mLocationClient.requestLocation();
//            return;
//        }
//        mLocationClient = new LocationClient(context);
//        mLocationClient.registerLocationListener(new BDLocationListener() {
//
//            @Override
//            public void onReceiveLocation(BDLocation location) {
//                if (location == null) {
//                    return;
//                }
//                l = location;
//                LogUtils.d("App---onReceiveLocation--->" + l.getAddrStr());
////                StringBuffer sb = new StringBuffer(256);
////                sb.append("time : ");
////                sb.append(location.getTime());
////                sb.append("\nerror code : ");
////                sb.append(location.getLocType());
////                sb.append("\nlatitude : ");
////                sb.append(location.getLatitude());
////                sb.append("\nlontitude : ");
////                sb.append(location.getLongitude());
////                sb.append("\nradius : ");
////                sb.append(location.getRadius());
////                if (location.getLocType() == BDLocation.TypeGpsLocation) {
////                    sb.append("\nspeed : ");
////                    sb.append(location.getSpeed());
////                    sb.append("\nsatellite : ");
////                    sb.append(location.getSatelliteNumber());
////                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
////                    sb.append("\naddr : ");
////                    sb.append(location.getAddrStr());
////                }
////                LogUtils.d(sb.toString());
//            }
//
//            @Override
//            public void onConnectHotSpotMessage(String s, int i) {
//
//            }
//        });
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true);// 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setAddrType("all");
//        // option.setScanSpan(1000);
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();
    }

//    public static BDLocation getLocation() {
//        return l;
//    }

//    private LocationClient mLocationClient = null;

//    private static BDLocation l;

    public static LoginCache getLoginCache() {
        if (isRecreted) {
            // reload
            Log.i(TAG, "app cache reload...");
            File f = new File(cacheDir + "appCache");
            if (f.exists()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    loginCache = (LoginCache) ois.readObject();
                    FileUtil.close(ois);
                    isRecreted = false;
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        }
        return loginCache;
    }

    public static void setLoginCache(LoginCache loginCache) {
        App.loginCache = loginCache;
    }

    /**
     * 在所有缓存参数设置后调用，缓存到磁盘 以便Application重建后恢复缓存状态
     */
    public static void saveState() {
        isRecreted = false;
        //  save cache to disk
        File f = new File(cacheDir + "appCache");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(loginCache);
            FileUtil.close(oos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
