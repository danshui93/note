package com.zf.po;

public class ResultInfo<T> {
	private int resultCode=1;
	private T result;
	public ResultInfo(int resutCode, T result) {
		super();
		this.resultCode = resutCode;
		this.result = result;
	}
	
	public ResultInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resutCode) {
		this.resultCode = resutCode;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}
