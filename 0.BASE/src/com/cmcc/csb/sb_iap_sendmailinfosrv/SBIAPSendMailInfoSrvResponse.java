
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_SendMailInfoSrvResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendMailInfoSrvResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SERVICE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SERVICE_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INSTANCE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TOTAL_RECORD" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TOTAL_PAGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PAGE_SIZE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CURRENT_PAGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SB_IAP_SendMailInfoSrvOutputCollection" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}SB_IAP_SendMailInfoSrvOutputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendMailInfoSrvResponse", propOrder = {
    "serviceflag",
    "servicemessage",
    "instanceid",
    "totalrecord",
    "totalpage",
    "pagesize",
    "currentpage",
    "sbiapSendMailInfoSrvOutputCollection"
})
public class SBIAPSendMailInfoSrvResponse {

    @XmlElement(name = "SERVICE_FLAG", required = true, nillable = true)
    protected String serviceflag;
    @XmlElement(name = "SERVICE_MESSAGE", required = true, nillable = true)
    protected String servicemessage;
    @XmlElement(name = "INSTANCE_ID", required = true, nillable = true)
    protected String instanceid;
    @XmlElement(name = "TOTAL_RECORD", required = true, nillable = true)
    protected BigDecimal totalrecord;
    @XmlElement(name = "TOTAL_PAGE", required = true, nillable = true)
    protected BigDecimal totalpage;
    @XmlElement(name = "PAGE_SIZE", required = true, nillable = true)
    protected BigDecimal pagesize;
    @XmlElement(name = "CURRENT_PAGE", required = true, nillable = true)
    protected BigDecimal currentpage;
    @XmlElement(name = "SB_IAP_SendMailInfoSrvOutputCollection", required = true)
    protected SBIAPSendMailInfoSrvOutputCollection sbiapSendMailInfoSrvOutputCollection;

    /**
     * 获取serviceflag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVICEFLAG() {
        return serviceflag;
    }

    /**
     * 设置serviceflag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVICEFLAG(String value) {
        this.serviceflag = value;
    }

    /**
     * 获取servicemessage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVICEMESSAGE() {
        return servicemessage;
    }

    /**
     * 设置servicemessage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVICEMESSAGE(String value) {
        this.servicemessage = value;
    }

    /**
     * 获取instanceid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTANCEID() {
        return instanceid;
    }

    /**
     * 设置instanceid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTANCEID(String value) {
        this.instanceid = value;
    }

    /**
     * 获取totalrecord属性的值。
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
     * 设置totalrecord属性的值。
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
     * 获取totalpage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALPAGE() {
        return totalpage;
    }

    /**
     * 设置totalpage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALPAGE(BigDecimal value) {
        this.totalpage = value;
    }

    /**
     * 获取pagesize属性的值。
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
     * 设置pagesize属性的值。
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
     * 获取currentpage属性的值。
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
     * 设置currentpage属性的值。
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
     * 获取sbiapSendMailInfoSrvOutputCollection属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SBIAPSendMailInfoSrvOutputCollection }
     *     
     */
    public SBIAPSendMailInfoSrvOutputCollection getSBIAPSendMailInfoSrvOutputCollection() {
        return sbiapSendMailInfoSrvOutputCollection;
    }

    /**
     * 设置sbiapSendMailInfoSrvOutputCollection属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SBIAPSendMailInfoSrvOutputCollection }
     *     
     */
    public void setSBIAPSendMailInfoSrvOutputCollection(SBIAPSendMailInfoSrvOutputCollection value) {
        this.sbiapSendMailInfoSrvOutputCollection = value;
    }

}
