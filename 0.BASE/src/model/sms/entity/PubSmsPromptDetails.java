package model.sms.entity;

import java.util.Date;
/**
 * 短信审批提示记录表
 * @author Zengkai
 */
public class PubSmsPromptDetails implements java.io.Serializable{
	/**
	 * 记录编码
	 */
	private String promptId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 下发端口(包含序列)
	 */
	private String portNo;
	/**
	 * 回复编码记录（外键）
	 */
	private String replyId;
	/**
	 * 短信下发内容
	 */
	private String smsContent;
	/**
	 * 下发时间
	 * 短信在早8晚10时段内发送。如果超过该时段，默认延迟到下一日早8点发送。
	 */
	private Date sendTime;
	/**
	 * 短信下发状态
	 * 0:短信隔天发送,1：短信下发成功,2,短信下发失败
	 */
	private String sendStatus;
	
	public PubSmsPromptDetails(){
		
	}
	
	public PubSmsPromptDetails(String promptId, String mobile, String portNo,String replyId,
			String smsContent, Date sendTime, String sendStatus) {
		this.promptId = promptId;
		this.mobile = mobile;
		this.portNo = portNo;
		this.replyId = replyId;
		this.smsContent = smsContent;
		this.sendTime = sendTime;
		this.sendStatus = sendStatus;
	}
	public String getPromptId() {
		return promptId;
	}
	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPortNo() {
		return portNo;
	}
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}
	
	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	
}
