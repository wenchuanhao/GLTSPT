
package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_SendSMSInfoSrvInputCollection complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SendSMSInfoSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SB_IAP_SendSMSInfoSrvInputItem" type="{http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv}SB_IAP_SendSMSInfoSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SendSMSInfoSrvInputCollection", propOrder = {
    "sbiapSendSMSInfoSrvInputItem"
})
public class SBIAPSendSMSInfoSrvInputCollection {

    @XmlElement(name = "SB_IAP_SendSMSInfoSrvInputItem")
    protected List<SBIAPSendSMSInfoSrvInputItem> sbiapSendSMSInfoSrvInputItem;

    /**
     * Gets the value of the sbiapSendSMSInfoSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sbiapSendSMSInfoSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSBIAPSendSMSInfoSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SBIAPSendSMSInfoSrvInputItem }
     * 
     * 
     */
    public List<SBIAPSendSMSInfoSrvInputItem> getSBIAPSendSMSInfoSrvInputItem() {
        if (sbiapSendSMSInfoSrvInputItem == null) {
            sbiapSendSMSInfoSrvInputItem = new ArrayList<SBIAPSendSMSInfoSrvInputItem>();
        }
        return this.sbiapSendSMSInfoSrvInputItem;
    }

}
