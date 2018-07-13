package com.cmcc.webservice.sendmailinfosrv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrv;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvRequest;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvResponse;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;

public class SendMailInfoClient extends JaxbParseBase{
	
	private SBIAPSendMailInfoSrv sBIAPSendMailInfoSrv;
	
	private Log log = LogFactory.getLog(getClass());

	public SendMailInfoClient() {
		super();
	}
	
	public void init(){
		if(sBIAPSendMailInfoSrv == null){
			SBIAPSendMailInfoSrv_Service sBIAPSendMailInfoSrv_Service = new SBIAPSendMailInfoSrv_Service();
			sBIAPSendMailInfoSrv = sBIAPSendMailInfoSrv_Service.getSBIAPSendMailInfoSrvPort();
		}
    }
	
	@SuppressWarnings("unchecked")
    public SBIAPSendMailInfoSrvResponse sendMailInfo(SBIAPSendMailInfoSrvRequest sBIAPSendMailInfoSrvRequest){
		try 
		{
			init();
			if(null == sBIAPSendMailInfoSrvRequest){
				return null;
			}
			MsgHeader msgHeader=sBIAPSendMailInfoSrvRequest.getMsgHeader();
			msgHeader=initMsgHeader(msgHeader);
			sBIAPSendMailInfoSrvRequest.setMsgHeader(msgHeader);
			return sBIAPSendMailInfoSrv.process(sBIAPSendMailInfoSrvRequest);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}   	
    }

}
