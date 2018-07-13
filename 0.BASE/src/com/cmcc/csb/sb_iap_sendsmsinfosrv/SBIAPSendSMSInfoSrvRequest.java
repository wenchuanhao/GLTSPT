
package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.cmcc.mss.msgheader.MsgHeader;


/**
 * <p>SB_IAP_SendSMSInfoSrvRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendSMSInfoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="SB_IAP_SendSMSInfoSrvInputCollection" type="{http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv}SB_IAP_SendSMSInfoSrvInputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendSMSInfoSrvRequest", propOrder = {
    "msgHeader",
    "sbiapSendSMSInfoSrvInputCollection"
})
public class SBIAPSendSMSInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "SB_IAP_SendSMSInfoSrvInputCollection", required = true)
    protected SBIAPSendSMSInfoSrvInputCollection sbiapSendSMSInfoSrvInputCollection;

    /**
     * ��ȡmsgHeader���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MsgHeader }
     *     
     */
    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    /**
     * ����msgHeader���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MsgHeader }
     *     
     */
    public void setMsgHeader(MsgHeader value) {
        this.msgHeader = value;
    }

    /**
     * ��ȡsbiapSendSMSInfoSrvInputCollection���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SBIAPSendSMSInfoSrvInputCollection }
     *     
     */
    public SBIAPSendSMSInfoSrvInputCollection getSBIAPSendSMSInfoSrvInputCollection() {
        return sbiapSendSMSInfoSrvInputCollection;
    }

    /**
     * ����sbiapSendSMSInfoSrvInputCollection���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SBIAPSendSMSInfoSrvInputCollection }
     *     
     */
    public void setSBIAPSendSMSInfoSrvInputCollection(SBIAPSendSMSInfoSrvInputCollection value) {
        this.sbiapSendSMSInfoSrvInputCollection = value;
    }

}
