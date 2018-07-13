
package com.cmcc.csb.sb_iap_checktokenkeyinsrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_checktokenkeyinsrv package. 
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

    private final static QName _SBIAPCheckTokenKeyInSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_CheckTokenKeyInSrv", "SB_IAP_CheckTokenKeyInSrvRequest");
    private final static QName _SBIAPCheckTokenKeyInSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_CheckTokenKeyInSrv", "SB_IAP_CheckTokenKeyInSrvResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_checktokenkeyinsrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBIAPCheckTokenKeyInSrvResponse }
     * 
     */
    public SBIAPCheckTokenKeyInSrvResponse createSBIAPCheckTokenKeyInSrvResponse() {
        return new SBIAPCheckTokenKeyInSrvResponse();
    }

    /**
     * Create an instance of {@link SBIAPCheckTokenKeyInSrvRequest }
     * 
     */
    public SBIAPCheckTokenKeyInSrvRequest createSBIAPCheckTokenKeyInSrvRequest() {
        return new SBIAPCheckTokenKeyInSrvRequest();
    }

    /**
     * Create an instance of {@link SBIAPCheckTokenKeyInSrvOutputItem }
     * 
     */
    public SBIAPCheckTokenKeyInSrvOutputItem createSBIAPCheckTokenKeyInSrvOutputItem() {
        return new SBIAPCheckTokenKeyInSrvOutputItem();
    }

    /**
     * Create an instance of {@link SBIAPCheckTokenKeyInSrvOutputCollection }
     * 
     */
    public SBIAPCheckTokenKeyInSrvOutputCollection createSBIAPCheckTokenKeyInSrvOutputCollection() {
        return new SBIAPCheckTokenKeyInSrvOutputCollection();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPCheckTokenKeyInSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_CheckTokenKeyInSrv", name = "SB_IAP_CheckTokenKeyInSrvRequest")
    public JAXBElement<SBIAPCheckTokenKeyInSrvRequest> createSBIAPCheckTokenKeyInSrvRequest(SBIAPCheckTokenKeyInSrvRequest value) {
        return new JAXBElement<SBIAPCheckTokenKeyInSrvRequest>(_SBIAPCheckTokenKeyInSrvRequest_QNAME, SBIAPCheckTokenKeyInSrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPCheckTokenKeyInSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_CheckTokenKeyInSrv", name = "SB_IAP_CheckTokenKeyInSrvResponse")
    public JAXBElement<SBIAPCheckTokenKeyInSrvResponse> createSBIAPCheckTokenKeyInSrvResponse(SBIAPCheckTokenKeyInSrvResponse value) {
        return new JAXBElement<SBIAPCheckTokenKeyInSrvResponse>(_SBIAPCheckTokenKeyInSrvResponse_QNAME, SBIAPCheckTokenKeyInSrvResponse.class, null, value);
    }

}
