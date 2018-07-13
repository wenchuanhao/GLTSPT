
package com.cmcc.csb.sb_iap_ssologininsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.cmcc.mss.msgheader.MsgHeader;


/**
 * <p>SB_IAP_SSOLoginInSrvRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SSOLoginInSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="IP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOGIN_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PASSWORD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SSOLoginInSrvRequest", propOrder = {
    "msgHeader",
    "ip",
    "loginid",
    "password"
})
public class SBIAPSSOLoginInSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "IP", required = true, nillable = true)
    protected String ip;
    @XmlElement(name = "LOGIN_ID", required = true, nillable = true)
    protected String loginid;
    @XmlElement(name = "PASSWORD", required = true, nillable = true)
    protected String password;

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
     * ��ȡip���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIP() {
        return ip;
    }

    /**
     * ����ip���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIP(String value) {
        this.ip = value;
    }

    /**
     * ��ȡloginid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOGINID() {
        return loginid;
    }

    /**
     * ����loginid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOGINID(String value) {
        this.loginid = value;
    }

    /**
     * ��ȡpassword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPASSWORD() {
        return password;
    }

    /**
     * ����password���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPASSWORD(String value) {
        this.password = value;
    }

}
