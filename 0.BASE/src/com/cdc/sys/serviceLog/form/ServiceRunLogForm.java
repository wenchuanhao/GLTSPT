package com.cdc.sys.serviceLog.form;

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

public class ServiceRunLogForm extends PageQueryForm{
	private String id;//主键ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSendMessage() {
		return sendMessage;
	}
	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getInterType() {
		return interType;
	}
	public void setInterType(Integer interType) {
		this.interType = interType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	private Integer type;//接口类型 0:ws接口1:文件接口2:sql接口
    private String sendMessage ;//发送发文
    private String returnMessage ;//返回发文
    private int status;//1 失败   ，0成功
    private Integer interType;//接口类型  0:客户端1:服务端
    private Date createDate;;//创建时间
    private String serviceId;//服务ID
    private String dataId;//数据提供ID
}
