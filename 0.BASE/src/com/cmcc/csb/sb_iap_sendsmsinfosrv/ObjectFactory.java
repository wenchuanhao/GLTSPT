
package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.cmcc.mss.msgheader.MsgHeader;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcc.csb.sb_iap_sendsmsinfosrv package. 
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

    private final static QName _SBIAPSendSMSInfoSrvRequest_QNAME = new QName("http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", "SB_IAP_SendSMSInfoSrvRequest");
    private final static QName _SBIAPSendSMSInfoSrvResponse_QNAME = new QName("http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", "SB_IAP_SendSMSInfoSrvResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcc.csb.sb_iap_sendsmsinfosrv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MsgHeader }
     * 
     */
    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    /**
     * Create an instance of {@link SBIAPSendSMSInfoSrvRequest }
     * 
     */
    public SBIAPSendSMSInfoSrvRequest createSBIAPSendSMSInfoSrvRequest() {
        return new SBIAPSendSMSInfoSrvRequest();
    }

    /**
     * Create an instance of {@link SBIAPSendSMSInfoSrvResponse }
     * 
     */
    public SBIAPSendSMSInfoSrvResponse createSBIAPSendSMSInfoSrvResponse() {
        return new SBIAPSendSMSInfoSrvResponse();
    }

    /**
     * Create an instance of {@link ReceiverCollection }
     * 
     */
    public ReceiverCollection createReceiverCollection() {
        return new ReceiverCollection();
    }

    /**
     * Create an instance of {@link SBIAPSendSMSInfoSrvOutputItem }
     * 
     */
    public SBIAPSendSMSInfoSrvOutputItem createSBIAPSendSMSInfoSrvOutputItem() {
        return new SBIAPSendSMSInfoSrvOutputItem();
    }

    /**
     * Create an instance of {@link SBIAPSendSMSInfoSrvInputCollection }
     * 
     */
    public SBIAPSendSMSInfoSrvInputCollection createSBIAPSendSMSInfoSrvInputCollection() {
        return new SBIAPSendSMSInfoSrvInputCollection();
    }

    /**
     * Create an instance of {@link ReceiverItem }
     * 
     */
    public ReceiverItem createReceiverItem() {
        return new ReceiverItem();
    }

    /**
     * Create an instance of {@link SBIAPSendSMSInfoSrvOutputCollection }
     * 
     */
    public SBIAPSendSMSInfoSrvOutputCollection createSBIAPSendSMSInfoSrvOutputCollection() {
        return new SBIAPSendSMSInfoSrvOutputCollection();
    }

    /**
     * Create an instance of {@link SBIAPSendSMSInfoSrvInputItem }
     * 
     */
    public SBIAPSendSMSInfoSrvInputItem createSBIAPSendSMSInfoSrvInputItem() {
        return new SBIAPSendSMSInfoSrvInputItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPSendSMSInfoSrvRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", name = "SB_IAP_SendSMSInfoSrvRequest")
    public JAXBElement<SBIAPSendSMSInfoSrvRequest> createSBIAPSendSMSInfoSrvRequest(SBIAPSendSMSInfoSrvRequest value) {
        return new JAXBElement<SBIAPSendSMSInfoSrvRequest>(_SBIAPSendSMSInfoSrvRequest_QNAME, SBIAPSendSMSInfoSrvRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SBIAPSendSMSInfoSrvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", name = "SB_IAP_SendSMSInfoSrvResponse")
    public JAXBElement<SBIAPSendSMSInfoSrvResponse> createSBIAPSendSMSInfoSrvResponse(SBIAPSendSMSInfoSrvResponse value) {
        return new JAXBElement<SBIAPSendSMSInfoSrvResponse>(_SBIAPSendSMSInfoSrvResponse_QNAME, SBIAPSendSMSInfoSrvResponse.class, null, value);
    }

}
