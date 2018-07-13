
package com.cmcc.csb.sb_iap_inquiryouinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>SB_IAP_InquiryOUInfoSrvOutputItem complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_InquiryOUInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OU_GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_STATE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PARENT_OUGUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_FULLNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_LEVEL" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="OU_ORDER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATE_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_InquiryOUInfoSrvOutputItem", propOrder = {
    "ouguid",
    "ouid",
    "orgstate",
    "ouname",
    "parentouguid",
    "oufullname",
    "oulevel",
    "ouorder",
    "createtime",
    "lastupdatedate"
})
public class SBIAPInquiryOUInfoSrvOutputItem {

    @XmlElement(name = "OU_GUID", required = true, nillable = true)
    protected String ouguid;
    @XmlElement(name = "OU_ID", required = true, nillable = true)
    protected BigDecimal ouid;
    @XmlElement(name = "ORG_STATE", required = true, nillable = true)
    protected String orgstate;
    @XmlElement(name = "OU_NAME", required = true, nillable = true)
    protected String ouname;
    @XmlElement(name = "PARENT_OUGUID", required = true, nillable = true)
    protected String parentouguid;
    @XmlElement(name = "OU_FULLNAME", required = true, nillable = true)
    protected String oufullname;
    @XmlElement(name = "OU_LEVEL", required = true, nillable = true)
    protected BigDecimal oulevel;
    @XmlElement(name = "OU_ORDER", required = true, nillable = true)
    protected String ouorder;
    @XmlElement(name = "CREATE_TIME", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createtime;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

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
     * ��ȡorgstate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGSTATE() {
        return orgstate;
    }

    /**
     * ����orgstate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGSTATE(String value) {
        this.orgstate = value;
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
     * ��ȡparentouguid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTOUGUID() {
        return parentouguid;
    }

    /**
     * ����parentouguid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTOUGUID(String value) {
        this.parentouguid = value;
    }

    /**
     * ��ȡoufullname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUFULLNAME() {
        return oufullname;
    }

    /**
     * ����oufullname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUFULLNAME(String value) {
        this.oufullname = value;
    }

    /**
     * ��ȡoulevel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOULEVEL() {
        return oulevel;
    }

    /**
     * ����oulevel���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOULEVEL(BigDecimal value) {
        this.oulevel = value;
    }

    /**
     * ��ȡouorder���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUORDER() {
        return ouorder;
    }

    /**
     * ����ouorder���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUORDER(String value) {
        this.ouorder = value;
    }

    /**
     * ��ȡcreatetime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATETIME() {
        return createtime;
    }

    /**
     * ����createtime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATETIME(XMLGregorianCalendar value) {
        this.createtime = value;
    }

    /**
     * ��ȡlastupdatedate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTUPDATEDATE() {
        return lastupdatedate;
    }

    /**
     * ����lastupdatedate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.lastupdatedate = value;
    }

}
