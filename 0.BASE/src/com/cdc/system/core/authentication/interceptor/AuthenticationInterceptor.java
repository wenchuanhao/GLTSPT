package com.cdc.system.core.authentication.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.trustel.system.SystemConstant;

import com.cdc.system.core.authentication.annotation.Permission;
import com.cdc.system.core.authentication.controller.Identifiable;
import com.cdc.system.core.authentication.service.DefaultAuthenticationInterceptorService;
import com.cdc.system.core.authentication.service.IAuthenticationInterceptorService;

/**
 * 
 * @Description: 权限验证的interceptor
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-18 下午03:29:11
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

	private Log logger = LogFactory.getLog(getClass());

	private IAuthenticationInterceptorService authenticationInerceptorService = DefaultAuthenticationInterceptorService.getInstance();

	/**
	 * 登录url
	 */
	private String loginUrl = "";
	/**
	 * 没有权限提示页面
	 */
	private String noprivilegeUrl = "";
	/**
	 * 超时或未登录的提示信息
	 */
	private final static String MESSAGE_OVERTIME = "系统超时或您尚未登陆本系统！";
	/**
	 * 没有操作权限的提示信息
	 */
	private final static String MESSAGE_NOPRIVILEGE = "您没有权限访问本系统功能，如确实需要访问，请与系统管理员联系！";
	
	private final static String[] preUrls = new String[]{"core/toConfirm","core/toChangePassword","core/modifyPwd","core/getDynamicCode","core/changeSuccess","core/tocheckOldPwd","core/toLogin","core/tocheckPhone","core/sslogin"};
	
	private final static String[] uploadFileUrls = new String[]{
		"adapterManage/createCesifileUploads",
		"adapterManage/createCesifileUploads1",
		"flowAttachment/createfileUploads",
		"dailyDevelop/createfileUploads",
		"preApproveDailyDevelop/dailyDetailFileUploads",
		"dailyDevelop/createfileUploads",
		"uploadController/doUpload",
		"uploadController/doPackageUpload",
		"uploadController/doRecordPackageUpload",
		"testProblemManage/mutualUploadFile",
		"AdapterCommonAttachmentUpload/doAttachmentUpload",
		"testProblemManage/testUploadFile",
		"launchRequireDevelop/developFileUploads",
		"manageRequireDevelop/developFileUploads",
		"launchRequirementProduct/launchfileUploads",
		"manageRequirementFlow/launchfileUploads",
		"newRequirement/createfileUploads",
		"requirementApprove/createfileUploads",
		"requirementManage/requirePoolFileUploads",
		"requirementManageCommon/requirePoolFileUploads",
		"versionPublish/versionUploads2",
		"noticeCommon/createfileUploads",
		"worksheetManageFlow/uploadFile",
		"worksheetManageFlow/uploadFile2"
		};

    /*private final static String[] preUrls = new String[]{"core/toConfirm","core/toChangePassword","core/modifyPwd","core/getDynamicCode","core/changeSuccess","core/tocheckOldPwd","core/toLogin","core/tocheckPhone"
    	   ,"getPwd/toGetPwd","getPwd/toGetUserPwdSuc","getPwd/checkAccountName","getPwd/checkUserName","getPwd/checkMobile","getPwd/checkMobile","getPwd/checkRandCode","getPwd/checkUserInfo"
    	   ,"getPwd/toCheckPhone","getPwd/getDynamicCode","getPwd/checkDynamicCode","getPwd/updateUserPwd","getPwd/randCodeImage"};*/

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HandlerMethod handlerMethod=null;
		if (handler instanceof HandlerMethod){
			handlerMethod = (HandlerMethod)handler;
		}		 
		// 输出请求的url
		logger.info("preHandle--> URL: " + request.getRequestURI());
		// 当前请求类的类名
		String privilegeCode = "";
		// 如果是 实现 Identifiable的类就需要进行验证
        String requestURI = request.getRequestURI();
        
        if(getVisitor(request) == null){
        	Boolean isHasSslogin=false;
        	/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
            ISysUserService sysUserService=(ISysUserService)wac.getBean("sysUserService");
            IDefaultLoginService  defaultLoginService=(IDefaultLoginService)wac.getBean("defaultLoginService");
            CheckTokenClient checkTokenClient=(CheckTokenClient)wac.getBean("checkTokenClient");
            SBIAPCheckTokenKeyInSrvRequest sBIAPCheckTokenKeyInSrvRequest=new SBIAPCheckTokenKeyInSrvRequest();
    		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
    		Boolean hasSsoLogin=false; //是否有单点登录标识
    		if(null!=cookies){
    			for(Cookie cookie : cookies){
    				if(cookie.getName().equals("NFJDIAPSSOToken")){
    					if(null!=cookie.getValue()){
    						sBIAPCheckTokenKeyInSrvRequest.setTOKENKEY(cookie.getValue());
    						hasSsoLogin=true;
    					}
    				}
    			}
    		}
    		if(hasSsoLogin){
    			String ip=IpUtil.getIpAddrByRequest(request);
    			sBIAPCheckTokenKeyInSrvRequest.setIP(ip);
    			SBIAPCheckTokenKeyInSrvResponse sBIAPCheckTokenKeyInSrvResponse=checkTokenClient.checkTokenClient(sBIAPCheckTokenKeyInSrvRequest);
    			if(sBIAPCheckTokenKeyInSrvResponse.getSERVICEFLAG().equals("Y")){
    				logger.info("令牌校验服务连接结果："+sBIAPCheckTokenKeyInSrvResponse.getSERVICEMESSAGE());
    				SBIAPCheckTokenKeyInSrvOutputCollection sBIAPCheckTokenKeyInSrvOutputCollection= sBIAPCheckTokenKeyInSrvResponse.getSBIAPCheckTokenKeyInSrvOutputCollection();
    				List<SBIAPCheckTokenKeyInSrvOutputItem> sbiapCheckTokenKeyInSrvOutputItem=sBIAPCheckTokenKeyInSrvOutputCollection.getSBIAPCheckTokenKeyInSrvOutputItem();
    				if(null!=sbiapCheckTokenKeyInSrvOutputItem&&sbiapCheckTokenKeyInSrvOutputItem.size()>0){
    					SBIAPCheckTokenKeyInSrvOutputItem temp=new SBIAPCheckTokenKeyInSrvOutputItem();
    					for(int i=0;i<sbiapCheckTokenKeyInSrvOutputItem.size();i++){
    						temp= sbiapCheckTokenKeyInSrvOutputItem.get(i);
    						if(temp.getRETURNCODE().equals(new  BigDecimal(0))){
    							com.cmcc.csb.sb_iap_checktokenkeyinsrv.CheckUserInfoOutCollection outparam=temp.getOUTPARAM();
    							List<com.cmcc.csb.sb_iap_checktokenkeyinsrv.CheckUserInfoOutItem> checkUserInfoOutItem=outparam.getCheckUserInfoOutItem();
    							if(null!=checkUserInfoOutItem&checkUserInfoOutItem.size()>0){
    								com.cmcc.csb.sb_iap_checktokenkeyinsrv.CheckUserInfoOutItem t1=checkUserInfoOutItem.get(i);
    								//SysUser sysUser=sysUserService.getSysUserByName(t1.getMUSERNAME());
    								SysUser sysUser=sysUserService.getSysUserByIdOrName(t1.getMLOGINID(), t1.getMUSERNAME());
    								if(null!=sysUser){
    									sysUser.setPrivileges(defaultLoginService.loadUserPrivilges(sysUser.getUserId()).get("privileges"));//加载权限
    				                    request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, sysUser);
    				                    ISysLogService sysLogService = (ISysLogService) wac.getBean("sysLogService");
    				                    sysLogService.log(request, sysUser, "用户登录",  "单点登录", "登录成功!", new Date(), "1", new Date(), null);
    				                    isHasSslogin=true;
    								}
    							}else{
    							}
    						}else{
    						}
    					}
    				}else{
    				}
    			}else{
    			}
    		}else{
    		}*/
        	if(!isHasSslogin){
        		 if (!uploadFilePath(requestURI) 
        				 && requestURI.indexOf("core/toLogin") == -1 
        				 && requestURI.indexOf("core/doLogin") == -1 
        				 && requestURI.indexOf("sms/sysSmsApproval") == -1 
        				 && requestURI.indexOf("command/h5") == -1 
        				 && requestURI.indexOf("purchase/h5") == -1 
        				 && requestURI.indexOf("html/njglts_index") == -1 
        				 && requestURI.indexOf("cordova_plugins") == -1 
        				 &&requestURI.indexOf("getPwd") == -1
        				 &&requestURI.indexOf("randCodeImage") == -1 
        				 && requestURI.indexOf("core/sslogin") == -1){
        			 
                     logger.warn(MESSAGE_OVERTIME);
                     request.setAttribute("message", MESSAGE_OVERTIME);
                     clearSession(request.getSession());
                     //request.getRequestDispatcher(loginUrl).forward(request, response);
                     String path = request.getContextPath();
                     String basePath = request.getScheme() + "://"
                             + request.getServerName() + ":" + request.getServerPort()
                             + path + "/";
                     //response.sendRedirect(basePath + loginUrl);
                     String script = "<script>window.top.location='" +basePath + loginUrl+ "'</script>";
                     response.getWriter().print(script);
                     return false;
                 }
        	}
           
        }

        boolean forceOpeator = false;
        if (request.getSession().getAttribute("user") != null) {
            forceOpeator = true;
            for (String preUrl : preUrls){
                if (requestURI.indexOf(preUrl) != -1){
                    forceOpeator = false;
                    break;
                }
            }
            if (forceOpeator){
                request.getRequestDispatcher("/WEB-INF/views/core/login/toChangePwdTip.jsp").forward(request, response);
                return false;
            }
        }

        if(handlerMethod!=null ){
			if ( handlerMethod.getBean() instanceof Identifiable) {
				privilegeCode = ((Identifiable) handlerMethod.getBean()).getPrivilegeCode();
				logger.info("preHandle-->handler implement Identifiable " + privilegeCode);
				return forward(request, response, privilegeCode,handler);
			} else {
				logger.info("preHandle-->handler not implement Identifiable " + handlerMethod.getBean().getClass().getSimpleName());
				return true;
			}
        }else{
        	if(requestURI.indexOf("html/njglts_index") != -1){
        		return true;
        	}
        	return false;
        }
	}

	/**
	 * 权限验证和页面转发
	 * 
	 * @param request
	 * @param response
	 * @param privilegeCode
	 *            当前请求的类名
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean forward(HttpServletRequest request, HttpServletResponse response, String privilegeCode,Object handler) throws ServletException, IOException {
		SysUser visitor = getVisitor(request);
		String url = "";
		if (visitor == null) {
			logger.warn(MESSAGE_OVERTIME);
			request.setAttribute("message", MESSAGE_OVERTIME);
			clearSession(request.getSession());
			url = loginUrl;
		} else {

			if (!authenticationInerceptorService.accessible(request, visitor, privilegeCode)) {
				try {
					/*
					 * 通过注解标识某个没有权限的Controller的method，该method的访问地址，在其他的页面中调用过，
					 * 但是因为这个Controller在登陆的账号角色中是没有权限的，这时候，而页面又需要调用该方法，所以，
					 * 用@permission注解，就可以了
					 */
					HandlerMethod handlerMethod = (HandlerMethod) handler;
					Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
					if (permission != null) {
						String value = permission.value();//
						String[] values = permission.value() != null ? value.split(",") : new String[] {};
						for (String val : values) {
							if(authenticationInerceptorService.accessible(request,visitor,val))
								return true;
						}
					}
					logger.error(privilegeCode);
					logger.error(MESSAGE_NOPRIVILEGE);
					request.setAttribute("message", MESSAGE_NOPRIVILEGE);
					url = noprivilegeUrl;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			} else {
				//response.setHeader("Set-Cookie", "name=value; HttpOnly");
				//response.setHeader("Set-Cookie", "JSESSIONID="+request.getSession().getId()+ "; HttpOnly");
				return true;
			}
			//return true;
		}
		logger.info("dispatcher:" + url);
		request.getRequestDispatcher(url).forward(request, response);
		return false;
	}

	/**
	 * 得到当前登录用户
	 * 
	 * @param request
	 * @return
	 */
	private SysUser getVisitor(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session == null)
			return null;
		return (SysUser) session.getAttribute(SystemConstant.SESSION_VISITOR);
	}
	
	
	private boolean uploadFilePath(String requestURI) {
		boolean result = false;
		for(String url : uploadFileUrls) {
			if(requestURI.indexOf(url) != -1) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 清除session
	 * 
	 * @param session
	 */
	private void clearSession(HttpSession session) {
		if (session == null)
			return;
		Enumeration<?> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			session.removeAttribute((String) names.nextElement());
		}
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setNoprivilegeUrl(String noprivilegeUrl) {
		this.noprivilegeUrl = noprivilegeUrl;
	}

}
