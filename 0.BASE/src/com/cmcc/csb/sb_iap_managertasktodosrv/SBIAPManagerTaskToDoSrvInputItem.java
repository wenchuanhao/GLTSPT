
package com.cmcc.csb.sb_iap_managertasktodosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>SB_IAP_ManagerTaskToDoSrvInputItem complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_ManagerTaskToDoSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TASK_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FROM_EMPLOYEE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TO_EMPLOYEE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SUBJECT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_URL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATE_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="WARNING_TIME" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TASK_TYPE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ACTION" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_ManagerTaskToDoSrvInputItem", propOrder = {
    "taskid",
    "fromemployeeid",
    "toemployeeid",
    "subject",
    "taskurl",
    "createtime",
    "warningtime",
    "tasktype",
    "action"
})
public class SBIAPManagerTaskToDoSrvInputItem {

    @XmlElement(name = "TASK_ID", required = true, nillable = true)
    protected String taskid;
    @XmlElement(name = "FROM_EMPLOYEE_ID", required = true, nillable = true)
    protected String fromemployeeid;
    @XmlElement(name = "TO_EMPLOYEE_ID", required = true, nillable = true)
    protected String toemployeeid;
    @XmlElement(name = "SUBJECT", required = true, nillable = true)
    protected String subject;
    @XmlElement(name = "TASK_URL", required = true, nillable = true)
    protected String taskurl;
    @XmlElement(name = "CREATE_TIME", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createtime;
    @XmlElement(name = "WARNING_TIME", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar warningtime;
    @XmlElement(name = "TASK_TYPE", required = true, nillable = true)
    protected BigDecimal tasktype;
    @XmlElement(name = "ACTION", required = true, nillable = true)
    protected BigDecimal action;

    /**
     * ��ȡtaskid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKID() {
        return taskid;
    }

    /**
     * ����taskid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKID(String value) {
        this.taskid = value;
    }

    /**
     * ��ȡfromemployeeid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFROMEMPLOYEEID() {
        return fromemployeeid;
    }

    /**
     * ����fromemployeeid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFROMEMPLOYEEID(String value) {
        this.fromemployeeid = value;
    }

    /**
     * ��ȡtoemployeeid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOEMPLOYEEID() {
        return toemployeeid;
    }

    /**
     * ����toemployeeid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOEMPLOYEEID(String value) {
        this.toemployeeid = value;
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
     * ��ȡtaskurl���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKURL() {
        return taskurl;
    }

    /**
     * ����taskurl���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKURL(String value) {
        this.taskurl = value;
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
     * ��ȡwarningtime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getWARNINGTIME() {
        return warningtime;
    }

    /**
     * ����warningtime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setWARNINGTIME(XMLGregorianCalendar value) {
        this.warningtime = value;
    }

    /**
     * ��ȡtasktype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTASKTYPE() {
        return tasktype;
    }

    /**
     * ����tasktype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTASKTYPE(BigDecimal value) {
        this.tasktype = value;
    }

    /**
     * ��ȡaction���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getACTION() {
        return action;
    }

    /**
     * ����action���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setACTION(BigDecimal value) {
        this.action = value;
    }

}
