package com.cmcc.csb.sb_iap_sendsmsinfosrv;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2016-08-15T11:26:22.863+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebService(targetNamespace = "http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", name = "SB_IAP_SendSMSInfoSrv")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SBIAPSendSMSInfoSrv {

    @WebResult(name = "SB_IAP_SendSMSInfoSrvResponse", targetNamespace = "http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv", partName = "payload")
    @WebMethod(action = "process")
    public SBIAPSendSMSInfoSrvResponse process(
        @WebParam(partName = "payload", name = "SB_IAP_SendSMSInfoSrvRequest", targetNamespace = "http://csb.cmcc.com/SB_IAP_SendSMSInfoSrv")
        SBIAPSendSMSInfoSrvRequest payload
    );
}