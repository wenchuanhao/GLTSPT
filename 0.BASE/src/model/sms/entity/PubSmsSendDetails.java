package model.sms.entity;

import java.util.Date;
/**
 * 短信审批下发记录表
 * @author Zengkai
 */
public class PubSmsSendDetails implements java.io.Serializable{
	/**
	 * 记录编码
	 */
	private String sendId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 下发端口(包含序列)
	 */
	private String portNo;
	/**
	 * 批次号:子端口号循环重置的批次
	 */
	private Integer seq;
	/**
	 * 短信下发内容
	 */
	private String smsContent;
	/**
	 * 工单类型
	 * 1：运营操作工单、2：数据需求工单、3：现网部署工单
	 */
	private String workorderType;
	/**
	 * 现网操作工单-工单信息表编号
	 */
	private String workorderId;
	/**
	 * 流程实例审批编号
	 */
	private String insactorId;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 超时时间
	 */
	private Date outTime;
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
	
	/**
	 * 审批状态(根据回复短信进行判断并更新)
	 * 0:未审批,1:已审批,2:超时审批(过期),3:审批代码不正确,5审核失败
	 */
	private String replyStatus;
	
	public PubSmsSendDetails(){
		
	}
	
	public PubSmsSendDetails(String sendId, String mobile, String portNo,
			Integer seq, String smsContent, String workorderType,
			String workorderId, String insactorId, Date createTime,
			Date outTime, Date sendTime, String sendStatus,String replyStatus) {
		this.sendId = sendId;
		this.mobile = mobile;
		this.portNo = portNo;
		this.seq = seq;
		this.smsContent = smsContent;
		this.workorderType = workorderType;
		this.workorderId = workorderId;
		this.insactorId = insactorId;
		this.createTime = createTime;
		this.outTime = outTime;
		this.sendTime = sendTime;
		this.sendStatus = sendStatus;
		this.replyStatus = replyStatus;
	}


	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	public String getWorkorderType() {
		return workorderType;
	}

	public void setWorkorderType(String workorderType) {
		this.workorderType = workorderType;
	}

	public String getWorkorderId() {
		return workorderId;
	}
	public void setWorkorderId(String workorderId) {
		this.workorderId = workorderId;
	}
	public String getInsactorId() {
		return insactorId;
	}
	public void setInsactorId(String insactorId) {
		this.insactorId = insactorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
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

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}
	
}
