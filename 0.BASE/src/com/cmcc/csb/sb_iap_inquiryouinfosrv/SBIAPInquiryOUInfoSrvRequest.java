
package com.cmcc.csb.sb_iap_inquiryouinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.cmcc.mss.msgheader.MsgHeader;


/**
 * <p>SB_IAP_InquiryOUInfoSrvRequest complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_InquiryOUInfoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="OU_GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="OU_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_InquiryOUInfoSrvRequest", propOrder = {
    "msgHeader",
    "ouguid",
    "ouid",
    "ouname",
    "flag",
    "startlastupdatedate",
    "endlastupdatedate"
})
public class SBIAPInquiryOUInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "OU_GUID", required = true, nillable = true)
    protected String ouguid;
    @XmlElement(name = "OU_ID", required = true, nillable = true)
    protected BigDecimal ouid;
    @XmlElement(name = "OU_NAME", required = true, nillable = true)
    protected String ouname;
    @XmlElement(name = "FLAG", required = true, nillable = true)
    protected String flag;
    @XmlElement(name = "START_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startlastupdatedate;
    @XmlElement(name = "END_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endlastupdatedate;

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
     * ��ȡouguid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUGUID() {
        return ouguid;
    }

    /**
     * ����ouguid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUGUID(String value) {
        this.ouguid = value;
    }

    /**
     * ��ȡouid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOUID() {
        return ouid;
    }

    /**
     * ����ouid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOUID(BigDecimal value) {
        this.ouid = value;
    }

    /**
     * ��ȡouname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUNAME() {
        return ouname;
    }

    /**
     * ����ouname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUNAME(String value) {
        this.ouname = value;
    }

    /**
     * ��ȡflag���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAG() {
        return flag;
    }

    /**
     * ����flag���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAG(String value) {
        this.flag = value;
    }

    /**
     * ��ȡstartlastupdatedate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTLASTUPDATEDATE() {
        return startlastupdatedate;
    }

    /**
     * ����startlastupdatedate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.startlastupdatedate = value;
    }

    /**
     * ��ȡendlastupdatedate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDLASTUPDATEDATE() {
        return endlastupdatedate;
    }

    /**
     * ����endlastupdatedate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.endlastupdatedate = value;
    }

}
