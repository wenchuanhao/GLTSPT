package com.cdc.dc.rules.task;


import java.util.Date;
import java.util.List;

import model.sys.entity.SysUserTemp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrvOutputCollection;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrvOutputItem;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrvRequest;
import com.cmcc.csb.sb_iap_pageinquiryuserinfosrv.SBIAPPageInquiryUserInfoSrvResponse;
import com.cmcc.webservice.pageinquiryuserinfosrv.InquiryUserInfoClient;


/*同步移动用户*/
public class SyncUserTask extends CommonController{

	private Log logger = LogFactory.getLog(RulesDelfileTask.class);
	
	private ISysUserService sysUserService;
	
	private InquiryUserInfoClient inquiryUserInfoClient;

	public ISysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public InquiryUserInfoClient getInquiryUserInfoClient() {
		return inquiryUserInfoClient;
	}

	public void setInquiryUserInfoClient(InquiryUserInfoClient inquiryUserInfoClient) {
		this.inquiryUserInfoClient = inquiryUserInfoClient;
	}

	public void syncUserTask() throws Exception{
		
		logger.info("***********定时同步移动用户到临时表任务开始**********");
		SBIAPPageInquiryUserInfoSrvRequest sBIAPPageInquiryUserInfoSrvRequest=new SBIAPPageInquiryUserInfoSrvRequest();
		sBIAPPageInquiryUserInfoSrvRequest.setFLAG("Y");
		sBIAPPageInquiryUserInfoSrvRequest.setENDLASTUPDATEDATE(null);
		sBIAPPageInquiryUserInfoSrvRequest.setSTARTLASTUPDATEDATE(null);
		SBIAPPageInquiryUserInfoSrvResponse sBIAPPageInquiryUserInfoSrvResponse=inquiryUserInfoClient.inquiryUserInfoClient(sBIAPPageInquiryUserInfoSrvRequest);
		if(sBIAPPageInquiryUserInfoSrvResponse.getSERVICEFLAG().equals("Y")){
			sysUserService.dropUserTemp();
			SBIAPPageInquiryUserInfoSrvOutputCollection sBIAPPageInquiryUserInfoSrvOutputCollection= sBIAPPageInquiryUserInfoSrvResponse.getSBIAPPageInquiryUserInfoSrvOutputCollection();
			List<SBIAPPageInquiryUserInfoSrvOutputItem> sbiapPageInquiryUserInfoSrvOutputItem=sBIAPPageInquiryUserInfoSrvOutputCollection.getSBIAPPageInquiryUserInfoSrvOutputItem();
			if(null!=sbiapPageInquiryUserInfoSrvOutputItem&&sbiapPageInquiryUserInfoSrvOutputItem.size()>0){
				SBIAPPageInquiryUserInfoSrvOutputItem temp=new SBIAPPageInquiryUserInfoSrvOutputItem();
				for(int i=0;i<sbiapPageInquiryUserInfoSrvOutputItem.size();i++){
					SysUserTemp sysUser=new SysUserTemp();
					temp= sbiapPageInquiryUserInfoSrvOutputItem.get(i);
					sysUser.setEmployee(temp.getEMPLOYEE());
					sysUser.setFullName(temp.getFULLNAME());
					sysUser.setUserName(temp.getFULLNAME());
					sysUser.setOuGuid(temp.getOUGUID());
					sysUser.setOrganizationId(temp.getOUGUID());
					if(null!=temp.getOUID()){
						sysUser.setOuId(temp.getOUID().longValue());
					}
					sysUser.setJobType(temp.getJOBTYPE());
					sysUser.setUserType(temp.getUSERTYPE());
					sysUser.setWorkPhone(temp.getWORKPHONE());
					sysUser.setTelePhone(temp.getTELEPHONE());
					sysUser.setMobile(temp.getTELEPHONE());
					sysUser.setEmail(temp.getEMAIL());//email 
					sysUser.setUserDefaultRole("13081210445436500001");
					if(null!=temp.getUSERBIRTHDAY()){
						sysUser.setUserBirthday(temp.getUSERBIRTHDAY().toGregorianCalendar().getTime());
					}
					sysUser.setSex(temp.getSEX());
					sysUser.setTitle(temp.getTITLE());
					sysUser.setOrderId(temp.getORDERID());
					if(null!=temp.getORDERID()){
						sysUser.setOrderNum(Integer.parseInt(temp.getORDERID()));
					}
					if(null!=temp.getUSERJOININDATE()){
						sysUser.setUserJoininDate(temp.getUSERJOININDATE().toGregorianCalendar().getTime());
					}
					sysUser.setUserNation(temp.getUSERNATION());
					sysUser.setUserReligion(temp.getUSERRELIGION());
					if(null!=temp.getUSERQUITDATE()){
						sysUser.setUserQuitDate(temp.getUSERQUITDATE().toGregorianCalendar().getTime());
					}
					if(null!=temp.getCHANGETIME()){
						sysUser.setChangeTime(temp.getCHANGETIME().toGregorianCalendar().getTime());
					}
					if(null!=temp.getLASTUPDATEDATE()){
						sysUser.setLastUpdateDate(temp.getLASTUPDATEDATE().toGregorianCalendar().getTime());
					}
					sysUser.setUserId(temp.getUSERID());
					sysUser.setAccount(temp.getUSERID());
					sysUser.setFreezeStatus("0");
					sysUser.setIsActivate("1");
					sysUser.setIsReceiveSms("1");
					sysUser.setIsFromWeb("1");
					sysUser.setCreateTime(new Date());
					try {
						sysUserService.addSysUserTemp(sysUser);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sysUserService.updateSysUserIdTemp();
			}
		}
	    logger.info("***********定时同步移动用户到临时表任务结束**********");
		
		
		logger.info("***********同步移动用户定时任务开始**********");
		sysUserService.dropUserBackup();//同步前先备份数据
		sysUserService.dropUser();
		try{
			sysUserService.addSysUserByTemp();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			logger.info("***********给用户赋予默认角色begin**********");
			sysUserService.addSysUserDefaultRoleId();
			logger.info("***********给用户赋予默认角色end**********");
		}catch(Exception e){
			e.printStackTrace();
		}
	    logger.info("***********同步移动用户定时任务结束**********");
	}

}
