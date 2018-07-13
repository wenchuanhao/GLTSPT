package com.cmcc.csb.sb_iap_sendmailinfosrv;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-10-14T13:33:33.167+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "SB_IAP_SendMailInfoSrv", 
                  wsdlLocation = "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_SendMailInfoSrv/SB_IAP_SendMailInfoSrv?WSDL",
                  targetNamespace = "http://csb.cmcc.com/SB_IAP_SendMailInfoSrv") 
public class SBIAPSendMailInfoSrv_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", "SB_IAP_SendMailInfoSrv");
    public final static QName SBIAPSendMailInfoSrvPort = new QName("http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", "SB_IAP_SendMailInfoSrvPort");
    static {
        URL url = null;
        try {
            url = new URL("http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_SendMailInfoSrv/SB_IAP_SendMailInfoSrv?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SBIAPSendMailInfoSrv_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_SendMailInfoSrv/SB_IAP_SendMailInfoSrv?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public SBIAPSendMailInfoSrv_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SBIAPSendMailInfoSrv_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SBIAPSendMailInfoSrv_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
   /* public SBIAPSendMailInfoSrv_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPSendMailInfoSrv_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPSendMailInfoSrv_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }*/

    /**
     *
     * @return
     *     returns SBIAPSendMailInfoSrv
     */
    @WebEndpoint(name = "SB_IAP_SendMailInfoSrvPort")
    public SBIAPSendMailInfoSrv getSBIAPSendMailInfoSrvPort() {
        return super.getPort(SBIAPSendMailInfoSrvPort, SBIAPSendMailInfoSrv.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SBIAPSendMailInfoSrv
     */
    @WebEndpoint(name = "SB_IAP_SendMailInfoSrvPort")
    public SBIAPSendMailInfoSrv getSBIAPSendMailInfoSrvPort(WebServiceFeature... features) {
        return super.getPort(SBIAPSendMailInfoSrvPort, SBIAPSendMailInfoSrv.class, features);
    }

}