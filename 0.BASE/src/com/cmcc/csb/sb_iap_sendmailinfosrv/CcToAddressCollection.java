
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CcToAddressCollection complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CcToAddressCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CcToAddressItem" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}CcToAddressItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CcToAddressCollection", propOrder = {
    "ccToAddressItem"
})
public class CcToAddressCollection {

    @XmlElement(name = "CcToAddressItem")
    protected List<CcToAddressItem> ccToAddressItem;

    /**
     * Gets the value of the ccToAddressItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ccToAddressItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCcToAddressItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CcToAddressItem }
     * 
     * 
     */
    public List<CcToAddressItem> getCcToAddressItem() {
        if (ccToAddressItem == null) {
            ccToAddressItem = new ArrayList<CcToAddressItem>();
        }
        return this.ccToAddressItem;
    }

}
