package com.jp.dto;

/**
 * 封装json结果
 * @author Zhangning
 * @param <T>
 */
public class SeckillResult<T> {
	private boolean success;
	private T data;//对象数据
	private String error;
	
	public SeckillResult(boolean success, T data, String error) {
		super();
		this.success = success;
		this.data = data;
		this.error = error;
	}
	//ajax成功，返回数据
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	//ajax失败，返回原因
	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return "SeckillResult [success=" + success + ", data=" + data
				+ ", error=" + error + "]";
	}
}
