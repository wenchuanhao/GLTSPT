package com.cmcc.webservice.sendsmsinfosrv;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.ReceiverItem;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrv;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvInputItem;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvRequest;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvResponse;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;
public class SendSmsinfoClient extends JaxbParseBase{

//	private SBIAPSendSMSInfoSrv_Service sBIAPSendSMSInfoSrv_Service;
//	
//	public SBIAPSendSMSInfoSrv_Service getsBIAPSendSMSInfoSrv_Service() {
//		return sBIAPSendSMSInfoSrv_Service;
//	}
//
//	public void setsBIAPSendSMSInfoSrv_Service(
//			SBIAPSendSMSInfoSrv_Service sBIAPSendSMSInfoSrv_Service) {
//		this.sBIAPSendSMSInfoSrv_Service = sBIAPSendSMSInfoSrv_Service;
//	}

	private SBIAPSendSMSInfoSrv sBIAPSendSMSInfoSrv;
	

	private Log log = LogFactory.getLog(getClass());

	public SendSmsinfoClient() {
		super();
	}
	
	public void init(){
		if(sBIAPSendSMSInfoSrv == null){
			SBIAPSendSMSInfoSrv_Service sBIAPSendSMSInfoSrv_Service = new SBIAPSendSMSInfoSrv_Service();
			sBIAPSendSMSInfoSrv = sBIAPSendSMSInfoSrv_Service.getSBIAPSendSMSInfoSrvPort();
		}
    }

	@SuppressWarnings("unchecked")
    public SBIAPSendSMSInfoSrvResponse sendSMSInfo(SBIAPSendSMSInfoSrvRequest sBIAPSendSMSInfoSrvRequest){
		try {
			init();
			if(null==sBIAPSendSMSInfoSrvRequest){
				return null;
			}{
				MsgHeader msgHeader=sBIAPSendSMSInfoSrvRequest.getMsgHeader();
				msgHeader=initMsgHeader(msgHeader);
				sBIAPSendSMSInfoSrvRequest.setMsgHeader(msgHeader);
				List<SBIAPSendSMSInfoSrvInputItem> list = sBIAPSendSMSInfoSrvRequest.getSBIAPSendSMSInfoSrvInputCollection().getSBIAPSendSMSInfoSrvInputItem();
				for (int i = 0; i < list.size(); i++) {
					List<ReceiverItem>  list_ri = list.get(i).getRECEIVER().getReceiverItem();
					for (int j = 0; j < list_ri.size(); j++) {
						log.info("请求参数:{CONTENT:"+list.get(i).getCONTENT()+",MOBILE:"+list_ri.get(j).getMOBILE()
								+",msgHeader:"+sBIAPSendSMSInfoSrvRequest.getMsgHeader()+"}");
					}
				}
	        	return sBIAPSendSMSInfoSrv.process(sBIAPSendSMSInfoSrvRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
