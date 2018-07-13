
package com.cmcc.csb.sb_iap_managertasktodosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_ManagerTaskToDoSrvOutputItem complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_ManagerTaskToDoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TASK_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RETURNS" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MSGERROR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_ManagerTaskToDoSrvOutputItem", propOrder = {
    "taskid",
    "returns",
    "msgerror"
})
public class SBIAPManagerTaskToDoSrvOutputItem {

    @XmlElement(name = "TASK_ID", required = true, nillable = true)
    protected String taskid;
    @XmlElement(name = "RETURNS", required = true, type = Boolean.class, nillable = true)
    protected Boolean returns;
    @XmlElement(name = "MSGERROR", required = true, nillable = true)
    protected String msgerror;

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
     * ��ȡreturns���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRETURNS() {
        return returns;
    }

    /**
     * ����returns���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRETURNS(Boolean value) {
        this.returns = value;
    }

    /**
     * ��ȡmsgerror���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSGERROR() {
        return msgerror;
    }

    /**
     * ����msgerror���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSGERROR(String value) {
        this.msgerror = value;
    }

}
