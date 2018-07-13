package com.cdc.dc.rules.task;

import java.util.List;

import model.sys.entity.SysOrganizationTemp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrvOutputCollection;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrvOutputItem;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrvRequest;
import com.cmcc.csb.sb_iap_inquiryouinfosrv.SBIAPInquiryOUInfoSrvResponse;
import com.cmcc.webservice.inquiryouinfosrv.OuInfoClient;


/*同步移动组织*/
public class SyncOrgTask extends CommonController{

	private Log logger = LogFactory.getLog(RulesDelfileTask.class);
	
	private ISysOrganizationService organizationService;
	
	private OuInfoClient ouInfoClient;
	

	public ISysOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(ISysOrganizationService organizationService) {
		this.organizationService = organizationService;
	}


	public OuInfoClient getOuInfoClient() {
		return ouInfoClient;
	}

	public void setOuInfoClient(OuInfoClient ouInfoClient) {
		this.ouInfoClient = ouInfoClient;
	}

	public void syncOrgTask(){
		
		logger.info("***********同步移动组织定时任务开始**********");
		SBIAPInquiryOUInfoSrvRequest sBIAPInquiryOUInfoSrvRequest=new SBIAPInquiryOUInfoSrvRequest();
			sBIAPInquiryOUInfoSrvRequest.setFLAG("N");
			sBIAPInquiryOUInfoSrvRequest.setSTARTLASTUPDATEDATE(null);
			sBIAPInquiryOUInfoSrvRequest.setENDLASTUPDATEDATE(null);
			SBIAPInquiryOUInfoSrvResponse sBIAPInquiryOUInfoSrvResponse=ouInfoClient.ouInfo(sBIAPInquiryOUInfoSrvRequest);
			if(sBIAPInquiryOUInfoSrvResponse.getSERVICEFLAG().equals("Y")){
				organizationService.dropOrgTemp(); //删除组织，以便同步组织
				SBIAPInquiryOUInfoSrvOutputCollection sBIAPInquiryOUInfoSrvOutputCollection= sBIAPInquiryOUInfoSrvResponse.getSBIAPInquiryOUInfoSrvOutputCollection();
				List<SBIAPInquiryOUInfoSrvOutputItem>  sBIAPInquiryOUInfoSrvOutputItem=sBIAPInquiryOUInfoSrvOutputCollection.getSBIAPInquiryOUInfoSrvOutputItem();
				if(null!=sBIAPInquiryOUInfoSrvOutputItem&&sBIAPInquiryOUInfoSrvOutputItem.size()>0){
					SBIAPInquiryOUInfoSrvOutputItem temp=new SBIAPInquiryOUInfoSrvOutputItem();
					for(int i=0;i<sBIAPInquiryOUInfoSrvOutputItem.size();i++){
						SysOrganizationTemp sysOrganization=new SysOrganizationTemp();
						temp= sBIAPInquiryOUInfoSrvOutputItem.get(i);
						sysOrganization.setOuGuid(temp.getOUGUID());
						if(null!=temp.getOUID()){
							sysOrganization.setOuId(temp.getOUID().longValue());
						}
						sysOrganization.setOuFullname(temp.getOUFULLNAME());
						if(null!=temp.getOULEVEL()){
							sysOrganization.setOrgLevel(temp.getOULEVEL().longValue());
						}
						sysOrganization.setOrgName(temp.getOUNAME());
						if(null!=temp.getOUORDER()){
							sysOrganization.setOrgOrder(Float.parseFloat(String.valueOf(temp.getOUORDER())));
						}
						sysOrganization.setOrgState(temp.getORGSTATE());
						sysOrganization.setParentId(temp.getPARENTOUGUID());
						if(null!=temp.getLASTUPDATEDATE()){
							sysOrganization.setLastUpdateDate(temp.getLASTUPDATEDATE().toGregorianCalendar().getTime());
						}
						if(null!=temp.getCREATETIME()){
							sysOrganization.setCreateTime(temp.getCREATETIME().toGregorianCalendar().getTime());
						}
						sysOrganization.setOrganizationId(temp.getOUGUID());
						sysOrganization.setIsFromWeb("1");
						try {
							organizationService.addOrganizationTemp(sysOrganization);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					organizationService.updateOrgIdTemp();
				}
				
			}
	    logger.info("***********同步移动组织定时任务结束**********");
		logger.info("***********同步移动组织定时任务开始**********");
		organizationService.dropOrgBackup();//同步前备份数据
		organizationService.dropOrg();
		try{
			organizationService.addOrgByTemp();
		}catch(Exception e){
			e.printStackTrace();
		}
	    logger.info("***********同步移动组织定时任务结束**********");
	}

}
