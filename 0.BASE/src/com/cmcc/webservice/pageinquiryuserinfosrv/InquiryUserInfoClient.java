package com.cmcc.webservice.pageinquiryuserinfosrv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrv;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrvRequest;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrvResponse;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;

/**
 * 描述: 单点登录接口
 */
public class InquiryUserInfoClient extends JaxbParseBase{
	
	private SBIAPPageInquiryUserInfoSrv_Service sBIAPPageInquiryUserInfoSrv_Service;
	
	
	public SBIAPPageInquiryUserInfoSrv_Service getsBIAPPageInquiryUserInfoSrv_Service() {
		return sBIAPPageInquiryUserInfoSrv_Service;
	}

	public void setsBIAPPageInquiryUserInfoSrv_Service(
			SBIAPPageInquiryUserInfoSrv_Service sBIAPPageInquiryUserInfoSrv_Service) {
		this.sBIAPPageInquiryUserInfoSrv_Service = sBIAPPageInquiryUserInfoSrv_Service;
	}


	private SBIAPPageInquiryUserInfoSrv sBIAPPageInquiryUserInfoSrv;
	

	private Log log = LogFactory.getLog(getClass());

	public InquiryUserInfoClient() {
		super();
	}
	
	public void init(){
		sBIAPPageInquiryUserInfoSrv =sBIAPPageInquiryUserInfoSrv_Service.getSBIAPPageInquiryUserInfoSrvPort();
    }


	@SuppressWarnings("unchecked")
    public SBIAPPageInquiryUserInfoSrvResponse inquiryUserInfoClient(SBIAPPageInquiryUserInfoSrvRequest sBIAPPageInquiryUserInfoSrvRequest){
		try {
			if(null==sBIAPPageInquiryUserInfoSrvRequest){
				return null;
			}{
				MsgHeader msgHeader=sBIAPPageInquiryUserInfoSrvRequest.getMsgHeader();
				msgHeader=initMsgHeader(msgHeader);
				msgHeader.setTOTALRECORD(null);
				msgHeader.setPAGESIZE(null);
				msgHeader.setCURRENTPAGE(null);
				sBIAPPageInquiryUserInfoSrvRequest.setMsgHeader(msgHeader);
	        	return sBIAPPageInquiryUserInfoSrv.process(sBIAPPageInquiryUserInfoSrvRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
