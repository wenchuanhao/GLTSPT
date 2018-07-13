
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AttachmentItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AttachmentItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FILE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FILE_DATA" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachmentItem", propOrder = {
    "filename",
    "filedata"
})
public class AttachmentItem {

    @XmlElement(name = "FILE_NAME", required = true, nillable = true)
    protected String filename;
    @XmlElement(name = "FILE_DATA", required = true, nillable = true)
    protected byte[] filedata;

    /**
     * ��ȡfilename���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFILENAME() {
        return filename;
    }

    /**
     * ����filename���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFILENAME(String value) {
        this.filename = value;
    }

    /**
     * ��ȡfiledata���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFILEDATA() {
        return filedata;
    }

    /**
     * ����filedata���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFILEDATA(byte[] value) {
        this.filedata = value;
    }

}
