
package com.cmcc.csb.sb_iap_sendmailinfosrv;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-10-12T14:26:08.163+08:00
 * Generated source version: 2.7.18
 * 
 */
public final class SBIAPSendMailInfoSrv_SBIAPSendMailInfoSrvPort_Client {

    private static final QName SERVICE_NAME = new QName("http://csb.cmcc.com/SB_IAP_SendMailInfoSrv", "SB_IAP_SendMailInfoSrv");

    private SBIAPSendMailInfoSrv_SBIAPSendMailInfoSrvPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SBIAPSendMailInfoSrv_Service.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        SBIAPSendMailInfoSrv_Service ss = new SBIAPSendMailInfoSrv_Service(wsdlURL, SERVICE_NAME);
        SBIAPSendMailInfoSrv port = ss.getSBIAPSendMailInfoSrvPort();  
        
        {
        System.out.println("Invoking process...");
        com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvRequest _process_payload = null;
        com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvResponse _process__return = port.process(_process_payload);
        System.out.println("process.result=" + _process__return);


        }

        System.exit(0);
    }

}
