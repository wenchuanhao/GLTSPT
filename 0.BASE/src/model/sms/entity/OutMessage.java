package model.sms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OutMessage implements Serializable {

    /**
     * 合法
     */
    public final static String LEGALITY = "1";
    /**
     *短信业务编码：省内
     */
    public final static String SMCC_PROVINCE = "1";
    /**
     * 未处理
     */
    public final static String UN_PROCESS = "0";
    /**
     * 中文短信
     */
    public final static String CHINESE_MESSAGE = "1";
    /**
     * 要求回执
     */
    public final static String REGISTER_DELIVER = "1";
    /**
     * 不要求回执
     */
    public final static String REGISTER_FREE = "0";

    /**
     * 任务ID
     */
    public String id;

    /**
     * 流水号
     */
    public String serialNo;

    /**
     * 订单ID
     */
    public String orderId;

    /**
     * 产品ID
     */
    public String productId;

    /**
     * 产品代码
     */
    public String productCode;

    /**
     * 计费ID
     */
    public int feeId;

    /**
     * 接收短信号码
     */
    public String serviceNo;

    /**
     * 内容
     */
    public String message;

    /**
     * 要求发送时间
     */
    public Date requestTime;

    /**
     * 语言标志
     */
    public String langFlag;

    /**
     * 是否需要状态报告
     */
    public String registerDeliver;

    /**
     * 发送短信号码
     */
    public String sendNo;

    /**
     * 省内标志
     */
    public String provinceFlag;

    /**
     * 发送状态
     */
    public String status;

    /**
     * 发送进程ID
     */
    public String processId;

    /**
     * 发送时间
     */
    public java.util.Date sendTime;

    /**
     * 短消息ID
     */
    public String messageId;

    /**
     * 接收到状态报告时间
     */
    public java.util.Date deliverTime;

    /**
     * 合法标志
     */
    public String validFlag;

    /**
     * 费率
     */
    public BigDecimal feeUnit;

    /**
     * 计费用户类型
     */
    public int feeUserType;

    /**
     * 被计费用户的号码
     */
    public String feeTerminal;

    /**
     * 资费类别
     */
    public String feeType;

    /**
     * 取任务时间
     */
    public Date getTaskTime;

    /**
     * 接口识别码
     */
    public String operCode;

    /**
     * 优先级
     */
    public int priority;

    /**
     * 业务码
     */
    public String serviceId;

    public List getDestinations() {
        return Arrays.asList(serviceNo.split(","));
    }

    public OutMessage() {

    }

    public OutMessage(String serialNo, String orderId, String productId,
                      String productCode, String serviceNo, String message,
                      String sendNo, BigDecimal fee, int feeUserType, String feeType,
                      String operCode, String serviceId) {
        //this.id = SerialNo.getUNID();
        this.serialNo = serialNo;
        this.orderId = orderId;
        this.productId = productId;
        this.productCode = productCode;
        this.feeId = 0;
        this.serviceNo = serviceNo;
        this.message = message;
        this.requestTime = new Date();
        this.langFlag = CHINESE_MESSAGE;
        this.registerDeliver = REGISTER_DELIVER;
        this.sendNo = sendNo;
        this.provinceFlag = SMCC_PROVINCE;
        this.status = UN_PROCESS;
        this.feeUnit = fee;
        this.feeUserType = feeUserType;
        this.feeTerminal = serviceNo;
        this.feeType = feeType;
        this.operCode = operCode;
        this.serviceId = serviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getLangFlag() {
        return langFlag;
    }

    public void setLangFlag(String langFlag) {
        this.langFlag = langFlag;
    }

    public String getRegisterDeliver() {
        return registerDeliver;
    }

    public void setRegisterDeliver(String registerDeliver) {
        this.registerDeliver = registerDeliver;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public String getProvinceFlag() {
        return provinceFlag;
    }

    public void setProvinceFlag(String provinceFlag) {
        this.provinceFlag = provinceFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public BigDecimal getFeeUnit() {
        return feeUnit;
    }

    public void setFeeUnit(BigDecimal feeUnit) {
        this.feeUnit = feeUnit;
    }

    public int getFeeUserType() {
        return feeUserType;
    }

    public void setFeeUserType(int feeUserType) {
        this.feeUserType = feeUserType;
    }

    public String getFeeTerminal() {
        return feeTerminal;
    }

    public void setFeeTerminal(String feeTerminal) {
        this.feeTerminal = feeTerminal;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Date getGetTaskTime() {
        return getTaskTime;
    }

    public void setGetTaskTime(Date getTaskTime) {
        this.getTaskTime = getTaskTime;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}