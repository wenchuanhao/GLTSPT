package com.cdc.inter.client.ws.mail.service;

import java.util.List;

public interface IMailService {
	
	/**
	 * 邮件发送接口 
	 * @param to_addess 邮件地址
	 * @param cc_address 抄送地址
	 * @param subject   邮件主题
	 * @param content   邮件内容
	 * @param account 操作人
	 * @return
	 */
	public boolean SendMail(List<String>  to_addess,List<String> cc_address,String subject,String content,String account);



}
