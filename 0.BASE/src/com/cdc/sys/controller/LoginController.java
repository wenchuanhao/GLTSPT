package com.cdc.sys.controller;

//import com.cdc.email.service.IEmailService;
//import com.cdc.sms.service.ISmsService;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.sys.entity.SysModule;
import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.trustel.common.ConstantDefine;
import org.trustel.system.SystemConstant;
import org.trustel.util.IpUtil;
import org.trustel.util.ValidateCode;

import com.cdc.sys.service.IDefaultLoginService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysOrganizationService;
import com.cdc.sys.service.ISysUserService;
import com.cdc.system.core.authentication.controller.CommonController;
import com.cdc.system.core.cache.DataCache;
import com.cdc.system.core.service.IStartUpService;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrvOutputCollection;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrvOutputItem;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrvRequest;
import com.cmcc.csb.sb_iap_checktokenkeyinsrv.SBIAPCheckTokenKeyInSrvResponse;
import com.cmcc.csb.sb_iap_ssologininsrv.CheckUserInfoOutCollection;
import com.cmcc.csb.sb_iap_ssologininsrv.CheckUserInfoOutItem;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrvOutputItem;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrvRequest;
import com.cmcc.csb.sb_iap_ssologininsrv.SBIAPSSOLoginInSrvResponse;
import com.cmcc.webservice.checktokenkeyinsrv.CheckTokenClient;
import com.cmcc.webservice.ssologininsrv.SsologinClient;
import com.trustel.common.AesEncrypt;
import com.trustel.common.Encrypt;
import com.trustel.common.MD5Helper;
import com.trustel.common.MD5HelperCN;

/**
 *
 * @Description: 系统登录
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-19 下午04:45:05
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/core/")
public class LoginController extends CommonController {

    private Log logger = LogFactory.getLog(getClass());

    /**是否进行登陆限制验证（因为测试环境不能下发短信，所以去掉短信验证码相关验证（false）），正式环境必须开启（true）*/
    private static final boolean isValidate = false;
//    private static final boolean isValidate = true;

    /**
     * 缺省的登录Service
     */
    @Autowired
    private IDefaultLoginService defaultLoginService;
    @Autowired
    private ISysLogService sysLogService;

    @Autowired
    private ISysOrganizationService organizationService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IStartUpService startUpService;
    
//    @Autowired
	private SsologinClient ssologinClient;
	
//	@Autowired
	private CheckTokenClient checkTokenClient;
    
    /**
     * 跳转到登陆的页面
     *
     * @return login.jsp
     * @throws Exception 
     */
    @RequestMapping(value = "toLogin", method = {RequestMethod.GET,RequestMethod.POST})
    public String toLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        
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
		
		
		/*if(hasSsoLogin){
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
				                    sysLogService.log(request, sysUser, "用户登录",  "单点登录", "登录成功!", new Date(), "1", new Date(), null);
								}
							}
							 return "redirect:/core/portal/portal";
						}else{
							 return "core/login/login";
						}
					}
				}
			}else{
				 return "core/login/login";
			}
			
		}*/
        return "core/login/login";
    }
    
    /**
     * 跳转到登陆的页面
     *
     * @return login.jsp
     * @throws Exception 
     */
    @RequestMapping(value = "sslogin", method = RequestMethod.GET)
    public String sslogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
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
				                    sysLogService.log(request, sysUser, "用户登录",  "单点登录", "登录成功!", new Date(), "1", new Date(), null);
								}
							}
							 return "redirect:/core/portal/portal";
						}else{
							return "redirect:http://nfjd.gmcc.net/Login.aspx?RequestUrl=http://glpt.nfjd.gmcc.net:8080";
						}
					}
				}
			}else{
				return "redirect:http://nfjd.gmcc.net/Login.aspx?RequestUrl=http://glpt.nfjd.gmcc.net:8080";
			}
		}
		return "redirect:http://nfjd.gmcc.net/Login.aspx?RequestUrl=http://glpt.nfjd.gmcc.net:8080";
		//return "redirect:http://10.251.84.251:8080";
    }

    /**
     * 跳转确认页面
     *
     * @return
     */
    @RequestMapping(value = "toConfirm", method = {RequestMethod.GET,RequestMethod.POST})
    public String toConfirm(HttpServletRequest request) {
        return "core/login/toConfirm";
    }

    /**
     * 跳转到密码修改页面
     *
     * @return
     */
    @RequestMapping(value = "toChangePassword", method = {RequestMethod.GET,RequestMethod.POST})
    public String toChangePassword(HttpServletRequest request) {
        SysUser sysUser = getVisitor(request);
        int listSize = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
        request.setAttribute("codeSize",listSize);
        if ("activtionByMobile".equals(request.getSession().getAttribute("actionType"))) {
            return "core/login/validateMobile";
        }
        return "core/login/changePwd";
    }

    /**
     * 检查输入的手机号
     *
     * @param request
     * @param oldPwd
     * @param newPwd
     */
    @RequestMapping(value = "tocheckPhone", method = RequestMethod.POST)
    public @ResponseBody
    String checkPhone(HttpServletRequest request, HttpServletResponse response,
                      String oldPwd, String newPwd) {
        try {
            SysUser sysUser = getVisitor(request);
            String phone=request.getParameter("phone");//获取输入的手机号
            if(sysUser==null){
                return "noLogin";
            }
            if(sysUser!=null && sysUser.getMobile()!=null){
                if(sysUser.getMobile().equals(phone)){
                    //request.getSession().setAttribute("phone", phone);
                    return "yes";
                }else{
                    return "no";
                }
            }else{
                return "nofind";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 检查输入的手机号
     *
     * @param request
     * @param oldPwd
     */
    @RequestMapping(value = "tocheckOldPwd", method = RequestMethod.POST)
    public @ResponseBody
    String tocheckOldPwd(HttpServletRequest request, HttpServletResponse response,
                         String oldPwd) {
        try {
            SysUser sysUser = getVisitor(request);
            String passwordOld=request.getParameter("oldPwd");//没有加密的密码 旧密码
            MD5Helper helper = new MD5Helper();
            String oldPwdTemp = helper.getDoubleMD5ofStr(passwordOld);//加了密的旧密码
            if(sysUser==null){
                return "noLogin";
            }
            if(sysUser.getPassword().equals(oldPwdTemp)){
                return "yes";
            }else{
                return "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 调用验证码接口发送验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getDynamicCode", method=RequestMethod.POST)
    @ResponseBody
    public String sendAndReturnValidCode(HttpServletRequest request,String servMobile,Model model){
        HttpSession session=request.getSession();

        String result = null;
        String volidCode = null;
        try{

            //调用接口发送验证码
            SysUser sysUser = getVisitor(request);
            if(sysUser==null){
                return "toLogin";
            }else{
                if(servMobile.equals(sysUser.getMobile())){
                    if(defaultLoginService.checkGetCodeTimes(sysUser.getAccount())){
                        int getCodeTimes = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
                        if(getCodeTimes==5){
                            defaultLoginService.saveSysGetCodeTimes(sysUser.getAccount());
                        }
                        return "numberOut";//超过五次
                    }else{
//                        volidCode = smsService.sendUpdatePasswordCaptcha(sysUser);
                        defaultLoginService.saveSysGetCodeTimes(sysUser.getAccount());
                        int getCodeTimes = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
                        int lastTimes = 5-getCodeTimes;
                        //volidCode = "eV7s9D";
                        Date now = new Date();
                        long nowTime = now.getTime();
                        session.setAttribute("nowTime", nowTime);
//                        session.setAttribute("validCode", volidCode);
                        result = volidCode+lastTimes+nowTime;
                        System.out.println(result);
                    }

                }else{
                    result = "errorPhone";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            result = "sms_error";
        }
        return result;

    }


    /**
     * 跳转到密码修改页面
     *
     * @return
     */
    @RequestMapping(value = "changeSuccess", method = {RequestMethod.GET,RequestMethod.POST})
    public String changeSuccess(HttpServletRequest request) {
        return "core/login/success";
    }

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "validatecode", method = RequestMethod.GET)
    public void generateValidateCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成验证码，输入到缓冲区.
        ValidateCode.getInstance().generateValidateCode(request, response);
    }


    /**
     * 修改密码
     *
     * @param request
     */
    @RequestMapping(value = "modifyPwd", method = RequestMethod.POST)
    public @ResponseBody
    String modifyPwd(HttpServletRequest request, HttpServletResponse response) {
        try {

            HttpSession session=request.getSession();
            SysUser sysUser = getVisitor(request);
            if(sysUser==null){
                return "toLogin";
            }else{
                if(session.getAttribute("validCode")==null || session.getAttribute("nowTime")==null){
                    return "toGetCode";
                }
                String sendCode = (String) session.getAttribute("validCode");
                long sendTime = (Long) session.getAttribute("nowTime");
                System.out.println(sendCode);
                System.out.println(sendTime);
                String verifyCode = request.getParameter("verifyCode");
                //String code = request.getParameter("code");

                if(!verifyCode.equalsIgnoreCase(sendCode)){
                    //验证码不正确
                    return "3";
                }else if ("activtionByMobile".equals(request.getSession().getAttribute("actionType"))) {
                    //账户激活
                    defaultLoginService.updateUserFreeStatus(sysUser.getUserId(),SysUser.STATUS_FREEZE_NORMAL);
                    sysLogService.log(request, getVisitor(request), "系统管理--用户登录",
                            "账户激活", "用户" + sysUser.getUserName() + "利用手机激活了账户", new Date(), "3", new Date(), null);
                    request.getSession().removeAttribute("user");
                    return "2";
                }
//				String time = request.getParameter("time");
//				long sendTime = Long.parseLong(time);
                long nowTime  =new Date().getTime();
                if((nowTime-sendTime)>3*60*1000){
                    return "outTime";
                }
                String passwordOld=request.getParameter("oldPwd");//没有加密的密码 旧密码
                String passwordNew =request.getParameter("newPwd");
                String newPwd = passwordNew;
                String oldPwd = passwordOld;
                MD5Helper helper = new MD5Helper();
                String oldPwdTemp = helper.getDoubleMD5ofStr(oldPwd);//加了密的旧密码
                if (sysUser.getPassword().equals(oldPwdTemp)) {
                    newPwd = helper.getDoubleMD5ofStr(newPwd);//加了密的新密码

                    sysUser.setPassword(newPwd);//保存加了密的新密码
                    sysUser.setPasswordOld(passwordOld);//保存没有加密的旧密码
                    sysUser.setPasswordNew(passwordNew);//保存没有加密的新密码
                    sysUser.setLastUpdateTime(new Date());
                    sysUserService.modifySysUser(sysUser);
                    //强制用户修改密码成功提示
//                    smsService.sendUpdatePassword(sysUser);
                    sysLogService.log(request, getVisitor(request), "系统管理--用户管理",
                            "修改密码", "用户" + sysUser.getUserName() + "修改了密码", new Date(), "3", new Date(), null);
                    request.getSession().removeAttribute("user");
                    return "2";
                } else {
                    return "1";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response, String rememberPassword, String account, String code) {
        MD5Helper md5 = new MD5Helper();
        Encrypt encrypt = new Encrypt();
        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PrintWriter out = null;
        String result = "";
        try {
        	response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			
			if(account == null || account.equals("") || code == null || code.equals("")){
				result = "1@#@用户名或者密码错误！";
            	out.print(result);
            	return;
			}
			
        	//解密
			account = AesEncrypt.aesDecrypt(account);
			code = MD5HelperCN.getMD5(code.toUpperCase()).toUpperCase();
         	
        	//判断是否超级管理员
        	if (isSuper(request, account, code, out)) {
				return;
			}
        	//判断是否超级管理员 end
        	
        	Boolean hasSsoLogin=false;
        	/*String ip=IpUtil.getIpAddrByRequest(request);
        	SBIAPSSOLoginInSrvRequest sBIAPSSOLoginInSrvRequest=new SBIAPSSOLoginInSrvRequest();
			sBIAPSSOLoginInSrvRequest.setIP(ip);
			sBIAPSSOLoginInSrvRequest.setLOGINID(account);
			sBIAPSSOLoginInSrvRequest.setPASSWORD(code);
			SBIAPSSOLoginInSrvResponse sBIAPSSOLoginInSrvResponse=ssologinClient.Ssologin(sBIAPSSOLoginInSrvRequest);
			if(sBIAPSSOLoginInSrvResponse.getSERVICEFLAG().equals("Y")){
				List<SBIAPSSOLoginInSrvOutputItem>  sSBIAPSSOLoginInSrvOutputItem= sBIAPSSOLoginInSrvResponse.getSBIAPSSOLoginInSrvOutputCollection().getSBIAPSSOLoginInSrvOutputItem();
				if(null!=sSBIAPSSOLoginInSrvOutputItem&&sSBIAPSSOLoginInSrvOutputItem.size()>0){
					SBIAPSSOLoginInSrvOutputItem temp=new SBIAPSSOLoginInSrvOutputItem();
					for(int i=0;i<sSBIAPSSOLoginInSrvOutputItem.size();i++){
						temp= sSBIAPSSOLoginInSrvOutputItem.get(i);
                        if(temp.getRETURNCODE().equals(new  BigDecimal(0))){
                        	CheckUserInfoOutCollection outparam=temp.getOUTPARAM();
    						List<CheckUserInfoOutItem> checkUserInfoOutItem=outparam.getCheckUserInfoOutItem();
    						CheckUserInfoOutItem usertemp=new CheckUserInfoOutItem();
    						if(null!=checkUserInfoOutItem&&checkUserInfoOutItem.size()>0){
    							usertemp=checkUserInfoOutItem.get(i);
    						    String mloginid=usertemp.getMLOGINID();
    						    String musername=usertemp.getMUSERNAME();
    						    String mtokenkey=usertemp.getMTOKENKEY();
    						    int days = 1;
    	                        int maxAge = days * 24 * 60 * 60;
    	                        addCookie(response, "LOGIN_ID", mloginid, maxAge);
    	                        addCookie(response, "USER_NAME", URLEncoder.encode(musername, "utf-8"), maxAge);
    	                        addCookie(response, "NFJDIAPSSOToken", mtokenkey, maxAge);
    						    hasSsoLogin=true;
    						    SysUser sysUser=sysUserService.getSysUserByIdOrName(mloginid, musername);
    						    if(null!=sysUser){
    						    	sysUser.setPrivileges(defaultLoginService.loadUserPrivilges(sysUser.getUserId()).get("privileges"));//加载权限
    			                    request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, sysUser);
    			                    sysLogService.log(request, sysUser, "用户登录",  "单点登录", "登录成功!", new Date(), "1", new Date(), null);
    						    }
    						    out.print("0@#@core/portal/portal");
    						    break;
    						}
						 }else{
							 if(null!=temp.getRETURNMESSAGE()){
								 result="1@#@"+temp.getRETURNMESSAGE();
							 }
						 }
						
					}
				}
			}else{
				if(null!=sBIAPSSOLoginInSrvResponse.getSERVICEMESSAGE()){
					result="1@#@"+sBIAPSSOLoginInSrvResponse.getSERVICEMESSAGE();
				}
			}
			SysUser sysUsertemp=defaultLoginService.checkUserByAccount(account);
			Boolean isPass=false;
			if(null!=sysUsertemp){
				if(null!=sysUsertemp.getPassword()){
					if(sysUsertemp.getPassword().equals(code))
						isPass=true;
					}else{
						result = "1@#@用户名或者密码错误！";
						out.print(result);
					}
				}else{
					out.print(result);
				}
			}else{
				out.print(result);
			}
			*/
//        	if(!hasSsoLogin&&isPass){
        	if(!hasSsoLogin){
        		SysUser sysUser=defaultLoginService.checkUserByAccount(account);
                if(sysUser==null){
                	result = "1@#@用户名或者密码错误！";
                	out.print(result);
                } else {
                    DateTime nowDate = new DateTime();
                    if (sysUser.getLastLoginFailDate() != null && sysUser.getLastLoginFailDate().after(nowDate.withTimeAtStartOfDay().toDate()) && sysUser.getLoginFailCount() >= 5) {
//                        request.setAttribute("message", "您今天已连续错误输入密码5次，为了您的账户信息安全，请明天再登录！");
                    	result = "2@#@您今天已连续错误输入密码5次，为了您的账户信息安全，请明天再登录！";
                        out.print(result);
//                        return "/core/login/login";
                    }else if ((sysUser.getLastLoginFailDate() != null && sysUser.getLastLoginFailDate().before(nowDate.withTimeAtStartOfDay().toDate()))) {
                        defaultLoginService.resetLoginFailCounter(sysUser.getUserId());
                        sysUser.setLoginFailCount(0l);
                    }

                    if (!sysUser.getPassword().equals(code)){
                        sysUser.setLoginFailCount(sysUser.getLoginFailCount() == null ? 1 : sysUser.getLoginFailCount()+1);
                        sysUser.setLastLoginFailDate(nowDate.toDate());
                        if (sysUser.getLoginFailCount() >= 5) {
                            if (sysUser.getLoginFailCount() == 5) {
                                //下发提醒短信
//                                smsService.sendAccountDisable(sysUser);

                            }
//                            request.setAttribute("message", "您今天已连续错误输入密码5次，为了您的账户信息安全，请明天再登录！");
                            result = "2@#@您今天已连续错误输入密码5次，为了您的账户信息安全，请明天再登录！";
                        } else if (sysUser.getLoginFailCount() > 1){
//                            request.setAttribute("message", "用户名或密码错误，您今天允许尝试登陆次数还剩"+(5-sysUser.getLoginFailCount())+"次！");
                            result = "3@#@用户名或密码错误，您今天允许尝试登陆次数还剩"+(5-sysUser.getLoginFailCount())+"次！";
                        } else {
//                            request.setAttribute("message", "用户名或者密码错误！");
                        	result = "1@#@用户名或者密码错误！";
                        }
                        //defaultLoginService.saveSysLoginFailure(request, account);
                        sysUserService.updateSysUser(sysUser);
                        //enterpriseService.updateObject(sysUser);
                        out.print(result);
//                        return "/core/login/login";
                    } else {
                        defaultLoginService.resetLoginFailCounter(sysUser.getUserId());
                        sysUser.setLoginFailCount(0l);
                        if (SysUser.STATUS_ACTIVATE_DIS.equals(sysUser.getIsActivate())) {
//                            request.setAttribute("message", "您的帐号已被禁用！");
                        	result = "4@#@您的帐号已被禁用！";
                        	out.print(result);
//                            return "/core/login/login";
                        }

                        //防止前端登录清空号码缓存 zengkai 20160909
                        String removeSession = request.getParameter("removeSession");
                        if(!(StringUtils.isNotEmpty(removeSession) && "0".equals(removeSession))){
                        	request.getSession().invalidate();//清空session
                        }

                        boolean toChangePwdTip = false;
                        if(isValidate){
                            if (SysUser.STATUS_FREEZE_DISABLE.equals(sysUser.getFreezeStatus())
                                    || (defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),nowDate.plusMonths(-6).toDate(),nowDate.plusDays(1).toDate(),null) == 0l
                                    && defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),null,null,null) > 0l)) {
                                result = "尊敬的用户，您的账号已经连续半年未登陆系统，为了您的账户信息安全，系统已经关闭您的账号使用权限。如您需要继续使用本系统，请联系管理员申请开通。";
                                if (!SysUser.STATUS_FREEZE_DISABLE.equals(sysUser.getFreezeStatus())) {
                                    defaultLoginService.updateUserFreeStatus(sysUser.getUserId(),SysUser.STATUS_FREEZE_DISABLE);
                                    sysLogService.log(request, sysUser, "系统后台",  "禁用用户", "账户超过半年未使用", new Date(), "1", new Date(), null);
                                }
                                out.print(result);
                            }

                            if (md5.getDoubleMD5ofStr(ConstantDefine.SYSTEM_DEFAULTE_PASSWORD).equals(sysUser.getPassword())){
                                request.getSession().setAttribute("force_message", "尊敬的用户，您当前的系统登陆密码为初始密码，为了您的账号信息安全，请立即修改登录密码，谢谢!");
                                request.getSession().setAttribute("actionType","updateStartPassword");
                                toChangePwdTip = true;
                            } else if (defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),nowDate.plusMonths(-3).toDate(),nowDate.plusDays(1).toDate(),"修改密码") == 0L
                            		&& defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),nowDate.plusMonths(-3).toDate(),nowDate.plusDays(1).toDate(),"找回密码") == 0L
                                    && defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),null,null,null) > 0L){
                                request.getSession().setAttribute("force_message","尊敬的用户，您的账号密码已经超过三个月的有效期，为了您的账户信息安全，请修改您的系统登陆密码。 ");
                                request.getSession().setAttribute("actionType","forceUpdatePassword");
                                toChangePwdTip = true;

                            }else if(SysUser.STATUS_FREEZE_FAIL.equals(sysUser.getFreezeStatus())
                                    || (defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),nowDate.plusMonths(-1).toDate(),nowDate.plusDays(1).toDate(),null) == 0l
                                    && defaultLoginService.neverUseBetweenTime(sysUser.getAccount(),null,null,null) > 0l)) {
                                request.getSession().setAttribute("force_message","尊敬的用户，您的账号已经连续超过一个月未登录系统，为了您的账户信息安全，请使用手机短信重新进行验证激活。 ");
                                request.getSession().setAttribute("actionType","activtionByMobile");

                                if (!SysUser.STATUS_FREEZE_FAIL.equals(sysUser.getFreezeStatus())) {
                                    defaultLoginService.updateUserFreeStatus(sysUser.getUserId(),SysUser.STATUS_FREEZE_FAIL);
                                    sysLogService.log(request, sysUser, "系统后台",  "失效用户", "账户超过一个月未使用", new Date(), "1", new Date(), null);
                                }

                                toChangePwdTip = true;
                            }

                        }


                        sysUser.setPrivileges(defaultLoginService.loadUserPrivilges(sysUser.getUserId()).get("privileges"));//加载权限

                        request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, sysUser);

                        if (toChangePwdTip) {
                            request.getSession().setAttribute("user", sysUser);
                            if(sysUser.getMobile()!=null && sysUser.getMobile().length()>3){
                                String userMobile = sysUser.getMobile();
                                int length = sysUser.getMobile().length();
                                String mobile = sysUser.getMobile().substring(3,length-2);
                                StringBuffer b=new StringBuffer();
                                for(int i=0;i<mobile.length();i++){
                                    b.append("*");
                                }
                                String phone=userMobile.substring(0,3)+b.toString()+userMobile.substring(length-2,length);
                                request.getSession().setAttribute("userPhone",phone);
                            }
                            SysOrganization org=organizationService.queryLevel2ByOrgId(sysUser.getOrganizationId());
                            request.getSession().setAttribute("userOrg",org.getOrgName());
//                            return "/core/login/toChangePwdTip";
                            out.print("0@#@core/login/toChangePwdTip");
                        }

                        //下发登陆成功短信提醒
//                        smsService.sendLoginSuccess(sysUser);
//                        emailService.sendLoginSuccess(request, sysUser);
                        sysLogService.log(request, sysUser, "用户登录",  "登录", "登录成功!", new Date(), "1", new Date(), null);
                       //                    return "redirect:/core/portal/portal";
                        out.print("0@#@core/portal/portal");
                    }
                }
        		
        	}
        	// out.print(result);
			
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登陆操作发生错误，详情如下：",e);
            request.setAttribute("message", "系统繁忙，请稍后再试!");
            out.print("5@#@系统繁忙，请稍后再试!");
//            return "/core/login/login";
        } finally {
        	if(out != null) {
        		out.close();
        	}
        }
    }

    
	/**
	 * 判断是否超级管理员
	 */
	private boolean isSuper(HttpServletRequest request, String account,
			String password, PrintWriter out) throws Exception {
		//超级管理员判断
		List<SysUser> superUsers = DataCache.getInstance().getSuperUsers();
		if(null!=superUsers){
			for (SysUser item : superUsers) {
				if(item.getAccount().equals(account) && item.getPassword().equals(password)){
					//超级用户
					SysUser superUser = new SysUser();
					superUser.setUserName(item.getAccount());
					superUser.setAccount(item.getAccount());
					superUser.setPassword(item.getPassword());
					superUser.setOrganizationId(item.getOrganizationId());
					superUser.setUserId("-1");
					superUser.setMobile("-1");
					superUser.setEmail("-1");
					superUser.setIsReceiveSms("0");
					superUser.setUserDefaultRole("-1");
					superUser.setIsActivate("1");
					superUser.setCreateTime(new Date());
					superUser.setFreezeStatus("0");
					superUser.setOrderNum(0);
					superUser.setPasswordOld("-1");
					superUser.setPasswordNew("-1");
					superUser.setLastUpdateTime(new Date());
					superUser.setLoginFailCount(0L);
					superUser.setLastLoginFailDate(new Date());
					//所有模块code
					List<SysModule> moduleList = (List<SysModule>) startUpService.loadModule();
					List<String> privileges = new ArrayList<String>();
					for (SysModule sysModule : moduleList) {
						privileges.add(sysModule.getModuleCode());
					}
					superUser.setPrivileges(privileges);
					//生成session
					request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, superUser);
					out.print("0@#@core/portal/portal");
					return true;
				}
			}//for
			return false;
		}
		return false;
	}



    /**
     * 用户登录
     *
     * @return
     */
    @RequestMapping(value = "doLoginNew", method = RequestMethod.POST)
    public @ResponseBody String  doLoginNew(HttpServletRequest request, HttpServletResponse response, String rememberPassword, String account, String pwd) {
        String data = null;
        MD5Helper md5 = new MD5Helper();
        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SysUser sysUser=defaultLoginService.checkUserByAccount(account);
            if(sysUser!=null){
                if(sysUser.getFreezeStatus()!=null && !sysUser.getIsActivate().trim().equals("") && sysUser.getIsActivate().trim().equals("0")
                        && !sysUser.getFreezeStatus().trim().equals("") && sysUser.getFreezeStatus().trim().equals("1") ){
                    request.setAttribute("message", "你的帐号已被禁用，且被冻结！");
                    data= "DisableAndFreeze";

                }
                if(sysUser.getFreezeStatus()!=null && !sysUser.getIsActivate().trim().equals("") && sysUser.getIsActivate().trim().equals("0")){
                    request.setAttribute("message", "你的帐号已被禁用！");
                    data= "Disable";
                }
                if(sysUser.getFreezeStatus()!=null && !sysUser.getFreezeStatus().trim().equals("") && sysUser.getFreezeStatus().trim().equals("1")){
                    request.setAttribute("message", "你的帐号已被冻结！");
                    data= "Freeze";
                }

            }
            if(defaultLoginService.checkLoginFailure(request, account)){
                request.setAttribute("message", "您今天已连续错误输入密码5次，请明天再登录！");
                data= "MoreThanFive";
            }
            SysUser loginedUser = defaultLoginService.checkLogin(account, pwd);
            new RuntimeException();
            if (loginedUser != null) {
                if (loginedUser.getIsActivate().equals("0") || (loginedUser.getFreezeStatus()!=null && loginedUser.getFreezeStatus().trim().equals("1"))) {
                    if(loginedUser.getIsActivate().equals("0")){
                        request.setAttribute("message", "你的帐号已被禁用");
                        data= "Disable";
                    }else{
                        request.setAttribute("message", "你的帐号已被冻结");
                        data= "Freeze";
                    }

                } else {
//					request.getSession().setAttribute(SystemConstant.DEFAULT_PROJECT, loginedUser.getDefaultProject());
                    request.getSession().invalidate();//清空session
                    if(request.getCookies() != null){
                        Cookie cookie = request.getCookies()[0];//获取cookie
                        cookie.setMaxAge(0);//让cookie过期
                    }
                    request.getSession().setAttribute(SystemConstant.SESSION_VISITOR, loginedUser);

                    defaultLoginService.clearLoginFailure(account);
                    sysLogService.log(request, loginedUser, "用户登录",  "登录", "登录成功!", new Date(), "1", new Date(), null);

                    int days = 30;
                    int maxAge = days * 24 * 60 * 60;
                    if("on".equals(rememberPassword)){
                        addCookie(response, "rememberPassword", rememberPassword, maxAge);
                        addCookie(response, "account", account, maxAge);
                        addCookie(response, "pwd", pwd, maxAge);
                    }else{
                        delCookie(response, "rememberPassword");
                        delCookie(response, "account");
                        delCookie(response, "pwd");
                    }
                    String loginPassword = md5.getDoubleMD5ofStr(pwd);
                    String nowPassword = md5.getDoubleMD5ofStr("MM2013@CMCC.com");
                    if(loginPassword.equals(nowPassword)){
                        request.setAttribute("message", "您的账号密码为默认密码，请修改您登录的密码！");
                        data= "toChangePassword";
                    }else{
                        data= "true";
                    }
                }
            } else{
                defaultLoginService.saveSysLoginFailure(request, account);
                defaultLoginService.updateFreezeStatus(account);
                int loginFailureTimes = defaultLoginService.querySysLoginFailureTimes(account);
                if(loginFailureTimes < 2){
                    request.setAttribute("message", "用户名或密码错误");
                    return "UserNameOrPwdError";
                }else if(loginFailureTimes >= 2 && loginFailureTimes <= 4){
                    request.setAttribute("message", "用户名或密码错误，您今天允许尝试登陆次数还剩" + (5 - loginFailureTimes) + "次");
                    String times = String.valueOf(5 - loginFailureTimes);
                    data= "have"+times;
                }else{
                    request.setAttribute("message", "您今天已连续错误输入密码5次，请明天再登录！");
                    data= "MoreThanFive";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "系统繁忙，请稍后再试!");
            data= "error";
        }
        return data;
    }

    /**
     * 设置cookie
     * @param response
     * @param name  cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    private static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0){
            cookie.setMaxAge(maxAge);
        }
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param response
     * @param name  cookie名字
     */
    private static void delCookie(HttpServletResponse response, String name){
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "loginOut", method = RequestMethod.GET)
    public void loginOut(HttpServletRequest request,HttpServletResponse response) {
        try {
            if (getVisitor(request) != null)
                sysLogService.log(request, getVisitor(request), "用户登录",  "登出", "登出成功!", new Date(), "1",null, new Date());

            defaultLoginService.cleanSession(request);
		    delCookie(response, "LOGIN_ID");
		    delCookie(response, "USER_NAME");
		    delCookie(response, "NFJDIAPSSOToken");
		    
		    Cookie mtokenkeycookie = new Cookie("NFJDIAPSSOToken",null);
		    mtokenkeycookie.setDomain("gmcc.net");
		    mtokenkeycookie.setMaxAge(0);
		    mtokenkeycookie.setPath("/");
		    response.addCookie(mtokenkeycookie);
		    
		    Cookie mtokenkeycookie3 = new Cookie("NFJDIAPSSOToken",null);
		    mtokenkeycookie3.setDomain("soa.nfjd.gmcc.net");
		    mtokenkeycookie3.setMaxAge(0);
		    mtokenkeycookie3.setPath("/");
		    response.addCookie(mtokenkeycookie3);
		    
		    Cookie mtokenkeycookie5 = new Cookie("NFJDIAPSSOToken",null);
		    mtokenkeycookie5.setDomain(".gmcc.net");
		    mtokenkeycookie5.setMaxAge(0);
		    mtokenkeycookie5.setPath("/");
		    response.addCookie(mtokenkeycookie3);
		    
		    Cookie mtokenkeycookie1 = new Cookie("iPlanetDirectoryPro",null);
		    mtokenkeycookie1.setDomain("glpt.nfjd.gmcc.net/");
		    mtokenkeycookie1.setMaxAge(0);
		    mtokenkeycookie1.setPath("/");
		    response.addCookie(mtokenkeycookie1);
		    
            String path = request.getContextPath();
        	String basePath = request.getScheme() + "://"
        			+ request.getServerName() + ":" + request.getServerPort()
        			+ path + "/";
            String str = "<script>window.top.location.href = '" + basePath + "core/toLogin'</script>";
            PrintWriter out = response.getWriter();
            out.print(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
