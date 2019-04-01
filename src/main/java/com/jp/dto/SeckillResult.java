package com.jp.dto;

/**
 * ��װjson���
 * @author Zhangning
 * @param <T>
 */
public class SeckillResult<T> {
	private boolean success;
	private T data;//��������
	private String error;
	
	public SeckillResult(boolean success, T data, String error) {
		super();
		this.success = success;
		this.data = data;
		this.error = error;
	}
	//ajax�ɹ�����������
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	//ajaxʧ�ܣ�����ԭ��
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
