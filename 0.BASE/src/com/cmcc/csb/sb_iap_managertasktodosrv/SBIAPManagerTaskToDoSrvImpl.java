
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.cmcc.csb.sb_iap_managertasktodosrv;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-06-03T14:25:41.330+08:00
 * Generated source version: 2.7.18
 * 
 */

@javax.jws.WebService(
                      serviceName = "SB_IAP_ManagerTaskToDoSrv",
                      portName = "SB_IAP_ManagerTaskToDoSrvPort",
                      targetNamespace = "http://csb.cmcc.com/SB_IAP_ManagerTaskToDoSrv",
                      wsdlLocation = "http://soa.nfjd.gmcc.net:8011/NFJD/ProxyService/IAP/SB_IAP_ManagerTaskToDoSrv/SB_IAP_ManagerTaskToDoSrv?WSDL",
                      endpointInterface = "com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrv")
                      
public class SBIAPManagerTaskToDoSrvImpl implements SBIAPManagerTaskToDoSrv {

    private static final Logger LOG = Logger.getLogger(SBIAPManagerTaskToDoSrvImpl.class.getName());

    /* (non-Javadoc)
     * @see com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrv#process(com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvRequest  payload )*
     */
    public com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvResponse process(SBIAPManagerTaskToDoSrvRequest payload) { 
        LOG.info("Executing operation process");
        System.out.println(payload);
        try {
            com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
