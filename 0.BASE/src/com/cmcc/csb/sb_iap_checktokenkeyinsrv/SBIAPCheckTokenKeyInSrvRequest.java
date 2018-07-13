
package com.cmcc.csb.sb_iap_checktokenkeyinsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.cmcc.mss.msgheader.MsgHeader;


/**
 * <p>SB_IAP_CheckTokenKeyInSrvRequest complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_CheckTokenKeyInSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="IP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TOKEN_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TOKEN_KEY_EX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_CheckTokenKeyInSrvRequest", propOrder = {
    "msgHeader",
    "ip",
    "tokenkey",
    "tokenkeyex"
})
public class SBIAPCheckTokenKeyInSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "IP", required = true, nillable = true)
    protected String ip;
    @XmlElement(name = "TOKEN_KEY", required = true, nillable = true)
    protected String tokenkey;
    @XmlElement(name = "TOKEN_KEY_EX", required = true, nillable = true)
    protected String tokenkeyex;

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
     * ��ȡtokenkey���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOKENKEY() {
        return tokenkey;
    }

    /**
     * ����tokenkey���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOKENKEY(String value) {
        this.tokenkey = value;
    }

    /**
     * ��ȡtokenkeyex���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOKENKEYEX() {
        return tokenkeyex;
    }

    /**
     * ����tokenkeyex���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOKENKEYEX(String value) {
        this.tokenkeyex = value;
    }

}
