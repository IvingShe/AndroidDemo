package com.neusoft.track.pojo;

public class Statition {
	private String mcc = null;
	private String mnc = null;
	private int cid = 0;
	private int lac = 0;

	public String getMcc() {
		return this.mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMnc() {
		return this.mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public int getCid() {
		return this.cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getLac() {
		return this.lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}
}
