package com.pacers.md.server;

import com.pacers.md.server.model.DownloadModel;

public interface DownloadCallback {
	public void onFinished(DownloadModel model);

	public void onStarted(DownloadModel model);

	public void onStoped(DownloadModel model);

	public void onError(DownloadModel model, int code);

	public void onProgress(DownloadModel model, long length, float rate);
}
