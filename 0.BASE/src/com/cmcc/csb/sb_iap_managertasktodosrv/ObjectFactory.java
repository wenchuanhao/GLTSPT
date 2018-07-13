
package com.cmcc.csb.sb_iap_managertasktodosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_managertasktodosrv package. 
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

    private final static QName _SBIAPManagerTaskToDoSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv", "SB_IAP_ManagerTaskToDoSrvRequest");
    private final static QName _SBIAPManagerTaskToDoSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv", "SB_IAP_ManagerTaskToDoSrvResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_managertasktodosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SBIAPManagerTaskToDoSrvRequest }
     * 
     */
    public SBIAPManagerTaskToDoSrvRequest createSBIAPManagerTaskToDoSrvRequest() {
        return new SBIAPManagerTaskToDoSrvRequest();
    }

    /**
     * Create an instance of {@link SBIAPManagerTaskToDoSrvResponse }
     * 
     */
    public SBIAPManagerTaskToDoSrvResponse createSBIAPManagerTaskToDoSrvResponse() {
        return new SBIAPManagerTaskToDoSrvResponse();
    }

    /**
     * Create an instance of {@link SBIAPManagerTaskToDoSrvOutputCollection }
     * 
     */
    public SBIAPManagerTaskToDoSrvOutputCollection createSBIAPManagerTaskToDoSrvOutputCollection() {
        return new SBIAPManagerTaskToDoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link SBIAPManagerTaskToDoSrvOutputItem }
     * 
     */
    public SBIAPManagerTaskToDoSrvOutputItem createSBIAPManagerTaskToDoSrvOutputItem() {
        return new SBIAPManagerTaskToDoSrvOutputItem();
    }

    /**
     * Create an instance of {@link SBIAPManagerTaskToDoSrvInputItem }
     * 
     */
    public SBIAPManagerTaskToDoSrvInputItem createSBIAPManagerTaskToDoSrvInputItem() {
        return new SBIAPManagerTaskToDoSrvInputItem();
    }

    /**
     * Create an instance of {@link SBIAPManagerTaskToDoSrvInputCollection }
     * 
     */
    public SBIAPManagerTaskToDoSrvInputCollection createSBIAPManagerTaskToDoSrvInputCollection() {
        return new SBIAPManagerTaskToDoSrvInputCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPManagerTaskToDoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv", name = "SB_IAP_ManagerTaskToDoSrvRequest")
    public JAXBElement<SBIAPManagerTaskToDoSrvRequest> createSBIAPManagerTaskToDoSrvRequest(SBIAPManagerTaskToDoSrvRequest value) {
        return new JAXBElement<SBIAPManagerTaskToDoSrvRequest>(_SBIAPManagerTaskToDoSrvRequest_QNAME, SBIAPManagerTaskToDoSrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPManagerTaskToDoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv", name = "SB_IAP_ManagerTaskToDoSrvResponse")
    public JAXBElement<SBIAPManagerTaskToDoSrvResponse> createSBIAPManagerTaskToDoSrvResponse(SBIAPManagerTaskToDoSrvResponse value) {
        return new JAXBElement<SBIAPManagerTaskToDoSrvResponse>(_SBIAPManagerTaskToDoSrvResponse_QNAME, SBIAPManagerTaskToDoSrvResponse.class, null, value);
    }

}
