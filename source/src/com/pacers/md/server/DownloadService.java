package com.pacers.md.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.pacers.md.server.model.DownloadModel;

public class DownloadService extends Service implements DownloadCallback {
	private final String tag = getClass().getSimpleName();
	private Messenger serverMsger;
	private Messenger clientMsger;
	public static final int FLAG_MSG_CLONE_CLIENT_MESSEGER = 0x0001;
	public static final int FLAG_MSG_DOWNLOAD_START = 0x0002;
	public static final int FLAG_MSG_DOWNLOAD_STOP = 0x0003;
	public static final int FLAG_MSG_DOWNLOAD_FINISH = 0x0004;
	public static final int FLAG_MSG_DOWNLOAD_PROGRESS = 0x0005;
	public static final String FLAG_MSG_TAG = "INFO";

	public static final int FLAG_CMD_CLIENT_PAUSE_ALL = 0x10001;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i(tag, "onCreate");
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(tag, "onBind");
		serverMsger = new Messenger(dlHandler);
		return serverMsger.getBinder();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private Handler dlHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case FLAG_MSG_CLONE_CLIENT_MESSEGER:
				clientMsger = msg.replyTo;
				break;
			case FLAG_MSG_DOWNLOAD_START:
				startDownload((DownloadModel) msg.obj);
				break;
			case FLAG_MSG_DOWNLOAD_STOP:
				stopDownload((DownloadModel) msg.obj);
				break;

			case FLAG_CMD_CLIENT_PAUSE_ALL:
				break;
			}
			return false;
		}
	});

	private boolean sendMsg(Message msg) {
		try {
			clientMsger.send(msg);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean startDownload(DownloadModel info) {
		return false;
	}

	public boolean stopDownload(DownloadModel info) {
		return false;
	}

	@Override
	public void onFinished(DownloadModel info) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = FLAG_MSG_DOWNLOAD_FINISH;
		msg.obj = info;
		sendMsg(msg);
	}

	@Override
	public void onStarted(DownloadModel info) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = FLAG_MSG_DOWNLOAD_START;
		msg.obj = info;
		sendMsg(msg);
	}

	@Override
	public void onStoped(DownloadModel info) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(DownloadModel info, int code) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProgress(DownloadModel info, long length, float rate) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = FLAG_MSG_DOWNLOAD_PROGRESS;
		msg.obj = info;
		sendMsg(msg);
	}

}
