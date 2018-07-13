package model.sms.entity;

import java.util.Date;
/**
 * 短信审批回复记录表
 * @author Zengkai
 */
public class PubSmsReplyDetails implements java.io.Serializable{
	/**
	 * 记录编码
	 */
	private String replyId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 回复端口(包含序列)
	 */
	private String portNo;
	 
	/**
	 * 下发编码
	 */
	private String sendId;
	/**
	 * 回复时间
	 */
	private Date replyTime;
	/**
	 * 回复内容
	 */
	private String replyContent;
	/**
	 * 审批代码-根据内容解析并指定处理人
	 */
	private String replyCode;
	/**
	 * 审批意见-根据内容解析并保存意见，为空即为同意
	 */
	private String replyOpinion;
	/**
	 * 审批状态
	 * 0:未审批,1:已审批,2:超时审批(过期),3:审批代码不正确,4:查无审批下发记录,5审核失败
	 */
	private String replyStatus;
	
	public PubSmsReplyDetails(){
		
	}
	
	public PubSmsReplyDetails(String replyId, String mobile, String portNo,
			String sendId, Date replyTime, String replyContent,
			String replyCode, String replyOpinion, String replyStatus) {
		this.replyId = replyId;
		this.mobile = mobile;
		this.portNo = portNo;
		this.sendId = sendId;
		this.replyTime = replyTime;
		this.replyContent = replyContent;
		this.replyCode = replyCode;
		this.replyOpinion = replyOpinion;
		this.replyStatus = replyStatus;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
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
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	public String getReplyOpinion() {
		return replyOpinion;
	}
	public void setReplyOpinion(String replyOpinion) {
		this.replyOpinion = replyOpinion;
	}
	public String getReplyStatus() {
		return replyStatus;
	}
	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}
	
	
}
