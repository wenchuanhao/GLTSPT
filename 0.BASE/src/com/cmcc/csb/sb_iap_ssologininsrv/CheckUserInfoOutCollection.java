
package com.cmcc.csb.sb_iap_ssologininsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CheckUserInfoOutCollection complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CheckUserInfoOutCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CheckUserInfoOutItem" type="{http://csb.cmcc.com/SB_IAP_SSOLoginInSrv}CheckUserInfoOutItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckUserInfoOutCollection", propOrder = {
    "checkUserInfoOutItem"
})
public class CheckUserInfoOutCollection {

    @XmlElement(name = "CheckUserInfoOutItem")
    protected List<CheckUserInfoOutItem> checkUserInfoOutItem;

    /**
     * Gets the value of the checkUserInfoOutItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the checkUserInfoOutItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCheckUserInfoOutItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CheckUserInfoOutItem }
     * 
     * 
     */
    public List<CheckUserInfoOutItem> getCheckUserInfoOutItem() {
        if (checkUserInfoOutItem == null) {
            checkUserInfoOutItem = new ArrayList<CheckUserInfoOutItem>();
        }
        return this.checkUserInfoOutItem;
    }

}
