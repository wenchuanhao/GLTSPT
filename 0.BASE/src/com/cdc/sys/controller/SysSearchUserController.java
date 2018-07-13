package com.cdc.sys.controller;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.trustel.system.SystemConstant;

import com.cdc.sys.form.SysUserForm;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysPrivilegesService;
import com.cdc.sys.service.ISysRoleGroupService;
import com.cdc.sys.service.ISysRoleService;
import com.cdc.sys.service.ISysRoleUserService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cmcc.csb.sb_iap_managertasktodosrv.SBIAPManagerTaskToDoSrvInputItem;
import com.cmcc.webservice.inquiryouinfosrv.OuInfoClient;
import com.cmcc.webservice.managertasktodosrv.ManageTaskClient;
import com.trustel.common.ItemPage;

/**
 * 
 * @Description: 用户管理>>查询用户
 * @Author: cyh
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/sys/user/*")
public class SysSearchUserController extends CommonController {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysOrganizationService organizationService;

	@Autowired
	private ISysPrivilegesService privilegesService;
	
	@Autowired
	private ISysRoleGroupService groupService;
	/**
	 * 日志Service
	 */
	@Autowired
	private ISysLogService logService;
	
	@Autowired
	private ISysRoleUserService sysRoleUserService;
	
	/*@Autowired
	private SsologinClient ssologinClient;
	
	@Autowired
	private CheckTokenClient checkTokenClient;
	
	@Autowired
	private OuInfoClient ouInfoClient;
	
	@Autowired
	private InquiryUserInfoClient inquiryUserInfoClient;
	*/
/*	@Autowired
	private ManageTaskClient manageTaskClient;*/
	
	private ManageTaskClient manageTaskClient;
	
	private Log logger = LogFactory.getLog(SysSearchUserController.class);
	
	//@Autowired
	private OuInfoClient ouInfoClient;

	/**
	 * 查询用户
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "toSelectUser", method = { RequestMethod.GET,RequestMethod.POST })
	public String searchUserList(HttpServletRequest request,HttpServletResponse  response, SysUserForm form) {
		try {
			
			String ip=this.getIpAddr(request);
			

			/*logger.info("***********同步移动组织定时任务开始**********");
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
		    logger.info("***********同步移动组织定时任务结束**********");*/
			
			/*SBIAPSSOLoginInSrvRequest sBIAPSSOLoginInSrvRequest=new SBIAPSSOLoginInSrvRequest();
			sBIAPSSOLoginInSrvRequest.setIP(ip);
			sBIAPSSOLoginInSrvRequest.setLOGINID("dwyangxiongwei");
			sBIAPSSOLoginInSrvRequest.setPASSWORD("zst1234!@#$");
			SBIAPSSOLoginInSrvResponse sBIAPSSOLoginInSrvResponse=ssologinClient.Ssologin(sBIAPSSOLoginInSrvRequest);
			if(sBIAPSSOLoginInSrvResponse.getSERVICEFLAG().equals("y")){
				List<SBIAPSSOLoginInSrvOutputItem>  sSBIAPSSOLoginInSrvOutputItem= sBIAPSSOLoginInSrvResponse.getSBIAPSSOLoginInSrvOutputCollection().getSBIAPSSOLoginInSrvOutputItem();
				if(null!=sSBIAPSSOLoginInSrvOutputItem&&sSBIAPSSOLoginInSrvOutputItem.size()>0){
					SBIAPSSOLoginInSrvOutputItem temp=new SBIAPSSOLoginInSrvOutputItem();
					for(int i=0;i<sSBIAPSSOLoginInSrvOutputItem.size();i++){
						temp= sSBIAPSSOLoginInSrvOutputItem.get(i);
						CheckUserInfoOutCollection outparam=temp.getOUTPARAM();
						List<CheckUserInfoOutItem> checkUserInfoOutItem=outparam.getCheckUserInfoOutItem();
						CheckUserInfoOutItem usertemp=new CheckUserInfoOutItem();
						if(null!=checkUserInfoOutItem&&checkUserInfoOutItem.size()>0){
							usertemp=checkUserInfoOutItem.get(i);
						    String mloginid=usertemp.getMLOGINID();
						    String musername=usertemp.getMUSERNAME();
						    String mtokenkey=usertemp.getMTOKENKEY();
						    Cookie mloginidcookie = new Cookie("M_LOGIN_ID",mloginid);
						    Cookie musernamecookie = new Cookie("M_USER_NAME",musername);
						    Cookie mtokenkeycookie = new Cookie("M_TOKEN_KEY",mtokenkey);
						    response.addCookie(mloginidcookie);
						    response.addCookie(musernamecookie);
						    response.addCookie(mtokenkeycookie);
						}
					}
				}
			}*/
			
			/*SBIAPCheckTokenKeyInSrvRequest sBIAPCheckTokenKeyInSrvRequest=new SBIAPCheckTokenKeyInSrvRequest();
			Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("M_LOGIN_ID")){
					sBIAPCheckTokenKeyInSrvRequest.setTOKENKEY(cookie.getValue());
				}
			}
			sBIAPCheckTokenKeyInSrvRequest.setIP(ip);
			sBIAPCheckTokenKeyInSrvRequest.setTOKENKEY("test");
			SBIAPCheckTokenKeyInSrvResponse sBIAPCheckTokenKeyInSrvResponse=checkTokenClient.checkTokenClient(sBIAPCheckTokenKeyInSrvRequest);
			if(sBIAPCheckTokenKeyInSrvResponse.getSERVICEFLAG().equals("Y")){
				SBIAPCheckTokenKeyInSrvOutputCollection sBIAPCheckTokenKeyInSrvOutputCollection= sBIAPCheckTokenKeyInSrvResponse.getSBIAPCheckTokenKeyInSrvOutputCollection();
				List<SBIAPCheckTokenKeyInSrvOutputItem> sbiapCheckTokenKeyInSrvOutputItem=sBIAPCheckTokenKeyInSrvOutputCollection.getSBIAPCheckTokenKeyInSrvOutputItem();
				if(null!=sbiapCheckTokenKeyInSrvOutputItem&&sbiapCheckTokenKeyInSrvOutputItem.size()>0){
					SBIAPCheckTokenKeyInSrvOutputItem temp=new SBIAPCheckTokenKeyInSrvOutputItem();
					for(int i=0;i<sbiapCheckTokenKeyInSrvOutputItem.size();i++){
						temp= sbiapCheckTokenKeyInSrvOutputItem.get(i);
					}
				}
			}*/
			
			//organizationService.updateOrgId();
			
		   /* SBIAPInquiryOUInfoSrvRequest sBIAPInquiryOUInfoSrvRequest=new SBIAPInquiryOUInfoSrvRequest();
			sBIAPInquiryOUInfoSrvRequest.setFLAG("N");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    Date date1 = sdf.parse("1450-01-02");  
			 XMLGregorianCalendar start=DateUtils.dateToXmlDate(date1);
			sBIAPInquiryOUInfoSrvRequest.setSTARTLASTUPDATEDATE(null);
			sBIAPInquiryOUInfoSrvRequest.setENDLASTUPDATEDATE(null);
			SBIAPInquiryOUInfoSrvResponse sBIAPInquiryOUInfoSrvResponse=ouInfoClient.ouInfo(sBIAPInquiryOUInfoSrvRequest);
			if(sBIAPInquiryOUInfoSrvResponse.getSERVICEFLAG().equals("Y")){
				SBIAPInquiryOUInfoSrvOutputCollection sBIAPInquiryOUInfoSrvOutputCollection= sBIAPInquiryOUInfoSrvResponse.getSBIAPInquiryOUInfoSrvOutputCollection();
				List<SBIAPInquiryOUInfoSrvOutputItem>  sBIAPInquiryOUInfoSrvOutputItem=sBIAPInquiryOUInfoSrvOutputCollection.getSBIAPInquiryOUInfoSrvOutputItem();
				if(null!=sBIAPInquiryOUInfoSrvOutputItem&&sBIAPInquiryOUInfoSrvOutputItem.size()>0){
					SBIAPInquiryOUInfoSrvOutputItem temp=new SBIAPInquiryOUInfoSrvOutputItem();
					for(int i=0;i<sBIAPInquiryOUInfoSrvOutputItem.size();i++){
						SysOrganization sysOrganization=new SysOrganization();
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
						organizationService.addOrganization(sysOrganization);
					}
					organizationService.updateOrgId();
				}
				
			}*/
			
			/*SBIAPPageInquiryUserInfoSrvRequest sBIAPPageInquiryUserInfoSrvRequest=new SBIAPPageInquiryUserInfoSrvRequest();
			sBIAPPageInquiryUserInfoSrvRequest.setFLAG("Y");
			sBIAPPageInquiryUserInfoSrvRequest.setENDLASTUPDATEDATE(null);
			sBIAPPageInquiryUserInfoSrvRequest.setSTARTLASTUPDATEDATE(null);
			SBIAPPageInquiryUserInfoSrvResponse sBIAPPageInquiryUserInfoSrvResponse=inquiryUserInfoClient.inquiryUserInfoClient(sBIAPPageInquiryUserInfoSrvRequest);
			if(sBIAPPageInquiryUserInfoSrvResponse.getSERVICEFLAG().equals("Y")){
				SBIAPPageInquiryUserInfoSrvOutputCollection sBIAPPageInquiryUserInfoSrvOutputCollection= sBIAPPageInquiryUserInfoSrvResponse.getSBIAPPageInquiryUserInfoSrvOutputCollection();
				List<SBIAPPageInquiryUserInfoSrvOutputItem> sbiapPageInquiryUserInfoSrvOutputItem=sBIAPPageInquiryUserInfoSrvOutputCollection.getSBIAPPageInquiryUserInfoSrvOutputItem();
				if(null!=sbiapPageInquiryUserInfoSrvOutputItem&&sbiapPageInquiryUserInfoSrvOutputItem.size()>0){
					SBIAPPageInquiryUserInfoSrvOutputItem temp=new SBIAPPageInquiryUserInfoSrvOutputItem();
					for(int i=0;i<sbiapPageInquiryUserInfoSrvOutputItem.size();i++){
						SysUser sysUser=new SysUser();
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
						//sysUserService.addSysUser(sysUser);
					}
				}
			}*/
		  
			List list=sysUserService.getUserByMenu("adapterManage");
			List<SBIAPManagerTaskToDoSrvInputItem> sbiapManagerTaskToDoSrvInputItem=null;
			SBIAPManagerTaskToDoSrvInputItem sBIAPManagerTaskToDoSrvInputItem=new SBIAPManagerTaskToDoSrvInputItem();
			if(null!=list){
				for(int i=0;i<list.size();i++){
					
				}
			}
			
			
			/*SBIAPManagerTaskToDoSrvRequest sBIAPManagerTaskToDoSrvRequest=new SBIAPManagerTaskToDoSrvRequest();
			SBIAPManagerTaskToDoSrvResponse 	sBIAPManagerTaskToDoSrvResponse=manageTaskClient.manageTask(sBIAPManagerTaskToDoSrvRequest);
			if(sBIAPManagerTaskToDoSrvResponse.getSERVICEFLAG().equals("y")){
				SBIAPManagerTaskToDoSrvOutputCollection sBIAPManagerTaskToDoSrvOutputCollection= sBIAPManagerTaskToDoSrvResponse.getSBIAPManagerTaskToDoSrvOutputCollection();
				List<SBIAPManagerTaskToDoSrvOutputItem> sbiapManagerTaskToDoSrvOutputItem=sBIAPManagerTaskToDoSrvOutputCollection.getSBIAPManagerTaskToDoSrvOutputItem();
				if(null!=sbiapManagerTaskToDoSrvOutputItem&&sbiapManagerTaskToDoSrvOutputItem.size()>0){
					SBIAPManagerTaskToDoSrvOutputItem temp=new SBIAPManagerTaskToDoSrvOutputItem();
					Boolean managerRusult=true;
					StringBuffer errorResult=null;
					errorResult.append("任务推送失败：");
					for(int i=0;i<sbiapManagerTaskToDoSrvOutputItem.size();i++){
						temp=sbiapManagerTaskToDoSrvOutputItem.get(i);
						if(!temp.isRETURNS()){
							managerRusult=false;
							errorResult.append("{任务Id:"+temp.getTASKID()+";推送失败原因："+temp.getMSGERROR()+"}");
						}
					}
				}
			}*/
			
			
			/*SBIAPImportUserGroupRSrvRequest sBIAPImportUserGroupRSrvRequest=new SBIAPImportUserGroupRSrvRequest();
			SBIAPImportUserGroupRSrvResponse 	SBIAPImportUserGroupRSrvResponse=importUserGroupClient.importUserGroupClient(sBIAPImportUserGroupRSrvRequest);
			if(SBIAPImportUserGroupRSrvResponse.getSERVICEFLAG().equals("y")){
				
			}*/
			
			ItemPage itemPage = sysUserService.querySysUser(form);
			if(itemPage!=null && itemPage.getItems()!=null){
				List<SysUser> listUser=(List<SysUser>)itemPage.getItems();
				if(listUser!=null && listUser.size()>=1){
					for(int k=0;k<listUser.size();k++){
						StringBuilder containStr = new StringBuilder("");
						organizationService.deptNames(containStr, listUser.get(k).getOrganizationId());
				        /* containStr.append(">>ROOT");*/
				        String newOrgName = containStr.toString();
				        String[] arrorg = newOrgName.split(">>");
				        String  orgaN="";
				        if(arrorg != null && arrorg.length>=0){
				        	int aindex=arrorg.length-1;
				        	if(arrorg.length>=4){
				        	  String[] arrorg2={arrorg[aindex-2],arrorg[aindex-1],arrorg[aindex]};
				        	  arrorg=null;
				        	  arrorg=arrorg2;
				        	}
			        		for(int i =arrorg.length-1;i>=0;i--){
				        		if(arrorg[i] !=null && !arrorg[i].equals("")){
				        			if(orgaN == null || orgaN.equals("")){
				        				orgaN = arrorg[i];
				        			}else{
				        				orgaN+=">>"+arrorg[i];
				        			}
				        		}
				        	}
			        		listUser.get(k).setdNames(orgaN);
				        }
					}
					itemPage.setItems(listUser);
				}
			}
			request.setAttribute("form", form);
			request.setAttribute(SystemConstant.ATTRIBUTE_ITEMPAGE, itemPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/user/searchUserList";
	}
	
	private String getIpAddr(HttpServletRequest request) {   
	     String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	       //根据网卡取本机配置的IP   
	       InetAddress inet=null;   
	    try {   
	     inet = InetAddress.getLocalHost();   
	    } catch (UnknownHostException e) {   
	     e.printStackTrace();   
	    }   
	    ipAddress= inet.getHostAddress();   
	      }   
	            
	     }   
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  }   
	
}
