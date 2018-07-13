
package com.cmcc.csb.sb_iap_checktokenkeyinsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CheckUserInfoOutItem complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CheckUserInfoOutItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="M_LOGIN_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="M_USER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckUserInfoOutItem", propOrder = {
    "mloginid",
    "musername"
})
public class CheckUserInfoOutItem {

    @XmlElement(name = "M_LOGIN_ID", required = true, nillable = true)
    protected String mloginid;
    @XmlElement(name = "M_USER_NAME", required = true, nillable = true)
    protected String musername;

    /**
     * ��ȡmloginid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMLOGINID() {
        return mloginid;
    }

    /**
     * ����mloginid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMLOGINID(String value) {
        this.mloginid = value;
    }

    /**
     * ��ȡmusername���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUSERNAME() {
        return musername;
    }

    /**
     * ����musername���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUSERNAME(String value) {
        this.musername = value;
    }

}
