package com.jerry.musicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * 监听网络状态变化
 * 
 * @author loutiantian
 * 
 */
public class NetStateChangedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

	}
//    private DownManager mDownloadManager;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//	String action = intent.getAction();
//	if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//	    // 网络不可用时中断下载
//	    if (!ConstantUtil.isNetworkConnected(context)) {
//		if (null == mDownloadManager) {
//		    mDownloadManager = DownloadService
//			    .getDownloadManager(context);
//		}
//		mDownloadManager.stopAllDownload();
//	    }
//	}
//    }
}
