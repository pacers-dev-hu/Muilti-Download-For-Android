package com.pacers.md.server;

public class Settings implements Cloneable {
	public int maxThreadCount = 2;
	public int maxTotalThreadCount = 6;
	public int minDivideBlock = 5 * 1024 * 1024;

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	public void setMaxTotalThreadCount(int maxTotalThreadCount) {
		this.maxTotalThreadCount = maxTotalThreadCount;
	}

	public void setMinDivideBlock(int minDivideBlock) {
		this.minDivideBlock = minDivideBlock;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Settings mSettings = new Settings();
		mSettings.setMaxThreadCount(maxThreadCount);
		mSettings.setMaxTotalThreadCount(maxTotalThreadCount);
		mSettings.setMinDivideBlock(minDivideBlock);
		return mSettings;
	}

}
