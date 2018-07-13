
package com.cmcc.csb.sb_iap_sendmailinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_sendmailinfosrv package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SBIAPSendMailInfoSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", "SB_IAP_SendMailInfoSrvResponse");
    private final static QName _SBIAPSendMailInfoSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", "SB_IAP_SendMailInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_sendmailinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBIAPSendMailInfoSrvResponse }
     * 
     */
    public SBIAPSendMailInfoSrvResponse createSBIAPSendMailInfoSrvResponse() {
        return new SBIAPSendMailInfoSrvResponse();
    }

    /**
     * Create an instance of {@link SBIAPSendMailInfoSrvRequest }
     * 
     */
    public SBIAPSendMailInfoSrvRequest createSBIAPSendMailInfoSrvRequest() {
        return new SBIAPSendMailInfoSrvRequest();
    }

    /**
     * Create an instance of {@link AttachmentCollection }
     * 
     */
    public AttachmentCollection createAttachmentCollection() {
        return new AttachmentCollection();
    }

    /**
     * Create an instance of {@link ToAddressItem }
     * 
     */
    public ToAddressItem createToAddressItem() {
        return new ToAddressItem();
    }

    /**
     * Create an instance of {@link CcToAddressItem }
     * 
     */
    public CcToAddressItem createCcToAddressItem() {
        return new CcToAddressItem();
    }

    /**
     * Create an instance of {@link AttachmentItem }
     * 
     */
    public AttachmentItem createAttachmentItem() {
        return new AttachmentItem();
    }

    /**
     * Create an instance of {@link SBIAPSendMailInfoSrvOutputItem }
     * 
     */
    public SBIAPSendMailInfoSrvOutputItem createSBIAPSendMailInfoSrvOutputItem() {
        return new SBIAPSendMailInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link ToAddressCollection }
     * 
     */
    public ToAddressCollection createToAddressCollection() {
        return new ToAddressCollection();
    }

    /**
     * Create an instance of {@link CcToAddressCollection }
     * 
     */
    public CcToAddressCollection createCcToAddressCollection() {
        return new CcToAddressCollection();
    }

    /**
     * Create an instance of {@link SBIAPSendMailInfoSrvOutputCollection }
     * 
     */
    public SBIAPSendMailInfoSrvOutputCollection createSBIAPSendMailInfoSrvOutputCollection() {
        return new SBIAPSendMailInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link SBIAPSendMailInfoSrvInputItem }
     * 
     */
    public SBIAPSendMailInfoSrvInputItem createSBIAPSendMailInfoSrvInputItem() {
        return new SBIAPSendMailInfoSrvInputItem();
    }

    /**
     * Create an instance of {@link SBIAPSendMailInfoSrvInputCollection }
     * 
     */
    public SBIAPSendMailInfoSrvInputCollection createSBIAPSendMailInfoSrvInputCollection() {
        return new SBIAPSendMailInfoSrvInputCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPSendMailInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", name = "SB_IAP_SendMailInfoSrvResponse")
    public JAXBElement<SBIAPSendMailInfoSrvResponse> createSBIAPSendMailInfoSrvResponse(SBIAPSendMailInfoSrvResponse value) {
        return new JAXBElement<SBIAPSendMailInfoSrvResponse>(_SBIAPSendMailInfoSrvResponse_QNAME, SBIAPSendMailInfoSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPSendMailInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", name = "SB_IAP_SendMailInfoSrvRequest")
    public JAXBElement<SBIAPSendMailInfoSrvRequest> createSBIAPSendMailInfoSrvRequest(SBIAPSendMailInfoSrvRequest value) {
        return new JAXBElement<SBIAPSendMailInfoSrvRequest>(_SBIAPSendMailInfoSrvRequest_QNAME, SBIAPSendMailInfoSrvRequest.class, null, value);
    }

}
