package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-08-15T11:26:22.869+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "SB_IAP_SendSMSInfoSrv", 
                  wsdlLocation = "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_SendSMSInfoSrv/SB_IAP_SendSMSInfoSrv?WSDL",
                  targetNamespace = "http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv") 
public class SBIAPSendSMSInfoSrv_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", "SB_IAP_SendSMSInfoSrv");
    public final static QName SBIAPSendSMSInfoSrvPort = new QName("http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", "SB_IAP_SendSMSInfoSrvPort");
    static {
        URL url = null;
        try {
            url = new URL("http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_SendSMSInfoSrv/SB_IAP_SendSMSInfoSrv?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SBIAPSendSMSInfoSrv_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_SendSMSInfoSrv/SB_IAP_SendSMSInfoSrv?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public SBIAPSendSMSInfoSrv_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SBIAPSendSMSInfoSrv_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SBIAPSendSMSInfoSrv_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPSendSMSInfoSrv_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPSendSMSInfoSrv_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPSendSMSInfoSrv_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns SBIAPSendSMSInfoSrv
     */
    @WebEndpoint(name = "SB_IAP_SendSMSInfoSrvPort")
    public SBIAPSendSMSInfoSrv getSBIAPSendSMSInfoSrvPort() {
        return super.getPort(SBIAPSendSMSInfoSrvPort, SBIAPSendSMSInfoSrv.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SBIAPSendSMSInfoSrv
     */
    @WebEndpoint(name = "SB_IAP_SendSMSInfoSrvPort")
    public SBIAPSendSMSInfoSrv getSBIAPSendSMSInfoSrvPort(WebServiceFeature... features) {
        return super.getPort(SBIAPSendSMSInfoSrvPort, SBIAPSendSMSInfoSrv.class, features);
    }

}
