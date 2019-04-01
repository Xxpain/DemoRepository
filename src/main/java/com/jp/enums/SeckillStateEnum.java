package com.jp.enums;

import java.util.Iterator;

/**
 * ʹ��ö�ٱ������ǵĳ��������ֵ�
 * @author Zhangning
 */
public enum SeckillStateEnum {
	SUCCESS(1,"��ɱ�ɹ�"),
	END(0,"��ɱ����"),
	REPEAT_KILL(-1,"�ظ���ɱ"),
	INNER_ERROR(-2,"ϵͳ�쳣"),
	DATA_REWRITE(-3,"���ݴ۸�");
	private int state;//״̬��
	private String stateInfo;//״̬���Ӧ��ֵ
	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
}
//	public static SeckillStateEnum stateOf(int index){
//		for (SeckillStateEnum state : values()) {
//			if(state.getState()==index){
//				return state;
//			}
//		}
//		return null;
//	}
