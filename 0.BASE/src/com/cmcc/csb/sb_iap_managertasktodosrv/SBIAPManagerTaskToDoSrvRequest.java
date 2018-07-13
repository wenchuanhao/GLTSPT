
package com.cmcc.csb.sb_iap_managertasktodosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.cmcc.mss.msgheader.MsgHeader;


/**
 * <p>SB_IAP_ManagerTaskToDoSrvRequest complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_ManagerTaskToDoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="SB_IAP_ManagerTaskToDoSrvInputCollection" type="{http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv}SB_IAP_ManagerTaskToDoSrvInputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_ManagerTaskToDoSrvRequest", propOrder = {
    "msgHeader",
    "sbiapManagerTaskToDoSrvInputCollection"
})
public class SBIAPManagerTaskToDoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "SB_IAP_ManagerTaskToDoSrvInputCollection", required = true)
    protected SBIAPManagerTaskToDoSrvInputCollection sbiapManagerTaskToDoSrvInputCollection;

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
     * ��ȡsbiapManagerTaskToDoSrvInputCollection���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SBIAPManagerTaskToDoSrvInputCollection }
     *     
     */
    public SBIAPManagerTaskToDoSrvInputCollection getSBIAPManagerTaskToDoSrvInputCollection() {
        return sbiapManagerTaskToDoSrvInputCollection;
    }

    /**
     * ����sbiapManagerTaskToDoSrvInputCollection���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SBIAPManagerTaskToDoSrvInputCollection }
     *     
     */
    public void setSBIAPManagerTaskToDoSrvInputCollection(SBIAPManagerTaskToDoSrvInputCollection value) {
        this.sbiapManagerTaskToDoSrvInputCollection = value;
    }

}
