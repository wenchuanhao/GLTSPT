package com.cdc.sys.controller;


import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.cdc.email.service.IEmailService;
//import com.cdc.sms.service.ISmsService;
import com.cdc.sys.service.IDefaultLoginService;
import com.cdc.sys.service.ISysLogService;
import com.cdc.sys.service.ISysUserService;
import com.trustel.common.Encrypt;
import com.trustel.common.MD5Helper;

/**
 * 找回用户密码
 * @author fuJ
 * date:2014-11-17
 */
@Controller
@RequestMapping(value = "/getPwd/")
public class GetUserPwdController {
	
	private final static String validateWay_sms = "1";
	private final static String validateWay_email = "2";
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
    private IDefaultLoginService defaultLoginService;
	
	@Autowired
	private ISysLogService sysLogService;
	
//	@Autowired
//	private ISmsService smsService;
//	
//	@Autowired
//	private IEmailService emailService;
	
	/**
	 * 跳转到用户找回密码页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toGetPwd", method = {RequestMethod.POST,RequestMethod.GET})
	public String toGetPwd(HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("checkDycodePass")!=null){
			session.removeAttribute("checkDycodePass");			
		}	
		if(session.getAttribute("actionType")!=null){
			session.removeAttribute("actionType");			
		}		
		try {	
			session.setAttribute("actionType","toGetPwd");
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		//
		//debug
//		request.setAttribute("account", "lijunhao");
//		request.setAttribute("userName", "李俊豪");
//		request.setAttribute("mobile", "15820638751");
		//
		
		
		return "core/login/getUserPwd";
	}
	
	/**
	 * 跳转到获取密码成功页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toGetUserPwdSuc", method = {RequestMethod.POST,RequestMethod.GET})
	public String toGetUserSuc(HttpServletRequest request) {
		if(request.getSession().getAttribute("actionType")==null||request.getSession().getAttribute("actionType")==""){
        	return "core/login/getUserPwd";
        }else{
        	if(!request.getSession().getAttribute("actionType").equals("toPwdSuc")){
        		return "core/login/getUserPwd";
        	}
        	
        }
		return "core/login/getUserPwdSuc";
	}
	/**
	 * 测试用户输入账户是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkAccountName", method = {RequestMethod.POST})
	public @ResponseBody String checkAccountName(HttpServletRequest request, HttpServletResponse response) {
		String result = "";  
		try {
			Encrypt encrypt = new Encrypt();
    		String account = request.getParameter("account").trim();
  	        if(account != null&&!"".equals(account)){  	         
  	        	account = encrypt.dencrypt(account);
  	        	if(sysUserService.checkAccountName(account)){ 
  	        		SysUser sysUser=sysUserService.getSysUserByAccount(account);
  	        		int listSize = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
  	     	        result = (""+listSize).trim();
  	        	}else{
  	        		result="no";
  	        	}
  	        }   		
		 }catch (Exception e) {
			result = "error";
			e.printStackTrace();
		 }
		return result;
	}
	
	
	/**
	 * 测试用户输入姓名是否匹配
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkUserName", method = {RequestMethod.POST})
	public @ResponseBody String checkUserName(HttpServletRequest request, HttpServletResponse response) {
		String result = "";  
		try {
    		String account = request.getParameter("account").trim(); 
    		String userName = request.getParameter("userName").trim();
  	        if(account != null&&!"".equals(account)){
  	        	//解密
  	        	Encrypt encrypt = new Encrypt();
  	        	account = encrypt.dencrypt(account);
  	        	userName = encrypt.dencrypt(userName);
  	        	if(sysUserService.checkAccountName(account)){ 
  	        		SysUser sysUser=sysUserService.getSysUserByAccount(account);
  	        		if(StringUtils.isNotEmpty(sysUser.getUserName())){
  	        			if(sysUser.getUserName().equals(userName)){
  	        				 return "pass";
  	        			}else{
  	        				result="no";
  	        			}
  	        			   
  	        		}  	        		
  	        	}else{
  	        		result="addAccount";
  	        	}
  	        }   		
		 }catch (Exception e) {
			result = "error";
			e.printStackTrace();
		 }
		return result;
	}
	/**
	 * 测试用户输入手机号码是否匹配
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkMobile", method = {RequestMethod.POST})
	public @ResponseBody String checkMobile(HttpServletRequest request, HttpServletResponse response) {
		String result = "";  
		try {
    		String account = request.getParameter("account").trim(); 
    		String mobile = request.getParameter("mobile").trim();
  	        if(account != null&&!"".equals(account)){
  	        	Encrypt encrypt = new Encrypt();
  	        	account = encrypt.dencrypt(account);
  	        	mobile = encrypt.dencrypt(mobile);
  	        	if(sysUserService.checkAccountName(account)){ 
  	        		SysUser sysUser=sysUserService.getSysUserByAccount(account);
  	        		if(StringUtils.isNotEmpty(sysUser.getMobile())){
  	        			if(sysUser.getMobile().equals(mobile)){
  	        				return "pass";
  	        			}else{
  	        				result="no";  	        				
  	        			}
  	        			
  	        		}  	        		
  	        	}else{
  	        		result="addAccount";
  	        	}
  	        }   		
		 }catch (Exception e) {
			result = "error";
			e.printStackTrace();
		 }
		return result;
	}
	
	/**
	 * 检测验证码是否正确
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkRandCode", method = {RequestMethod.POST})
	public @ResponseBody String checkRandCode(HttpServletRequest request, HttpServletResponse response) {
		String result = "";  
		try {
    		String randCode = request.getParameter("randCode").trim(); 
    		System.out.println("randCode  checkRandCode = " + randCode);
    		/*WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    		RedisSessionManager redisSessionManager = (RedisSessionManager)context.getBean("redisSessionManager");
    		String codet = redisSessionManager.getString("randCode");  */  		
    		String codet = (String)request.getSession().getAttribute("randCode");
    		System.out.println("codet = " + codet);
  	        if(randCode != null&&!"".equals(randCode)){  	         
  	        	if(!(randCode.equalsIgnoreCase(codet))){ 
  	        		result="no";       		
  	        	}else{
  	        		result="pass";
  	        	}
  	        }   		
		 }catch (Exception e) {
			result = "error";
			e.printStackTrace();
		 }
		return result;
	}
	
	/**
	 * 检测个人信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkUserInfo", method = {RequestMethod.POST})
	public @ResponseBody String checkUserInfo(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		String result = ""; 		
		try {			
			/*WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    		RedisSessionManager redisSessionManager = (RedisSessionManager)context.getBean("redisSessionManager");
    		String codet = redisSessionManager.getString("randCode");*/
			/*System.out.print(request.getSession().getAttribute("actionType"));*/
			if(request.getSession().getAttribute("actionType")==null||request.getSession().getAttribute("actionType")==""){
            	return "toBack";
            }else{
            	  	if(!request.getSession().getAttribute("actionType").equals("toGetPwd")){
            		return "toBack";
            	}
            	
            }
			String codet = (String)session.getAttribute("randCode");
			String account = request.getParameter("account").trim();
    		String userName = request.getParameter("userName").trim();
    	    String mobile=request.getParameter("mobile").trim();
    	    String randCode=request.getParameter("randCode").trim();
    	    
    	    Encrypt encrypt = new Encrypt();
    	    account = encrypt.dencrypt(account);
    	    userName = encrypt.dencrypt(userName);
    	    mobile = encrypt.dencrypt(mobile);
    	    
    	    if (!(randCode.equalsIgnoreCase(codet))) {  //忽略验证码大小写  
    	    	result = "1";    	        
    	    }else { 
    	    	result=sysUserService.checkUserInfo(account, userName, mobile);    	        
    	    }    	    		
		 }catch (Exception e) {
			result = "3";
			e.printStackTrace();
		 }
		return result;
	}
		
	/**
	 * 跳转到手机短信验证
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toCheckPhone", method = {RequestMethod.GET,RequestMethod.POST})
	public String toCheckPhone(HttpServletRequest request) {
		HttpSession session=request.getSession();
		Encrypt encrypt = new Encrypt();
		String account;
		String actionType=(String)session.getAttribute("actionType");
		if(request.getParameter("account")==null||request.getParameter("account")==""||actionType==null||actionType==""){
			/*if(gAccount!=null&&gAccount!=""){
				account=gAccount.trim();
			}else{
				return "core/login/getUserPwd";
			}	*/	
			return "redirect:/getPwd/toGetPwd";
		}else{
			if(!actionType.equals("toGetPwd")){
				return "redirect:/getPwd/toGetPwd";
			}
			account = request.getParameter("account").trim();
			//解密
			account = encrypt.dencrypt(account);
		}		
		try {		
			SysUser sysUser=sysUserService.getSysUserByAccount(account); 
			String userName = sysUser.getUserName();
    	    String userMobile=sysUser.getMobile();
    	    String email=sysUser.getEmail();
    	    String userEmail = null;
    	    if(StringUtils.contains(email, "@")){
    			String emailLeft = StringUtils.substringBefore(email, "@");
    			String emailRight = StringUtils.substringAfter(email, "@");
    			String emailLeft_first = StringUtils.left(emailLeft, 1);
    			String emailLeft_last = StringUtils.right(emailLeft, 1);
    			String padding = StringUtils.leftPad("", emailLeft.length()-2 ,"*");
    			userEmail = emailLeft_first+padding+emailLeft_last +"@"+ emailRight;
    	    }
    	    String orgName=sysUserService.getUserOrgNameByAccount(account);
            int length = sysUser.getMobile().length();
            String mobile = sysUser.getMobile().substring(3,length-2);
            StringBuffer b=new StringBuffer();
            for(int i=0;i<mobile.length();i++){
                b.append("*");
            }
            String phone=userMobile.substring(0,3)+b.toString()+userMobile.substring(length-2,length);
            int listSize = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
            //request.getSession().setAttribute("userPhone",phone);
            request.setAttribute("account", account);
            request.setAttribute("userName",userName);
            request.setAttribute("userPhone",phone);
            request.setAttribute("codeSize",listSize);
            request.setAttribute("orgName",orgName);
            request.setAttribute("userMobile",userMobile);
            request.setAttribute("status","checkPhone");  
            request.setAttribute("email", email);
            request.setAttribute("userEmail", userEmail);
            session.setAttribute("actionType", "checkPhone");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "core/login/getUserPhoneCheck";
		return "core/login/getUserPwd";
	}
	
	 /**
     * 调用验证码接口发送验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getDynamicCode", method=RequestMethod.POST)
    @ResponseBody
    public String sendAndReturnValidCode(HttpServletRequest request){
        HttpSession session=request.getSession();
        Encrypt encrypt = new Encrypt();
        
        String account = request.getParameter("account").trim();
        String servMobile = request.getParameter("servMobile").trim();
        String validateWay = request.getParameter("validateWay").trim();
        account = encrypt.dencrypt(account);
        servMobile = encrypt.dencrypt(servMobile);
        
        String result = null;
        String volidCode = null;
        if(request.getSession().getAttribute("actionType")==null||request.getSession().getAttribute("actionType")==""){
        	return "toBack";
        }else{
        	if(!request.getSession().getAttribute("actionType").equals("checkPhone")){
        		return "toBack";
        	}        	
        }
        try{
            //调用接口发送验证码     
        	    SysUser sysUser=sysUserService.getSysUserByAccount(account);
                if(servMobile.equals(sysUser.getMobile())){
                    if(defaultLoginService.checkGetCodeTimes(sysUser.getAccount())){
                        int getCodeTimes = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
                        if(getCodeTimes==5){
                            defaultLoginService.saveSysGetCodeTimes(sysUser.getAccount());
                        }
                        return "numberOut";//超过五次
                    }else{
                    	if(validateWay_sms.equals(validateWay)){
                    		//volidCode = smsService.sendGetPasswordCaptcha(sysUser);
                    		session.setAttribute("limitMinute", 3); //3分钟 超时
                    	}else if (validateWay_email.equals(validateWay)){
                    		//volidCode = emailService.sendGetPasswordCaptcha(sysUser);
                    		session.setAttribute("limitMinute", 10);//10分钟超时
                    	}
                        defaultLoginService.saveSysGetCodeTimes(sysUser.getAccount());
                        int getCodeTimes = defaultLoginService.queryGetCodeTimes(sysUser.getAccount());
                        int lastTimes = 5-getCodeTimes;
                        //volidCode = "eV7s9D";
                        Date now = new Date();
                        long nowTime = now.getTime();
                        session.setAttribute("nowTime", nowTime);
                        //session.setAttribute("validCode", volidCode);                        
                        result = volidCode+lastTimes+nowTime;
                        System.out.println(result);
                    }
                }else{
                    result = "errorPhone";
                }
            
        }catch(Exception e){
            e.printStackTrace();
            result = "sms_error";
        }
        return result;

    }
	
    /**
     * 验证手机验证码是否正确
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "checkDynamicCode", method = {RequestMethod.POST})
	public @ResponseBody String checkDynamicCode(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session=request.getSession();
    	Encrypt encrypt = new Encrypt();
    	String result = "";  
		try {
			String sendCode = (String) session.getAttribute("validCode");
            long sendTime = (Long) session.getAttribute("nowTime");
            String verifyCode = request.getParameter("verifyCode");
            String account = request.getParameter("account").trim();
            
            //解密
            account = encrypt.dencrypt(account);
            
            SysUser sysUser=sysUserService.getSysUserByAccount(account);
            if(request.getSession().getAttribute("actionType")==null||request.getSession().getAttribute("actionType")==""){
            	return "toBack";
            }else{
            	if(!request.getSession().getAttribute("actionType").equals("checkPhone")){
            		return "toBack";
            	}            	
            }
            if(session.getAttribute("validCode")==null || session.getAttribute("nowTime")==null){
                return "toGetCode";
            }
            
            long nowTime  =new Date().getTime();
            int limitMinute = (Integer) session.getAttribute("limitMinute");
            if((nowTime-sendTime)>limitMinute*60*1000){
                return "outTime";
            }
            
            if(!verifyCode.equalsIgnoreCase(sendCode)){
                //验证码不正确
                return "3";
            }else {
                //账户激活
            	    session.setAttribute("checkDycodePass", "pass");
            		defaultLoginService.updateUserFreeStatus(sysUser.getUserId(),SysUser.STATUS_FREEZE_NORMAL);
                    sysLogService.log(request, sysUser, "系统管理--用户登录",
                            "账户激活", "用户" + sysUser.getUserName() + "利用手机激活了账户", new Date(), "3", new Date(), null);
                    return "2";                
            }
   				
		 }catch (Exception e) {
			result = "error";
			e.printStackTrace();
		 }
		return result;
	}
    
    /**
     * 跳转到更新密码是添加标示
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "toUpdatPwd", method = RequestMethod.POST)
    public @ResponseBody String toUpdatPwd(HttpServletRequest request, HttpServletResponse response) {
    	try {   
    		 if(request.getSession().getAttribute("actionType")==null||request.getSession().getAttribute("actionType")==""){
    	        	return "toBack";
    	        }else{
    	        	if(!request.getSession().getAttribute("actionType").equals("checkPhone")){
    	        		return "toBack";
    	        	}else{
    	        		request.getSession().setAttribute("actionType", "updatPwd");
    	        		return "success";
    	        	}
    	     }
        } catch (Exception e) {
            e.printStackTrace(); 
            return "error";
        }
    }
    
    
    
    /**
     * 用户设定密码
     * @param request
     * @param response
     */
    @RequestMapping(value = "updateUserPwd", method = RequestMethod.POST)
    public @ResponseBody String updateUserPwd(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session=request.getSession();
    	//解密
    	Encrypt encrypt = new Encrypt();
    	String passwordNew =request.getParameter("newPwd");
    	passwordNew = encrypt.dencrypt(passwordNew);
    	String account = request.getParameter("account").trim();			
    	account = encrypt.dencrypt(account);
    	
		SysUser sysUser=sysUserService.getSysUserByAccount(account); 
		MD5Helper helper = new MD5Helper();
		if(request.getSession().getAttribute("actionType")==null||request.getSession().getAttribute("actionType")==""){
        	return "toBack";
        }else{
        	if(!request.getSession().getAttribute("actionType").equals("updatPwd")){
        		return "toBack";
        	}            	
        }		
		if(session.getAttribute("checkDycodePass")!=null&&session.getAttribute("checkDycodePass").equals("pass")){			
		}else{
			return "toBack";
		}
    	try {        
    		String newPwd = helper.getDoubleMD5ofStr(passwordNew);//加了密的新密码
    		sysUser.setPassword(newPwd);//保存加了密的新密码            
            sysUser.setPasswordNew(passwordNew);//保存没有加密的新密码
            sysUser.setLastUpdateTime(new Date());
            sysUser.setFreezeStatus(SysUser.STATUS_FREEZE_NORMAL);//设置为正常状态..
            sysUserService.modifySysUser(sysUser);
            //强制用户修改密码成功提示
//            smsService.sendGetPassword(sysUser);
//            emailService.sendGetPassword(request, sysUser);
            
            sysLogService.log(request,sysUser, "系统管理--用户登录",
                    "找回密码", "用户" + sysUser.getUserName() + "设置了密码", new Date(), "3", new Date(), null);
            request.getSession().setAttribute("actionType", "toPwdSuc");
            if(session.getAttribute("checkDycodePass")!=null){
    			session.removeAttribute("checkDycodePass");			
    		}	
            return "2";
            
        } catch (Exception e) {
            e.printStackTrace(); 
            return "error";
        }
    }
    
    

}
