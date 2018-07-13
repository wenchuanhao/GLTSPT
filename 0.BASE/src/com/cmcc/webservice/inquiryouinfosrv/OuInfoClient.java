package com.cmcc.webservice.inquiryouinfosrv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrv;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrvRequest;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrvResponse;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;

/**
 * 描述: 目录同步接口
 */
public class OuInfoClient extends JaxbParseBase{
	
	private SBIAPInquiryOUInfoSrv_Service sBIAPInquiryOUInfoSrv_Service;
	
	
	public SBIAPInquiryOUInfoSrv_Service getsBIAPInquiryOUInfoSrv_Service() {
		return sBIAPInquiryOUInfoSrv_Service;
	}

	public void setsBIAPInquiryOUInfoSrv_Service(
			SBIAPInquiryOUInfoSrv_Service sBIAPInquiryOUInfoSrv_Service) {
		this.sBIAPInquiryOUInfoSrv_Service = sBIAPInquiryOUInfoSrv_Service;
	}


	private SBIAPInquiryOUInfoSrv sBIAPInquiryOUInfoSrv;
	

	private Log log = LogFactory.getLog(getClass());

	public OuInfoClient() {
		super();
	}
	
	public void init(){
		sBIAPInquiryOUInfoSrv =sBIAPInquiryOUInfoSrv_Service.getSBIAPInquiryOUInfoSrvPort();
    }


	@SuppressWarnings("unchecked")
    public SBIAPInquiryOUInfoSrvResponse ouInfo(SBIAPInquiryOUInfoSrvRequest sBIAPInquiryOUInfoSrvRequest){
		try {
			if(null==sBIAPInquiryOUInfoSrvRequest){
				return null;
			}{
				MsgHeader msgHeader=sBIAPInquiryOUInfoSrvRequest.getMsgHeader();
				msgHeader=initMsgHeader(msgHeader);
				msgHeader.setTOTALRECORD(null);
				msgHeader.setPAGESIZE(null);
				msgHeader.setCURRENTPAGE(null);
				sBIAPInquiryOUInfoSrvRequest.setMsgHeader(msgHeader);
				return sBIAPInquiryOUInfoSrv.process(sBIAPInquiryOUInfoSrvRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
