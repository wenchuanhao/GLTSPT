
package com.cmcc.csb.sb_iap_ssologininsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_SSOLoginInSrvOutputCollection complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_SSOLoginInSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SB_IAP_SSOLoginInSrvOutputItem" type="{http://csb.cmcc.com/SB_IAP_SSOLoginInSrv}SB_IAP_SSOLoginInSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_SSOLoginInSrvOutputCollection", propOrder = {
    "sbiapssoLoginInSrvOutputItem"
})
public class SBIAPSSOLoginInSrvOutputCollection {

    @XmlElement(name = "SB_IAP_SSOLoginInSrvOutputItem")
    protected List<SBIAPSSOLoginInSrvOutputItem> sbiapssoLoginInSrvOutputItem;

    /**
     * Gets the value of the sbiapssoLoginInSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sbiapssoLoginInSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSBIAPSSOLoginInSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SBIAPSSOLoginInSrvOutputItem }
     * 
     * 
     */
    public List<SBIAPSSOLoginInSrvOutputItem> getSBIAPSSOLoginInSrvOutputItem() {
        if (sbiapssoLoginInSrvOutputItem == null) {
            sbiapssoLoginInSrvOutputItem = new ArrayList<SBIAPSSOLoginInSrvOutputItem>();
        }
        return this.sbiapssoLoginInSrvOutputItem;
    }

}
