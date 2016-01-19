package com.jerry.musicplayer;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import com.jerry.musicplayer.bean.MusicInfo;
import com.jerry.musicplayer.receiver.DownloadCompleteReceiver;
import com.jerry.musicplayer.receiver.NetStateChangedReceiver;
import com.jerry.musicplayer.service.MediaPlayerService;
import com.jerry.musicplayer.utils.LogUtils;
import com.jerry.musicplayer.utils.MusicUtils;

import org.json.JSONObject;

/**
 * Created by jerry on 16-1-4.
 */
public class MusicPlayerApp extends Application{
    private String mTag = "MusicPlayerApp";
    /**
     * 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了
     */
    private static MusicPlayerApp mInstance;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    public static Activity CURRENT_ACTIVITY;
    private NetStateChangedReceiver mNetReceiver;

    private DownloadCompleteReceiver mDownloadCompleteReceiver;

    public static boolean isCheckUpdate = false;

    public static boolean ISLOGIN = false;

    /**
     * 播放器主服务
     */
    private static MediaPlayerService.Stub mService;

    @Override
    public void onCreate() {
        Intent intent = new Intent(this, MediaPlayerService.class);
        startService(intent);
        bindService(intent, osc, this.BIND_AUTO_CREATE);
        mNetReceiver = new NetStateChangedReceiver();
        // 注册网络状态变化的广播接收者
        registerReceiver(mNetReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        super.onCreate();
    }

    /**
     * 应用销毁是调用
     */
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mNetReceiver);
        unbindService(osc);
    }

    ServiceConnection osc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = (MediaPlayerService.Stub) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    public static MusicPlayerApp getApplication() {
        return mInstance;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static MediaPlayerService.Stub getService(){
        return mService;
    }

    /**
     * 检测版本
     */
    public void checkUpdate() {
        if (isCheckUpdate) {
            return;
        }
//        if (ConstantUtil.isNetworkConnected(this)) {
//            mDownloadCompleteReceiver = new DownloadCompleteReceiver();
//            registerReceiver(mDownloadCompleteReceiver, new IntentFilter(
//                    DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//            HttpUtils httpUtils = new HttpUtils();
//            httpUtils
//                    .configRequestThreadPoolSize(ConstantUtil.MAX_THREAD_NUMBER);
//            httpUtils.send(HttpRequest.HttpMethod.GET,
//                    WebParams.URL_CHECKVERSION, null,
//                    new RequestCallBack<String>() {
//
//                        @Override
//                        public void onStart() {
//                        }
//
//                        @Override
//                        public void onSuccess(ResponseInfo<String> arg0) {
//                            if (null != arg0.result && !arg0.result.isEmpty()) {
//                                try {
//                                    updateApp(arg0.result);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(HttpException arg0, String arg1) {
//                        }
//                    });
//        }
        isCheckUpdate = true;
    }

    private void updateApp(String checkResult) throws Exception {
        JSONObject obj = new JSONObject(checkResult);
        int versionCode = obj.getInt("versionCode");
        boolean mustUpdate = obj.getString("update").equalsIgnoreCase("force");
        String msg = obj.getString("msg");
        String appUrl = obj.getString("url");
//		OnSweetClickListener confirmListener = new OnSweetClickListener() {
//
//			@Override
//			public void onClick(SweetAlertDialog sweetAlertDialog) {
//				DownloadManager.Request down = new DownloadManager.Request(
//						Uri.parse(appUrl));
//				// down.addRequestHeader("security","ChineseAll&*(");
//				// 设置允许使用的网络类型，这里是移动网络和wifi都可以
//				down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
//						| DownloadManager.Request.NETWORK_WIFI);
//				// 显示通知
//				down.setShowRunningNotification(true);
//				// 显示下载界面
//				down.setVisibleInDownloadsUi(true);
//				// 设置下载后文件存放的位置
//				down.setDestinationInExternalFilesDir(CURRENT_ACTIVITY, null,
//						"bjetextbook.apk");
//				// 将下载请求放入队列
//				DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//				manager.enqueue(down);
//				sweetAlertDialog.dismiss();
//			}
//		};
//		OnSweetClickListener cancelListener = new OnSweetClickListener() {
//
//			@Override
//			public void onClick(SweetAlertDialog sweetAlertDialog) {
//				sweetAlertDialog.dismiss();
//			}
//		};
        if (versionCode > getPackageManager().getPackageInfo(getPackageName(),
                1).versionCode) {
//			if (mustUpdate) {
//				ConstantUtil.showMyAlertDialog(CURRENT_ACTIVITY,
//						getString(R.string.app_update_title), msg,
//						getString(R.string.app_update_confirm), "",
//						confirmListener, null, false);
//			} else {
//				ConstantUtil.showMyAlertDialog(CURRENT_ACTIVITY,
//						getString(R.string.app_update_title), msg,
//						getString(R.string.app_update_confirm),
//						getString(R.string.app_update_cancel), confirmListener,
//						cancelListener, true);
//			}

//			NormalDialogStyleOne(msg, appUrl, mustUpdate);
        }
    }
}
