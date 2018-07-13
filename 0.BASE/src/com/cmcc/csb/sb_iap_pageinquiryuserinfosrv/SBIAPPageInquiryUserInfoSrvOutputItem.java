
package com.cmcc.csb.sb_iap_pageinquiryuserinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>SB_IAP_PageInquiryUserInfoSrvOutputItem complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_PageInquiryUserInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EMPLOYEE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FULL_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_GUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OU_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="JOB_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WORK_PHONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TELE_PHONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMAIL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_BIRTHDAY" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SEX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TITLE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORDER_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_JOININ_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="USER_NATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_RELIGION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_QUIT_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CHANGE_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "SB_IAP_PageInquiryUserInfoSrvOutputItem", propOrder = {
    "employee",
    "userid",
    "fullname",
    "ouguid",
    "ouid",
    "jobtype",
    "usertype",
    "workphone",
    "telephone",
    "email",
    "address",
    "userbirthday",
    "sex",
    "title",
    "orderid",
    "userjoinindate",
    "usernation",
    "userreligion",
    "userquitdate",
    "changetime",
    "createtime",
    "lastupdatedate"
})
public class SBIAPPageInquiryUserInfoSrvOutputItem {

    @XmlElement(name = "EMPLOYEE", required = true, nillable = true)
    protected String employee;
    @XmlElement(name = "USER_ID", required = true, nillable = true)
    protected String userid;
    @XmlElement(name = "FULL_NAME", required = true, nillable = true)
    protected String fullname;
    @XmlElement(name = "OU_GUID", required = true, nillable = true)
    protected String ouguid;
    @XmlElement(name = "OU_ID", required = true, nillable = true)
    protected BigDecimal ouid;
    @XmlElement(name = "JOB_TYPE", required = true, nillable = true)
    protected String jobtype;
    @XmlElement(name = "USER_TYPE", required = true, nillable = true)
    protected String usertype;
    @XmlElement(name = "WORK_PHONE", required = true, nillable = true)
    protected String workphone;
    @XmlElement(name = "TELE_PHONE", required = true, nillable = true)
    protected String telephone;
    @XmlElement(name = "EMAIL", required = true, nillable = true)
    protected String email;
    @XmlElement(name = "ADDRESS", required = true, nillable = true)
    protected String address;
    @XmlElement(name = "USER_BIRTHDAY", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar userbirthday;
    @XmlElement(name = "SEX", required = true, nillable = true)
    protected String sex;
    @XmlElement(name = "TITLE", required = true, nillable = true)
    protected String title;
    @XmlElement(name = "ORDER_ID", required = true, nillable = true)
    protected String orderid;
    @XmlElement(name = "USER_JOININ_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar userjoinindate;
    @XmlElement(name = "USER_NATION", required = true, nillable = true)
    protected String usernation;
    @XmlElement(name = "USER_RELIGION", required = true, nillable = true)
    protected String userreligion;
    @XmlElement(name = "USER_QUIT_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar userquitdate;
    @XmlElement(name = "CHANGE_TIME", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar changetime;
    @XmlElement(name = "CREATE_TIME", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createtime;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

    /**
     * ��ȡemployee���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEE() {
        return employee;
    }

    /**
     * ����employee���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEE(String value) {
        this.employee = value;
    }

    /**
     * ��ȡuserid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERID() {
        return userid;
    }

    /**
     * ����userid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERID(String value) {
        this.userid = value;
    }

    /**
     * ��ȡfullname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFULLNAME() {
        return fullname;
    }

    /**
     * ����fullname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFULLNAME(String value) {
        this.fullname = value;
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
     * ��ȡjobtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJOBTYPE() {
        return jobtype;
    }

    /**
     * ����jobtype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJOBTYPE(String value) {
        this.jobtype = value;
    }

    /**
     * ��ȡusertype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERTYPE() {
        return usertype;
    }

    /**
     * ����usertype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERTYPE(String value) {
        this.usertype = value;
    }

    /**
     * ��ȡworkphone���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWORKPHONE() {
        return workphone;
    }

    /**
     * ����workphone���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWORKPHONE(String value) {
        this.workphone = value;
    }

    /**
     * ��ȡtelephone���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEPHONE() {
        return telephone;
    }

    /**
     * ����telephone���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEPHONE(String value) {
        this.telephone = value;
    }

    /**
     * ��ȡemail���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * ����email���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * ��ȡaddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDRESS() {
        return address;
    }

    /**
     * ����address���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDRESS(String value) {
        this.address = value;
    }

    /**
     * ��ȡuserbirthday���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUSERBIRTHDAY() {
        return userbirthday;
    }

    /**
     * ����userbirthday���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUSERBIRTHDAY(XMLGregorianCalendar value) {
        this.userbirthday = value;
    }

    /**
     * ��ȡsex���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEX() {
        return sex;
    }

    /**
     * ����sex���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEX(String value) {
        this.sex = value;
    }

    /**
     * ��ȡtitle���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTITLE() {
        return title;
    }

    /**
     * ����title���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTITLE(String value) {
        this.title = value;
    }

    /**
     * ��ȡorderid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDERID() {
        return orderid;
    }

    /**
     * ����orderid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDERID(String value) {
        this.orderid = value;
    }

    /**
     * ��ȡuserjoinindate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUSERJOININDATE() {
        return userjoinindate;
    }

    /**
     * ����userjoinindate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUSERJOININDATE(XMLGregorianCalendar value) {
        this.userjoinindate = value;
    }

    /**
     * ��ȡusernation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERNATION() {
        return usernation;
    }

    /**
     * ����usernation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERNATION(String value) {
        this.usernation = value;
    }

    /**
     * ��ȡuserreligion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERRELIGION() {
        return userreligion;
    }

    /**
     * ����userreligion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERRELIGION(String value) {
        this.userreligion = value;
    }

    /**
     * ��ȡuserquitdate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUSERQUITDATE() {
        return userquitdate;
    }

    /**
     * ����userquitdate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUSERQUITDATE(XMLGregorianCalendar value) {
        this.userquitdate = value;
    }

    /**
     * ��ȡchangetime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCHANGETIME() {
        return changetime;
    }

    /**
     * ����changetime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCHANGETIME(XMLGregorianCalendar value) {
        this.changetime = value;
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
