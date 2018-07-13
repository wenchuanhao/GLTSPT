package model.sms.entity;

import java.util.Date;

/**
 * 短信审批审批代码对应处理人表
 * @author Zengkai
 */
public class PubSmsReplycodeSysuser implements java.io.Serializable{

	/**
	 * 记录编码
	 */
	private String rsId;
	 
	/**
	 * 审批代码-根据内容解析并指定处理人
	 */
	private String replycode;
	private String userId;//用户id
	private String userName;//用户名
	/**
	 * 下发编码
	 */
	private String sendId;
	/**
	 * 节点流向编号
	 */
	private String transitionId;
	/**
	 * 节点名称编码
	 */
	private String flowNodeId;
	 
	/**
	 * 目标节点
	 */
	private String toNode;
	/**
	 * 目标节点名称
	 */
	private String toNodeName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	public PubSmsReplycodeSysuser() {
		
	}
 
	public PubSmsReplycodeSysuser(String rsId, String replycode, String userId,
			String userName, String sendId, String transitionId,
			String flowNodeId, String toNode, String toNodeName, Date createTime) {
		this.rsId = rsId;
		this.replycode = replycode;
		this.userId = userId;
		this.userName = userName;
		this.sendId = sendId;
		this.transitionId = transitionId;
		this.flowNodeId = flowNodeId;
		this.toNode = toNode;
		this.toNodeName = toNodeName;
		this.createTime = createTime;
	}

	public String getRsId() {
		return rsId;
	}
	public void setRsId(String rsId) {
		this.rsId = rsId;
	}
	public String getReplycode() {
		return replycode;
	}
	public void setReplycode(String replycode) {
		this.replycode = replycode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	
	public String getTransitionId() {
		return transitionId;
	}
	public void setTransitionId(String transitionId) {
		this.transitionId = transitionId;
	}
	public String getFlowNodeId() {
		return flowNodeId;
	}
	public void setFlowNodeId(String flowNodeId) {
		this.flowNodeId = flowNodeId;
	}
	public String getToNode() {
		return toNode;
	}
	public void setToNode(String toNode) {
		this.toNode = toNode;
	}
	public String getToNodeName() {
		return toNodeName;
	}
	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
