
package com.cmcc.csb.sb_iap_pageinquiryuserinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_pageinquiryuserinfosrv package. 
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

    private final static QName _SBIAPPageInquiryUserInfoSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv", "SB_IAP_PageInquiryUserInfoSrvResponse");
    private final static QName _SBIAPPageInquiryUserInfoSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv", "SB_IAP_PageInquiryUserInfoSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_pageinquiryuserinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBIAPPageInquiryUserInfoSrvRequest }
     * 
     */
    public SBIAPPageInquiryUserInfoSrvRequest createSBIAPPageInquiryUserInfoSrvRequest() {
        return new SBIAPPageInquiryUserInfoSrvRequest();
    }

    /**
     * Create an instance of {@link SBIAPPageInquiryUserInfoSrvResponse }
     * 
     */
    public SBIAPPageInquiryUserInfoSrvResponse createSBIAPPageInquiryUserInfoSrvResponse() {
        return new SBIAPPageInquiryUserInfoSrvResponse();
    }

    /**
     * Create an instance of {@link SBIAPPageInquiryUserInfoSrvOutputItem }
     * 
     */
    public SBIAPPageInquiryUserInfoSrvOutputItem createSBIAPPageInquiryUserInfoSrvOutputItem() {
        return new SBIAPPageInquiryUserInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link SBIAPPageInquiryUserInfoSrvOutputCollection }
     * 
     */
    public SBIAPPageInquiryUserInfoSrvOutputCollection createSBIAPPageInquiryUserInfoSrvOutputCollection() {
        return new SBIAPPageInquiryUserInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPPageInquiryUserInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv", name = "SB_IAP_PageInquiryUserInfoSrvResponse")
    public JAXBElement<SBIAPPageInquiryUserInfoSrvResponse> createSBIAPPageInquiryUserInfoSrvResponse(SBIAPPageInquiryUserInfoSrvResponse value) {
        return new JAXBElement<SBIAPPageInquiryUserInfoSrvResponse>(_SBIAPPageInquiryUserInfoSrvResponse_QNAME, SBIAPPageInquiryUserInfoSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPPageInquiryUserInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv", name = "SB_IAP_PageInquiryUserInfoSrvRequest")
    public JAXBElement<SBIAPPageInquiryUserInfoSrvRequest> createSBIAPPageInquiryUserInfoSrvRequest(SBIAPPageInquiryUserInfoSrvRequest value) {
        return new JAXBElement<SBIAPPageInquiryUserInfoSrvRequest>(_SBIAPPageInquiryUserInfoSrvRequest_QNAME, SBIAPPageInquiryUserInfoSrvRequest.class, null, value);
    }

}
