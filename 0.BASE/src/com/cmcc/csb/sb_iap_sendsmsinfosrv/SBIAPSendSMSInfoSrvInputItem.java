
package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>SB_IAP_SendSMSInfoSrvInputItem complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendSMSInfoSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CONTENT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RECEIVER" type="{http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv}ReceiverCollection"/>
 *         &lt;element name="SEND_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendSMSInfoSrvInputItem", propOrder = {
    "content",
    "receiver",
    "sendtime"
})
public class SBIAPSendSMSInfoSrvInputItem {

    @XmlElement(name = "CONTENT", required = true, nillable = true)
    protected String content;
    @XmlElement(name = "RECEIVER", required = true)
    protected ReceiverCollection receiver;
    @XmlElement(name = "SEND_TIME", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sendtime;

    /**
     * ��ȡcontent���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTENT() {
        return content;
    }

    /**
     * ����content���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTENT(String value) {
        this.content = value;
    }

    /**
     * ��ȡreceiver���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ReceiverCollection }
     *     
     */
    public ReceiverCollection getRECEIVER() {
        return receiver;
    }

    /**
     * ����receiver���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiverCollection }
     *     
     */
    public void setRECEIVER(ReceiverCollection value) {
        this.receiver = value;
    }

    /**
     * ��ȡsendtime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSENDTIME() {
        return sendtime;
    }

    /**
     * ����sendtime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSENDTIME(XMLGregorianCalendar value) {
        this.sendtime = value;
    }

}
