package com.cmcc.csb.sb_iap_pageinquiryuserinfosrv;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-05-17T17:05:54.177+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "SB_IAP_PageInquiryUserInfoSrv", 
                  wsdlLocation = "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_PageInquiryUserInfoSrv/SB_IAP_PageInquiryUserInfoSrv?WSDL",
                  targetNamespace = "http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv") 
public class SBIAPPageInquiryUserInfoSrv_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv", "SB_IAP_PageInquiryUserInfoSrv");
    public final static QName SBIAPPageInquiryUserInfoSrvPort = new QName("http://csb.cmcc.com/SB_IAP_PageInquiryUserInfoSrv", "SB_IAP_PageInquiryUserInfoSrvPort");
    static {
        URL url = null;
        try {
            url = new URL("http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_PageInquiryUserInfoSrv/SB_IAP_PageInquiryUserInfoSrv?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SBIAPPageInquiryUserInfoSrv_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_PageInquiryUserInfoSrv/SB_IAP_PageInquiryUserInfoSrv?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public SBIAPPageInquiryUserInfoSrv_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SBIAPPageInquiryUserInfoSrv_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SBIAPPageInquiryUserInfoSrv_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
   /* public SBIAPPageInquiryUserInfoSrv_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPPageInquiryUserInfoSrv_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SBIAPPageInquiryUserInfoSrv_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }*/

    /**
     *
     * @return
     *     returns SBIAPPageInquiryUserInfoSrv
     */
    @WebEndpoint(name = "SB_IAP_PageInquiryUserInfoSrvPort")
    public SBIAPPageInquiryUserInfoSrv getSBIAPPageInquiryUserInfoSrvPort() {
        return super.getPort(SBIAPPageInquiryUserInfoSrvPort, SBIAPPageInquiryUserInfoSrv.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SBIAPPageInquiryUserInfoSrv
     */
   /* @WebEndpoint(name = "SB_IAP_PageInquiryUserInfoSrvPort")
    public SBIAPPageInquiryUserInfoSrv getSBIAPPageInquiryUserInfoSrvPort(WebServiceFeature... features) {
        return super.getPort(SBIAPPageInquiryUserInfoSrvPort, SBIAPPageInquiryUserInfoSrv.class, features);
    }*/

}