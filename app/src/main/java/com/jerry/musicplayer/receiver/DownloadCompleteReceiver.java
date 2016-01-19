package com.jerry.musicplayer.receiver;

import java.util.Locale;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
	    DownloadManager downloadManager = (DownloadManager) context
		    .getSystemService(Context.DOWNLOAD_SERVICE);
	    long downloadId = intent.getLongExtra(
		    DownloadManager.EXTRA_DOWNLOAD_ID, 0);
	    Query query = new Query();
	    query.setFilterById(downloadId);
	    Cursor c = downloadManager.query(query);
	    if (c.moveToFirst()) {
		int columnIndex = c
			.getColumnIndex(DownloadManager.COLUMN_STATUS);
		if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
		    columnIndex = c
			    .getColumnIndex(DownloadManager.COLUMN_TITLE);
		    final String fileName = c.getString(columnIndex)
			    .toLowerCase(Locale.getDefault());
		    if (fileName.endsWith(".apk")) {
			columnIndex = c
				.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
			Uri installUri = Uri.parse(c.getString(columnIndex));
			Intent returnIt = new Intent(Intent.ACTION_VIEW);
			returnIt.setDataAndType(installUri,
				"application/vnd.android.package-archive");
			returnIt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(returnIt);
		    }
		}
	    }
	}
    }

}
