
package com.cmcc.csb.sb_iap_managertasktodosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SB_IAP_ManagerTaskToDoSrvInputCollection complex type
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SB_IAP_ManagerTaskToDoSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SB_IAP_ManagerTaskToDoSrvInputItem" type="{http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv}SB_IAP_ManagerTaskToDoSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_IAP_ManagerTaskToDoSrvInputCollection", propOrder = {
    "sbiapManagerTaskToDoSrvInputItem"
})
public class SBIAPManagerTaskToDoSrvInputCollection {

    @XmlElement(name = "SB_IAP_ManagerTaskToDoSrvInputItem")
    protected List<SBIAPManagerTaskToDoSrvInputItem> sbiapManagerTaskToDoSrvInputItem;

    /**
     * Gets the value of the sbiapManagerTaskToDoSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sbiapManagerTaskToDoSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSBIAPManagerTaskToDoSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SBIAPManagerTaskToDoSrvInputItem }
     * 
     * 
     */
    public List<SBIAPManagerTaskToDoSrvInputItem> getSBIAPManagerTaskToDoSrvInputItem() {
        if (sbiapManagerTaskToDoSrvInputItem == null) {
            sbiapManagerTaskToDoSrvInputItem = new ArrayList<SBIAPManagerTaskToDoSrvInputItem>();
        }
        return this.sbiapManagerTaskToDoSrvInputItem;
    }

}
