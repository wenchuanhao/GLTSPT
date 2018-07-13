
package com.cmcc.mss.msgheader;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>MsgHeader complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MsgHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SOURCE_SYSTEM_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SOURCE_SYSTEM_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SUBMIT_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PAGE_SIZE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CURRENT_PAGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TOTAL_RECORD" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PROVINCE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ENVIRONMENT_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgHeader", propOrder = {
    "sourcesystemid",
    "sourcesystemname",
    "userid",
    "username",
    "submitdate",
    "pagesize",
    "currentpage",
    "totalrecord",
    "provincecode",
    "environmentname"
})
public class MsgHeader {

    @XmlElement(name = "SOURCE_SYSTEM_ID", required = true, nillable = true)
    protected String sourcesystemid;
    @XmlElement(name = "SOURCE_SYSTEM_NAME", required = true, nillable = true)
    protected String sourcesystemname;
    @XmlElement(name = "USER_ID", required = true, nillable = true)
    protected String userid;
    @XmlElement(name = "USER_NAME", required = true, nillable = true)
    protected String username;
    @XmlElement(name = "SUBMIT_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar submitdate;
    @XmlElement(name = "PAGE_SIZE", required = true, nillable = true)
    protected BigDecimal pagesize;
    @XmlElement(name = "CURRENT_PAGE", required = true, nillable = true)
    protected BigDecimal currentpage;
    @XmlElement(name = "TOTAL_RECORD", required = true, nillable = true)
    protected BigDecimal totalrecord;
    @XmlElement(name = "PROVINCE_CODE", required = true, nillable = true)
    protected String provincecode;
    @XmlElement(name = "ENVIRONMENT_NAME", required = true, nillable = true)
    protected String environmentname;

    /**
     * ��ȡsourcesystemid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCESYSTEMID() {
        return sourcesystemid;
    }

    /**
     * ����sourcesystemid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCESYSTEMID(String value) {
        this.sourcesystemid = value;
    }

    /**
     * ��ȡsourcesystemname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOURCESYSTEMNAME() {
        return sourcesystemname;
    }

    /**
     * ����sourcesystemname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOURCESYSTEMNAME(String value) {
        this.sourcesystemname = value;
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
     * ��ȡusername���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERNAME() {
        return username;
    }

    /**
     * ����username���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERNAME(String value) {
        this.username = value;
    }

    /**
     * ��ȡsubmitdate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSUBMITDATE() {
        return submitdate;
    }

    /**
     * ����submitdate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSUBMITDATE(XMLGregorianCalendar value) {
        this.submitdate = value;
    }

    /**
     * ��ȡpagesize���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAGESIZE() {
        return pagesize;
    }

    /**
     * ����pagesize���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAGESIZE(BigDecimal value) {
        this.pagesize = value;
    }

    /**
     * ��ȡcurrentpage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCURRENTPAGE() {
        return currentpage;
    }

    /**
     * ����currentpage���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCURRENTPAGE(BigDecimal value) {
        this.currentpage = value;
    }

    /**
     * ��ȡtotalrecord���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALRECORD() {
        return totalrecord;
    }

    /**
     * ����totalrecord���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALRECORD(BigDecimal value) {
        this.totalrecord = value;
    }

    /**
     * ��ȡprovincecode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCECODE() {
        return provincecode;
    }

    /**
     * ����provincecode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCECODE(String value) {
        this.provincecode = value;
    }

    /**
     * ��ȡenvironmentname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENVIRONMENTNAME() {
        return environmentname;
    }

    /**
     * ����environmentname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENVIRONMENTNAME(String value) {
        this.environmentname = value;
    }

    public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<msgHeader>");
		sb.append("  <sourcesystemid>" + sourcesystemid + "</sourcesystemid>");
		sb.append("  <sourcesystemname>" + sourcesystemname + "</sourcesystemname>");
		sb.append("  <userid>" + userid + "</userid>");
		sb.append("  <username>" + username + "</username>");
		sb.append("  <submitdate>" + submitdate + "</submitdate>");
		sb.append("  <pagesize>" + pagesize + "</pagesize>");
		sb.append("  <currentpage>" + currentpage + "</currentpage>");
		sb.append("  <totalrecord>" + totalrecord + "</totalrecord>");
		sb.append("  <provincecode>" + provincecode + "</provincecode>");
		sb.append("  <environmentname>" + environmentname + "</environmentname>");
		sb.append("</msgHeader>");
		return sb.toString();
	}
}
