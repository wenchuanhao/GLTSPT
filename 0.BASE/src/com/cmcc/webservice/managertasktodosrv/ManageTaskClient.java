package com.cmcc.webservice.managertasktodosrv;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.cmcc.JaxbParseBase;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrv;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvRequest;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvResponse;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrv_Service;
import com.cmcc.mss.msgheader.MsgHeader;

/**
 * 描述: 代办待阅推送接口
 */
public class ManageTaskClient extends JaxbParseBase{
	
	private SBIAPManagerTaskToDoSrv_Service sBIAPManagerTaskToDoSrv_Service;
	
	
	public SBIAPManagerTaskToDoSrv_Service getsBIAPManagerTaskToDoSrv_Service() {
		return sBIAPManagerTaskToDoSrv_Service;
	}

	public void setsBIAPManagerTaskToDoSrv_Service(
			SBIAPManagerTaskToDoSrv_Service sBIAPManagerTaskToDoSrv_Service) {
		this.sBIAPManagerTaskToDoSrv_Service = sBIAPManagerTaskToDoSrv_Service;
	}


	private SBIAPManagerTaskToDoSrv sBIAPManagerTaskToDoSrv;
	

	private Log log = LogFactory.getLog(getClass());

	public ManageTaskClient() {
		super();
	}
	
	public void init(){
		sBIAPManagerTaskToDoSrv =sBIAPManagerTaskToDoSrv_Service.getSBIAPManagerTaskToDoSrvPort();
    }


	@SuppressWarnings("unchecked")
    public SBIAPManagerTaskToDoSrvResponse manageTask(SBIAPManagerTaskToDoSrvRequest sBIAPManagerTaskToDoSrvRequest){
		try {
			if(null==sBIAPManagerTaskToDoSrvRequest){
				return null;
			}{
				MsgHeader msgHeader=sBIAPManagerTaskToDoSrvRequest.getMsgHeader();
				msgHeader=initMsgHeader(msgHeader);
				sBIAPManagerTaskToDoSrvRequest.setMsgHeader(msgHeader);
	        	return sBIAPManagerTaskToDoSrv.process(sBIAPManagerTaskToDoSrvRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
