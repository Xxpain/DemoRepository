package com.jp.exception;

/**
 * �ظ���ɱ�쳣
 * @author Zhangning
 */
public class RepeatKillException extends RuntimeException {
	public RepeatKillException() {
		super();
	}
	public RepeatKillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}
	//����2������Ҫ��
	public RepeatKillException(String message) {
		super(message);
	}
	public RepeatKillException(Throwable cause) {
		super(cause);
	}

}
