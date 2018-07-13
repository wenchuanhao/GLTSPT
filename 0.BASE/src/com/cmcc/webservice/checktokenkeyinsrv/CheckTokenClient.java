package com.cmcc.webservice.checktokenkeyinsrv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrv;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrvRequest;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrvResponse;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;

/**
 * 描述: 令牌校验服务接口
 */
public class CheckTokenClient extends JaxbParseBase{
	
	private SBIAPCheckTokenKeyInSrv_Service sBIAPCheckTokenKeyInSrv_Service;
	
	
	public SBIAPCheckTokenKeyInSrv_Service getsBIAPCheckTokenKeyInSrv_Service() {
		return sBIAPCheckTokenKeyInSrv_Service;
	}

	public void setsBIAPCheckTokenKeyInSrv_Service(
			SBIAPCheckTokenKeyInSrv_Service sBIAPCheckTokenKeyInSrv_Service) {
		this.sBIAPCheckTokenKeyInSrv_Service = sBIAPCheckTokenKeyInSrv_Service;
	}

	private SBIAPCheckTokenKeyInSrv sBIAPCheckTokenKeyInSrv;
	

	private Log log = LogFactory.getLog(getClass());

	public CheckTokenClient() {
		super();
	}
	
	public void init(){
		sBIAPCheckTokenKeyInSrv =sBIAPCheckTokenKeyInSrv_Service.getSBIAPCheckTokenKeyInSrvPort();
    }

	@SuppressWarnings("unchecked")
    public SBIAPCheckTokenKeyInSrvResponse checkTokenClient(SBIAPCheckTokenKeyInSrvRequest sBIAPCheckTokenKeyInSrvRequest){
		try {
			if(null==sBIAPCheckTokenKeyInSrvRequest){
				return null;
			}else{
				MsgHeader msgHeader=sBIAPCheckTokenKeyInSrvRequest.getMsgHeader();
				msgHeader=initMsgHeader(msgHeader);
				sBIAPCheckTokenKeyInSrvRequest.setMsgHeader(msgHeader);
				return sBIAPCheckTokenKeyInSrv.process(sBIAPCheckTokenKeyInSrvRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
