package com.pacers.md.client;

import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.pacers.md.server.DownloadService;
import com.pacers.md.server.DownloadCallback;
import com.pacers.md.server.model.DownloadModel;

public class DownloadClient implements DownloadListener {
	private Messenger serverMsger;
	private Messenger clientMsger;
	private List<DownloadCallback> listeners;
	private static DownloadClient instance;
	private Application app;

	private DownloadClient(Application app) {
		this.app = app;
	}

	public static DownloadClient instance(Application app) {
		if (instance == null)
			instance = new DownloadClient(app);
		return instance;
	}

	public static DownloadClient instance() {
		return instance;
	}

	public void connect() {
		// TODO Auto-generated method stub
		app.startService(new Intent(app, DownloadService.class));
		app.bindService(new Intent(app, DownloadService.class),
				dlServiceConnection, 0);
	}

	public void startDownloadService(Context context) {

	}

	ServiceConnection dlServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			serverMsger = new Messenger(arg1);
			clientMsger = new Messenger(dlHandler);
			Message msg = new Message();
			msg.what = DownloadService.FLAG_MSG_CLONE_CLIENT_MESSEGER;
			msg.replyTo = clientMsger;
			try {
				serverMsger.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private boolean sendMsg(Message msg) {
		try {
			serverMsger.send(msg);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler dlHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case DownloadService.FLAG_MSG_DOWNLOAD_START:
				break;
			case DownloadService.FLAG_MSG_DOWNLOAD_STOP:
				break;
			case DownloadService.FLAG_MSG_DOWNLOAD_FINISH:
				break;
			case DownloadService.FLAG_MSG_DOWNLOAD_PROGRESS:
				break;
			}
			return false;
		}
	});

	public void registerDLListener(DownloadCallback listener) {
		if (listeners == null)
			listeners = new Vector<DownloadCallback>();
		listeners.add(listener);
	}

	public void unregisterDLListener(DownloadCallback listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	public boolean startDownload(DownloadModel model) {
		Message msg = new Message();
		msg.what = DownloadService.FLAG_MSG_DOWNLOAD_START;
		msg.obj = model;
		return sendMsg(msg);
	}

	public boolean stopDownload(DownloadModel model) {
		Message msg = new Message();
		msg.what = DownloadService.FLAG_MSG_DOWNLOAD_STOP;
		msg.obj = model;
		return sendMsg(msg);
	}

	public boolean pauseAll() {
		Message msg = new Message();
		msg.what = DownloadService.FLAG_CMD_CLIENT_PAUSE_ALL;
		return sendMsg(msg);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
