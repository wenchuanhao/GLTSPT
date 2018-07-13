
package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_SendSMSInfoSrvOutputItem complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendSMSInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RETURN_CODE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RETURN_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEND_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendSMSInfoSrvOutputItem", propOrder = {
    "returncode",
    "returnmessage",
    "sendid"
})
public class SBIAPSendSMSInfoSrvOutputItem {

    @XmlElement(name = "RETURN_CODE", required = true, nillable = true)
    protected BigDecimal returncode;
    @XmlElement(name = "RETURN_MESSAGE", required = true, nillable = true)
    protected String returnmessage;
    @XmlElement(name = "SEND_ID", required = true, nillable = true)
    protected String sendid;

    /**
     * ��ȡreturncode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRETURNCODE() {
        return returncode;
    }

    /**
     * ����returncode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRETURNCODE(BigDecimal value) {
        this.returncode = value;
    }

    /**
     * ��ȡreturnmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETURNMESSAGE() {
        return returnmessage;
    }

    /**
     * ����returnmessage���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETURNMESSAGE(String value) {
        this.returnmessage = value;
    }

    /**
     * ��ȡsendid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSENDID() {
        return sendid;
    }

    /**
     * ����sendid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSENDID(String value) {
        this.sendid = value;
    }

}
