
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ToAddressCollection complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ToAddressCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ToAddressItem" type="{http://csb.cmcc.com/SB_IAP_SendMailInfoSrv}ToAddressItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ToAddressCollection", propOrder = {
    "toAddressItem"
})
public class ToAddressCollection {

    @XmlElement(name = "ToAddressItem")
    protected List<ToAddressItem> toAddressItem;

    /**
     * Gets the value of the toAddressItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toAddressItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToAddressItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ToAddressItem }
     * 
     * 
     */
    public List<ToAddressItem> getToAddressItem() {
        if (toAddressItem == null) {
            toAddressItem = new ArrayList<ToAddressItem>();
        }
        return this.toAddressItem;
    }

}
