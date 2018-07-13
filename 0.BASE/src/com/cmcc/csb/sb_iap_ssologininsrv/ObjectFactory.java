
package com.cmcc.csb.sb_iap_ssologininsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_ssologininsrv package. 
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

    private final static QName _SBIAPSSOLoginInSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_SSOLoginInSrv", "SB_IAP_SSOLoginInSrvResponse");
    private final static QName _SBIAPSSOLoginInSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_SSOLoginInSrv", "SB_IAP_SSOLoginInSrvRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_ssologininsrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBIAPSSOLoginInSrvRequest }
     * 
     */
    public SBIAPSSOLoginInSrvRequest createSBIAPSSOLoginInSrvRequest() {
        return new SBIAPSSOLoginInSrvRequest();
    }

    /**
     * Create an instance of {@link SBIAPSSOLoginInSrvResponse }
     * 
     */
    public SBIAPSSOLoginInSrvResponse createSBIAPSSOLoginInSrvResponse() {
        return new SBIAPSSOLoginInSrvResponse();
    }

    /**
     * Create an instance of {@link SBIAPSSOLoginInSrvOutputCollection }
     * 
     */
    public SBIAPSSOLoginInSrvOutputCollection createSBIAPSSOLoginInSrvOutputCollection() {
        return new SBIAPSSOLoginInSrvOutputCollection();
    }

    /**
     * Create an instance of {@link SBIAPSSOLoginInSrvOutputItem }
     * 
     */
    public SBIAPSSOLoginInSrvOutputItem createSBIAPSSOLoginInSrvOutputItem() {
        return new SBIAPSSOLoginInSrvOutputItem();
    }

    /**
     * Create an instance of {@link CheckUserInfoOutItem }
     * 
     */
    public CheckUserInfoOutItem createCheckUserInfoOutItem() {
        return new CheckUserInfoOutItem();
    }

    /**
     * Create an instance of {@link CheckUserInfoOutCollection }
     * 
     */
    public CheckUserInfoOutCollection createCheckUserInfoOutCollection() {
        return new CheckUserInfoOutCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPSSOLoginInSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_SSOLoginInSrv", name = "SB_IAP_SSOLoginInSrvResponse")
    public JAXBElement<SBIAPSSOLoginInSrvResponse> createSBIAPSSOLoginInSrvResponse(SBIAPSSOLoginInSrvResponse value) {
        return new JAXBElement<SBIAPSSOLoginInSrvResponse>(_SBIAPSSOLoginInSrvResponse_QNAME, SBIAPSSOLoginInSrvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPSSOLoginInSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_SSOLoginInSrv", name = "SB_IAP_SSOLoginInSrvRequest")
    public JAXBElement<SBIAPSSOLoginInSrvRequest> createSBIAPSSOLoginInSrvRequest(SBIAPSSOLoginInSrvRequest value) {
        return new JAXBElement<SBIAPSSOLoginInSrvRequest>(_SBIAPSSOLoginInSrvRequest_QNAME, SBIAPSSOLoginInSrvRequest.class, null, value);
    }

}
