
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.cmcc.mss.msgheader.MsgHeader;


/**
 * <p>SB_IAP_SendMailInfoSrvRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendMailInfoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="SB_IAP_SendMailInfoSrvInputCollection" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}SB_IAP_SendMailInfoSrvInputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendMailInfoSrvRequest", propOrder = {
    "msgHeader",
    "sbiapSendMailInfoSrvInputCollection"
})
public class SBIAPSendMailInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "SB_IAP_SendMailInfoSrvInputCollection", required = true)
    protected SBIAPSendMailInfoSrvInputCollection sbiapSendMailInfoSrvInputCollection;

    /**
     * 获取msgHeader属性的值。
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
     * 设置msgHeader属性的值。
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
     * 获取sbiapSendMailInfoSrvInputCollection属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SBIAPSendMailInfoSrvInputCollection }
     *     
     */
    public SBIAPSendMailInfoSrvInputCollection getSBIAPSendMailInfoSrvInputCollection() {
        return sbiapSendMailInfoSrvInputCollection;
    }

    /**
     * 设置sbiapSendMailInfoSrvInputCollection属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SBIAPSendMailInfoSrvInputCollection }
     *     
     */
    public void setSBIAPSendMailInfoSrvInputCollection(SBIAPSendMailInfoSrvInputCollection value) {
        this.sbiapSendMailInfoSrvInputCollection = value;
    }

}
