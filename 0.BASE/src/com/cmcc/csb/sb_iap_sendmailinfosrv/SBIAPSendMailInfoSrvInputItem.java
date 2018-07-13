
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_SendMailInfoSrvInputItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendMailInfoSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FROM_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SUBJECT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTENT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TO_ADDRESS" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}ToAddressCollection"/>
 *         &lt;element name="CC_TO_ADDRESS" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}CcToAddressCollection"/>
 *         &lt;element name="ATTACHMENT" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}AttachmentCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendMailInfoSrvInputItem", propOrder = {
    "fromaddress",
    "subject",
    "content",
    "type",
    "toaddress",
    "cctoaddress",
    "attachment"
})
public class SBIAPSendMailInfoSrvInputItem {

    @XmlElement(name = "FROM_ADDRESS", required = true, nillable = true)
    protected String fromaddress;
    @XmlElement(name = "SUBJECT", required = true, nillable = true)
    protected String subject;
    @XmlElement(name = "CONTENT", required = true, nillable = true)
    protected String content;
    @XmlElement(name = "TYPE", required = true, nillable = true)
    protected String type;
    @XmlElement(name = "TO_ADDRESS", required = true)
    protected ToAddressCollection toaddress;
    @XmlElement(name = "CC_TO_ADDRESS", required = true)
    protected CcToAddressCollection cctoaddress;
    @XmlElement(name = "ATTACHMENT", required = true)
    protected AttachmentCollection attachment;

    /**
     * ��ȡfromaddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFROMADDRESS() {
        return fromaddress;
    }

    /**
     * ����fromaddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFROMADDRESS(String value) {
        this.fromaddress = value;
    }

    /**
     * ��ȡsubject���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBJECT() {
        return subject;
    }

    /**
     * ����subject���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBJECT(String value) {
        this.subject = value;
    }

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
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYPE() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTYPE(String value) {
        this.type = value;
    }

    /**
     * ��ȡtoaddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ToAddressCollection }
     *     
     */
    public ToAddressCollection getTOADDRESS() {
        return toaddress;
    }

    /**
     * ����toaddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ToAddressCollection }
     *     
     */
    public void setTOADDRESS(ToAddressCollection value) {
        this.toaddress = value;
    }

    /**
     * ��ȡcctoaddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CcToAddressCollection }
     *     
     */
    public CcToAddressCollection getCCTOADDRESS() {
        return cctoaddress;
    }

    /**
     * ����cctoaddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CcToAddressCollection }
     *     
     */
    public void setCCTOADDRESS(CcToAddressCollection value) {
        this.cctoaddress = value;
    }

    /**
     * ��ȡattachment���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AttachmentCollection }
     *     
     */
    public AttachmentCollection getATTACHMENT() {
        return attachment;
    }

    /**
     * ����attachment���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentCollection }
     *     
     */
    public void setATTACHMENT(AttachmentCollection value) {
        this.attachment = value;
    }

}
