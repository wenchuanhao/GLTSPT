
package com.cmcc.csb.sb_iap_inquiryouinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_inquiryouinfosrv package. 
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

    private final static QName _SBIAPInquiryOUInfoSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_InquiryOUInfoSrv", "SB_IAP_InquiryOUInfoSrvResponse");
    private final static QName _SBIAPInquiryOUInfoSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_InquiryOUInfoSrv", "SB_IAP_InquiryOUInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_inquiryouinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBIAPInquiryOUInfoSrvRequest }
     * 
     */
    public SBIAPInquiryOUInfoSrvRequest createSBIAPInquiryOUInfoSrvRequest() {
        return new SBIAPInquiryOUInfoSrvRequest();
    }

    /**
     * Create an instance of {@link SBIAPInquiryOUInfoSrvResponse }
     * 
     */
    public SBIAPInquiryOUInfoSrvResponse createSBIAPInquiryOUInfoSrvResponse() {
        return new SBIAPInquiryOUInfoSrvResponse();
    }

    /**
     * Create an instance of {@link SBIAPInquiryOUInfoSrvOutputItem }
     * 
     */
    public SBIAPInquiryOUInfoSrvOutputItem createSBIAPInquiryOUInfoSrvOutputItem() {
        return new SBIAPInquiryOUInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link SBIAPInquiryOUInfoSrvOutputCollection }
     * 
     */
    public SBIAPInquiryOUInfoSrvOutputCollection createSBIAPInquiryOUInfoSrvOutputCollection() {
        return new SBIAPInquiryOUInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPInquiryOUInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_InquiryOUInfoSrv", name = "SB_IAP_InquiryOUInfoSrvResponse")
    public JAXBElement<SBIAPInquiryOUInfoSrvResponse> createSBIAPInquiryOUInfoSrvResponse(SBIAPInquiryOUInfoSrvResponse value) {
        return new JAXBElement<SBIAPInquiryOUInfoSrvResponse>(_SBIAPInquiryOUInfoSrvResponse_QNAME, SBIAPInquiryOUInfoSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPInquiryOUInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_InquiryOUInfoSrv", name = "SB_IAP_InquiryOUInfoSrvRequest")
    public JAXBElement<SBIAPInquiryOUInfoSrvRequest> createSBIAPInquiryOUInfoSrvRequest(SBIAPInquiryOUInfoSrvRequest value) {
        return new JAXBElement<SBIAPInquiryOUInfoSrvRequest>(_SBIAPInquiryOUInfoSrvRequest_QNAME, SBIAPInquiryOUInfoSrvRequest.class, null, value);
    }

}
