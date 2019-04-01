package com.jp.exception;

/**
 * 重复秒杀异常
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
	//下面2个是主要的
	public RepeatKillException(String message) {
		super(message);
	}
	public RepeatKillException(Throwable cause) {
		super(cause);
	}

}
