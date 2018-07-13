package com.cdc.inter.client.ws.sms.service;

import java.util.Date;
import java.util.List;

public interface ISmsService {

	/**
	 * 短信批量发送
	 * @param moiles 手机号码列表
	 * @param msg	短信内容
	 * @param sendTime 发送时间，为空则即时发送
	 * @return
	 */
	public boolean sendMessage(List<String> moiles,String msg,Date sendTime);
	
	/**
	 * 即时发送短信
	 * @param moile 手机号码
	 * @param msg	发送内容
	 * @return
	 */
	public boolean sendMessage(String moile,String msg);

	/**
	 * 下发验证码
	 * @param mobile
	 */
	public boolean sendSms(String mobile);

	/**
	 * 校验验证码
	 * @param mobile
	 * @param smsCode
	 */
	public boolean checkSms(String mobile, String smsCode);
}
