package com.cdc.dc.account.model;

import java.io.Serializable;


/**
 * 
 * @author xms
 *审批流信息（excel导入）
 */
public class FlowInfo implements Serializable{
	private String id;//主键ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSubmiter() {
		return submiter;
	}
	public void setSubmiter(String submiter) {
		this.submiter = submiter;
	}
	public String getSubmiterDate() {
		return submiterDate;
	}
	public void setSubmiterDate(String submiterDate) {
		this.submiterDate = submiterDate;
	}
	public String getSjlApprover() {
		return sjlApprover;
	}
	public void setSjlApprover(String sjlApprover) {
		this.sjlApprover = sjlApprover;
	}
	public String getSjlApproveDate() {
		return sjlApproveDate;
	}
	public void setSjlApproveDate(String sjlApproveDate) {
		this.sjlApproveDate = sjlApproveDate;
	}
	public String getBmldApprover() {
		return bmldApprover;
	}
	public void setBmldApprover(String bmldApprover) {
		this.bmldApprover = bmldApprover;
	}
	public String getBmldApproveDate() {
		return bmldApproveDate;
	}
	public void setBmldApproveDate(String bmldApproveDate) {
		this.bmldApproveDate = bmldApproveDate;
	}
	public String getCskjApprover() {
		return cskjApprover;
	}
	public void setCskjApprover(String cskjApprover) {
		this.cskjApprover = cskjApprover;
	}
	public String getCskjApproveDate() {
		return cskjApproveDate;
	}
	public void setCskjApproveDate(String cskjApproveDate) {
		this.cskjApproveDate = cskjApproveDate;
	}
	public String getSwkjApprover() {
		return swkjApprover;
	}
	public void setSwkjApprover(String swkjApprover) {
		this.swkjApprover = swkjApprover;
	}
	public String getSwkjApproveDate() {
		return swkjApproveDate;
	}
	public void setSwkjApproveDate(String swkjApproveDate) {
		this.swkjApproveDate = swkjApproveDate;
	}
	public String getZbkjApprover() {
		return zbkjApprover;
	}
	public void setZbkjApprover(String zbkjApprover) {
		this.zbkjApprover = zbkjApprover;
	}
	public String getZbkjApproveDate() {
		return zbkjApproveDate;
	}
	public void setZbkjApproveDate(String zbkjApproveDate) {
		this.zbkjApproveDate = zbkjApproveDate;
	}
	public String getCwjlApprover() {
		return cwjlApprover;
	}
	public void setCwjlApprover(String cwjlApprover) {
		this.cwjlApprover = cwjlApprover;
	}
	public String getCwjlApproveDate() {
		return cwjlApproveDate;
	}
	public void setCwjlApproveDate(String cwjlApproveDate) {
		this.cwjlApproveDate = cwjlApproveDate;
	}
	public String getNjldApprover() {
		return njldApprover;
	}
	public void setNjldApprover(String njldApprover) {
		this.njldApprover = njldApprover;
	}
	public String getNjldApproveDate() {
		return njldApproveDate;
	}
	public void setNjldApproveDate(String njldApproveDate) {
		this.njldApproveDate = njldApproveDate;
	}
	public String getScwApprover() {
		return scwApprover;
	}
	public void setScwApprover(String scwApprover) {
		this.scwApprover = scwApprover;
	}
	public String getScwApproveDate() {
		return scwApproveDate;
	}
	public void setScwApproveDate(String scwApproveDate) {
		this.scwApproveDate = scwApproveDate;
	}
	public String getCnfkApprover() {
		return cnfkApprover;
	}
	public void setCnfkApprover(String cnfkApprover) {
		this.cnfkApprover = cnfkApprover;
	}
	public String getCnfkApproveDate() {
		return cnfkApproveDate;
	}
	public void setCnfkApproveDate(String cnfkApproveDate) {
		this.cnfkApproveDate = cnfkApproveDate;
	}

	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	private String orderId; //报账编号
	private String submiter;//提交电子报账单
	private String submiterDate; //提交电子报账单时间
	private String sjlApprover;//室经理审批人
	private String sjlApproveDate; //室经理审批时间
	private String bmldApprover;//部门领导审批人
	private String bmldApproveDate; //部门领导审批时间
	private String cskjApprover;//初审会计审批人
	private String cskjApproveDate; //初审会计审批时间
	private String swkjApprover;//税务会计审批人
	private String swkjApproveDate; //税务会计审批时间
	private String zbkjApprover;//主办会计审批人
	private String zbkjApproveDate; //主办会计审批时间
	private String cwjlApprover;//财务经理审批人
	private String cwjlApproveDate; //财务经理审批时间
	private String njldApprover;//南基领导审批人
	private String njldApproveDate; //南基领导审批时间
	private String scwApprover;//省财务审批人
	private String scwApproveDate; //省财务审批时间
	private String cnfkApprover;//出纳付款人
	private String cnfkApproveDate; //出纳付款时间
	private String news;//最新导入标示

}
