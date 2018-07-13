<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>项目统一管理平台</title>
	<link rel="shortcut icon" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon" />
	<link rel="bookmark" href="/SRMC/rmpb/images/chinamobile.ico" type="image/x-icon" />
	<link href="/SRMC/rmpb/css/getPwd.css" rel="stylesheet" type="text/css" />
	<link href="/SRMC/rmpb/css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="/SRMC/rmpb/js/jqueryui/css/ui-lightness/jquery-ui-1.10.3.custom.css" />
	<link href="/SRMC/rmpb/css/ieTip/css/style.css" type="text/css" rel="stylesheet" />
	<link href="/SRMC/rmpb/css/adapterManage/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/SRMC/dc/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/SRMC/rmpb/js/tab.js"></script>
     
    <script type="text/javascript" src="<%=basePath %>dwr/engine.js"></script>
    <script type="text/javascript" src="<%=basePath %>dwr/util.js"></script>
	<script type="text/javascript" src="<%=basePath %>dwr/interface/encrypt.js"></script>
	
	<style type="text/css">
		.infoClass {
			font-size: 15px;
			color: #6A6464;
			text-align: left;
			display: none;
		}
	</style>
	<script type="text/javascript"> 
	
	var g_suffix =""; //全局的后缀，如果为邮箱方式，后缀为 "_Email"
	//目前用到此标记的 div ，分别为 id="sendCode"  id="times"  id="noTimes"
	
	   /* T1,T2,T4,T4,记录第一步提交时验证状态，以便点击下一步时验证输入信息 */
       T1=1;
       T2=1;
       T3=1;
       T4=1;
       ys=1;//手机验证是否通过 1：不同过；2：通过    
       randCodePass=1;//验证码是否通过      
       jQuery(document).ready(function(){
	        var status= jQuery("#status").val();
	        if(status=="checkPhone"){
	            jQuery("#st1").hide();
	            jQuery("#st2").show();
	            jQuery("#accountCheckDiv").css("display", "none");		           
				jQuery("#checkPhoneDiv").css("display", "block");
								
	        }	        
	   }); 
	   
		/*  第一步提交    */
		function oneCheck(){
		        var account=jQuery("#account").val();	       
		        var userName=jQuery("#userName").val();
		        var mobile=jQuery("#mobile").val();
		        var randCode=jQuery("#randCode").val();
		        var actionType=jQuery("#actionType").val();
		        account=jQuery.trim(account);	        
		        userName=jQuery.trim(userName);
		        mobile=jQuery.trim(mobile);
		        randCode=jQuery.trim(randCode);
			    if(account!=null && account!=''){
			        jQuery("#accountNullError").hide();                  
					if(account.length <3){	 
	                    jQuery("#accountNotExitError").hide();
	                    jQuery("#accountError").show();	
	                    toClearUserNameInfo();
                        toClearMobileInfo(); 
                        jQuery("#randCode").val("");
                        reloadRandCodeImage();
                        toClearRandCodeInfo();                  
	                    T1=1;
	                    return;					
					}    
			   }else{
			        T1=1;	
			        jQuery("#accountNotExitError").hide();
			        jQuery("#accountError").hide();
			        jQuery("#accountNullError").show();
			        jQuery("#account").focus();
			        toClearUserNameInfo();
		            toClearMobileInfo();
		            jQuery("#randCode").val("");
		            reloadRandCodeImage();
	                toClearRandCodeInfo(); 		
			        return ;				    
			   };		   
			   if(userName!=null && userName!=''){
			         jQuery("#userNameNullError").hide();	
			         if(userName.length >20){	                      
	                    jQuery("#userNameError").show();
	                    T2=1;
	                    jQuery("#randCode").val("");
	                    reloadRandCodeImage();
	                    toClearRandCodeInfo(); 							
						return;
					  }	    
			   }else{
			        jQuery("#userNameError").hide();
			        jQuery("#userNameNullError").show();
			        jQuery("#userName").focus();
			        T2=1;
			        jQuery("#randCode").val("");
			        reloadRandCodeImage();
	                toClearRandCodeInfo(); 			
					return;	    
			  }  
			  		    
			  if(mobile!=null && mobile!=''){
			        jQuery("#mobileNullError").hide();	
			        if (mobile.length !=11){
		                 jQuery("#mobileError").show();
		                 jQuery("#mobile").focus();
		                 T3=1;	
		                 jQuery("#randCode").val("");
		                 reloadRandCodeImage();
	                     toClearRandCodeInfo(); 			
				         return ;
					}else if(!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(mobile))){
		                 jQuery("#mobileError").show();
		                 jQuery("#mobile").focus();
		                 T3=1;	
		                 jQuery("#randCode").val("");
		                 reloadRandCodeImage();
	                     toClearRandCodeInfo(); 				
				         return ;
					}		
			    }else{
			        jQuery("#mobileError").hide();
			        jQuery("#mobileNullError").show();
			        jQuery("#mobile").focus();
			        T3=1;	
			        jQuery("#randCode").val("");
			        reloadRandCodeImage();
	                toClearRandCodeInfo(); 				
					return ;	    
			    }  		    
			    var randCode=jQuery("#randCode").val();    
			    randCode=jQuery.trim(randCode);  
			    if(randCode!=null && randCode!=''){
			         jQuery("#randCodeNullError").hide();        
			         if(randCode.length>0){
			                var urlt4="<%=basePath%>getPwd/checkRandCode";
							var paramst4 = {
								randCode: randCode
							};
							jQuery.ajax({  
						          type : "post",
						          url :urlt4,
						          data : paramst4,  
						          async : false,  
						          success : function(data){
						             if (data != ""&&data!="no"&&data!="error") {
									    jQuery("#randCodePassError").hide();								    
									    randCodePass=2;
									    T4=2;	
									}
									if (data == "no") {
								        jQuery("#randCodePassError").show(); 
								        jQuery("#notPassInfo").hide();
								        jQuery("#randCode").val("");
								        jQuery("#randCode").focus();
								        reloadRandCodeImage();                                     
								        T4=1;
								        return ;	
									}
									if (data == "error"||data == "addAccount") {
									    jQuery("#notPassInfo").hide();
									    jQuery("#randCode").val("");
									    jQuery("#randCode").focus();
									    reloadRandCodeImage();                                    
									    T4=1;
									    return ;
									}
						         }  
						    });       
					 }
			    }else{
			        jQuery("#randCodePassError").hide(); 
			        jQuery("#randCodeNullError").show();		        
			        jQuery("#notPassInfo").hide();
			        reloadRandCodeImage();
			        jQuery("#randCode").focus();
			        T4=1;
					return ;	    
			    }  
			    if(T4!=2){
			      return;
			    }
			    //加密start
			    try{
			    	dwr.engine.setAsync(false);
			       	encrypt.ecrypt(account,{callback:function(data){
			           	jQuery("#maccount").val(data);
			           	account = data;
			        }});
			       	encrypt.ecrypt(userName,{callback:function(data){
			           	jQuery("#muserName").val(data);
			           	userName = data;
			        }});
			       	encrypt.ecrypt(mobile,{callback:function(data){
			           	jQuery("#mmobile").val(data);
			           	mobile = data;
			        }});
			        dwr.engine.setAsync(true);
	        	}catch(e){
	        		
	        	}
		       	/* dwr.engine.setAsync(false);
		       	encrypt.ecrypt(account,{callback:function(data){
		           	jQuery("#maccount").val(data);
		           	account = data;
		        }});
		       	encrypt.ecrypt(userName,{callback:function(data){
		           	jQuery("#muserName").val(data);
		           	userName = data;
		        }});
		       	encrypt.ecrypt(mobile,{callback:function(data){
		           	jQuery("#mmobile").val(data);
		           	mobile = data;
		        }});
		        dwr.engine.setAsync(true); */
		        //加密end
		        
			    var urlt1="<%=basePath%>getPwd/checkAccountName";
			    
				var paramst1 = {account : account};						
				jQuery.ajax({  
		          type : "post",  
		          url :urlt1,  
		          data : paramst1,  
		          async : false,  
		          success : function(data){
		                if (data != ""&&data!="no"&&data!="error") {
		                    T1=2;		
						    jQuery("#notPassInfo").hide();
							jQuery("#accountError").hide();
						}
						if (data == "no") {	
						    T1=1;
						    jQuery("#accountError").hide();
					        jQuery("#notPassInfo").show();
					        toClearUserNameInfo();
	                        toClearMobileInfo();
	                        jQuery("#randCode").val("");
	                        reloadRandCodeImage();
	                        toClearRandCodeInfo(); 										       
	                        return ; 								       
						}
						if (data == "error") {
						   T1=1;
						   toClearUserNameInfo();
	                                toClearMobileInfo();	
	                                jQuery("#randCode").val("");
	                                reloadRandCodeImage();
	                                toClearRandCodeInfo(); 		
						   return ;					   
						}
		           }  
				});
				if(T1!=2){
			      return;
			    }		    
	            var urlt2="<%=basePath%>getPwd/checkUserName";
				var paramst2 = {
					account : account,
					userName: userName
				};
				jQuery.ajax({  
		          type : "post",  
		          url :urlt2,  
		          data : paramst2,  
		          async : false,  
		          success : function(data){				          
		                if (data != ""&&data!="no"&&data!="error"&&data!="addAccount") {
						    jQuery("#notPassInfo").hide();
						    jQuery("#userNameError").hide();
			                T2=2;						               
						}
						if (data == "no") {									
						    jQuery("#userNameError").hide();
					        jQuery("#notPassInfo").show();
					        jQuery("#randCode").val("");
					        reloadRandCodeImage();
	                        toClearRandCodeInfo(); 
					        T2=1;
					        return ;
						}
						if (data == "addAccount") {
						   T2=1;
						   jQuery("#randCode").val("");
						   reloadRandCodeImage();
	                       toClearRandCodeInfo(); 
						   return ;
						}
						if (data == "error") {
						   T2=1;
						   jQuery("#randCode").val("");
						   reloadRandCodeImage();
	                       toClearRandCodeInfo(); 
						   return ;
						}
		           } 
		        }); 								        
				if(T2!=2){
			      return;
			    }			
	            var urlt3="<%=basePath%>getPwd/checkMobile";
				var paramst3 = {
					account : account,
					mobile: mobile
				};
				jQuery.ajax({  
			          type : "post",  
			          url :urlt3,  
			          data : paramst3,  
			          async : false,  
			          success : function(data){
			              if (data != ""&&data!="no"&&data!="error"&&data!="addAccount") {
						    jQuery("#notPassInfo").hide(); 
						    jQuery("#mobileError").hide();				 
			                T3=2;			                
						}
						if (data == "no") {
						    jQuery("#mobileError").hide();				 
					        jQuery("#notPassInfo").show();
					        T3=1;
					        jQuery("#randCode").val("");	
					        reloadRandCodeImage();
	                        toClearRandCodeInfo(); 
					        return ;				        
						}
						if (data == "error"||data == "addAccount") {
						   T3=1;
						   jQuery("#randCode").val("");
						   reloadRandCodeImage();
	                       toClearRandCodeInfo(); 
						   return ;
						}
			          }  
			     }); 
			    if(T3!=2){
			      return;
			    }	
		        if(T1!=1&&T2!=1&&T3!=1&&T4!=1){	                     
		            var url="<%=basePath%>getPwd/checkUserInfo";
					var params = {
						account : account,
						userName : userName,
						mobile : mobile,
						randCode : randCode,
						actionType:actionType				
					};
					
					jQuery("#submitForm").attr("action","<%=basePath%>getPwd/toCheckPhone");
					document.forms[0].submit();
					
					/*jQuery.post(url, params, function(data) {
					    if (data == "0") {

						}
					    if (data == "1") {
							alert("验证码不匹配，请重新输入验证码");
							jQuery("#randCode").focus();
			                return ;	 
						}
						if (data == "2") {
							alert("您输入的手机号码/用户姓名/验证码有误，请重新输入");
			                return ;	
						}	
						if(data=="toBack"){
						   window.location.href="<%=basePath%>getPwd/toGetPwd";
						}			
						if (data == "7"||data == "3") {
							alert("系统出错，请与管理员联系");
						}
					});	*/
					
					
		        }	        
		}
	         
	    /* 检测输入验证码是否正确 */
	    function torandCodeMobile(){
	        var randCode=jQuery("#randCode").val();    
		    randCode=jQuery.trim(randCode);  
		    if(randCode!=null && randCode!=''){	 
		         jQuery("#randCodeNullError").hide();        
		         if(randCode.length>0){
		                var urlt="<%=basePath%>getPwd/checkRandCode";
						var paramst = {						
							randCode: randCode
						};
						jQuery.ajax({  
					          type : "post",  
					          url :urlt,  
					          data : paramst,  
					          async : true,  
					          success : function(data){
					             if (data != ""&&data!="no"&&data!="error") {
								    jQuery("#randCodePassError").hide(); 
								    jQuery("#randCodeOK").show();
								    T4=2;	
								}
								if (data == "no") {
								    jQuery("#randCodeOK").hide();						   
							        jQuery("#randCodePassError").show(); 
							        T4=1;
							        return false;	
								}
								if (data == "error"||data == "addAccount") {
								    jQuery("#randCodeOK").hide();	
								    T4=1;
								    return false;
								}
					         }  
					    }); 
				 }
		    }else{
		        jQuery("#randCodeOK").hide();
		        jQuery("#randCodePassError").hide(); 
		        jQuery("#randCodeNullError").show();
		        T4=1;
				return false;	    
		    }  
	    
	    }
	    
	    /* 检测输入验证码是否正确(同步检测) */
	    function torandCodeMobileAdd(){
	        var randCode=jQuery("#randCode").val();    
		    randCode=jQuery.trim(randCode);  
		    if(randCode!=null && randCode!=''){	 
		         jQuery("#randCodeNullError").hide();        
		         if(randCode.length>0){
		                var urlt="<%=basePath%>getPwd/checkRandCode";
						var paramst = {						
							randCode: randCode
						};
						jQuery.ajax({  
					          type : "post",  
					          url :urlt,  
					          data : paramst,  
					          async : false,  
					          success : function(data){
					             if (data != ""&&data!="no"&&data!="error") {
								    jQuery("#randCodePassError").hide(); 
								    jQuery("#randCodeOK").show();
								    randCodePass=2;
								    T4=2;	
								}
								if (data == "no") {
								    jQuery("#randCodeOK").hide();						   
							        jQuery("#randCodePassError").show(); 
							        T4=1;
							        return false;	
								}
								if (data == "error"||data == "addAccount") {
								    jQuery("#randCodeOK").hide();	
								    T4=1;
								    return false;
								}
					         }  
					    });               
				 }
		    }else{
		        jQuery("#randCodeOK").hide();
		        jQuery("#randCodePassError").hide(); 
		        jQuery("#randCodeNullError").show();
		        T4=1;
				return false;	    
		    }  
	    
	    }
	    
	     /* 清楚用户账号提示信息 */
	    function toClearAccountInfo(){         
	            jQuery("#accountNotExitError").hide();
		        jQuery("#accountError").hide();
		        jQuery("#accountNullError").hide();
	    }  
	    function toClearAccountInfoAdd(){         
	            jQuery("#accountNotExitError").hide();
		        jQuery("#accountError").hide();
		        jQuery("#accountNullError").hide();
		        jQuery("#notPassInfo").hide();
	    }  
	    
	    /* 清楚用户姓名提示信息 */
	    function toClearUserNameInfo(){         
	            jQuery("#userNamePassError").hide();
		        jQuery("#userNameError").hide();
		        jQuery("#userNameNullError").hide();	        
	    }
	    function toClearUserNameInfoAdd(){         
	            jQuery("#userNamePassError").hide();
		        jQuery("#userNameError").hide();
		        jQuery("#userNameNullError").hide();   
		        jQuery("#notPassInfo").hide(); 
	    }
	    /* 清楚手机号码提示信息 */
	    function toClearMobileInfo(){          
	            jQuery("#mobilePassError").hide();
		        jQuery("#mobileError").hide();
		        jQuery("#mobileOK").hide();
		        jQuery("#mobileNullError").hide();
	    }   
	    function toClearMobileInfoAdd(){          
	            jQuery("#mobilePassError").hide();
		        jQuery("#mobileError").hide();
		        jQuery("#mobileOK").hide();
		        jQuery("#mobileNullError").hide();
		        jQuery("#notPassInfo").hide();
	    }   
	     /* 清楚验证码码提示信息 */
	    function toClearRandCodeInfo(){          
	           jQuery("#randCodeOK").hide();
		       jQuery("#randCodePassError").hide(); 
		       jQuery("#randCodeNullError").hide();
	    }        
	     
		/**
		 * 刷新验证码
		 */
		function reloadRandCodeImage() {
			var date = new Date();
			var img = document.getElementById("randCodeImage");
			img.src = 'randCodeImage?a=' + date.getTime();
		}
		/* 点击换一张 */
		function changeRandCodeImage() {
			var date = new Date();
			var img = document.getElementById("randCodeImage");
			img.src = 'randCodeImage?a=' + date.getTime();
			jQuery("#randCodeOK").hide();
		    jQuery("#randCodePassError").hide(); 
		    jQuery("#randCodeNullError").hide();
		}
		
		/*  第二步提交    */
		function twoCheck(){			
		        var account=jQuery("#account").val();
		        account=jQuery.trim(account);
		        var verifyCode=jQuery("#verifyCode").val();
		        verifyCode=jQuery.trim(verifyCode);
		        if(account==""||account==null){	           
		           return ;
		        }
		        var code=jQuery("#code").val();	      
		        if(code==null||code==""){
		           jQuery("#toGetCodeDiv").show();
		           return ;
		        }
		        if(verifyCode==""||verifyCode==null){
		            ys=1;            
		            jQuery("#codeError").css("display", "none");
		            jQuery("#verifyCodeOK").hide();
		            jQuery("#toGetCodeDiv").hide();
				    jQuery("#outTimeDiv").hide();
		            jQuery("#verifyCodeNO").show(); 
		            jQuery("#codeNullError").show();	                     
		            return ;
		        }
		        //加密start
		        try{
		        	dwr.engine.setAsync(false);
			       	encrypt.ecrypt(account,{callback:function(data){
			           	jQuery("#maccount").val(data);
			           	account = data;
			        }});
			        dwr.engine.setAsync(true);
	        	}catch(e){
	        		
	        	}
		       	/* dwr.engine.setAsync(false);
		       	encrypt.ecrypt(account,{callback:function(data){
		           	jQuery("#maccount").val(data);
		           	account = data;
		        }});
		        dwr.engine.setAsync(true); */
		        //加密end
		        
		        var url="<%=basePath%>getPwd/checkDynamicCode";
				var params = {
					account : account,
					verifyCode:verifyCode				
				};
				jQuery.post(url, params, function(data) {
					if('2'==data){
					   ys=2;
					   var urlt="<%=basePath%>getPwd/toUpdatPwd";
					   var paramst = {};
					   jQuery.ajax({  
					          type : "post",  
					          url :urlt,  
					          data : paramst,  
					          async : false,  
					          success : function(data){				            
					         }  
					   }); 				   
					   jQuery("#st2").hide();
		               jQuery("#st3").show();			     
			    	   jQuery("#checkPhoneDiv").css("display", "none");
				       jQuery("#updatePwdDiv").css("display", "block");
				       jQuery("#codeError").css("display", "none");					     
				    }
				    if(data=='3'){
				        ys=1; 
				        jQuery("#toGetCodeDiv").hide();
				        jQuery("#codeNullError").hide();
				        jQuery("#verifyCodeOK").hide();
				        jQuery("#outTimeDiv").hide();			    
				        jQuery("#verifyCodeNO").show(); 
				        jQuery("#codeError").css("display", "block");			         	
						return;
				    }			   
				    if(data=='outTime'){
				        ys=1; 
				        jQuery("#codeNullError").hide();
				        jQuery("#verifyCodeOK").hide();
				        jQuery("#verifyCodeNO").show(); 
				        jQuery("#codeError").css("display", "none");
				        jQuery("#toGetCodeDiv").hide();
				        jQuery("#outTimeDiv").show();
				    	alert("验证码已过期，请重新获取验证码！");
				    	return;
				    } 
				    if(data=='toGetCode'){
				        ys=1; 
				        jQuery("#codeNullError").hide();
				        jQuery("#verifyCodeOK").hide();			        
				        jQuery("#codeError").css("display", "none");
				        jQuery("#outTimeDiv").hide();
				        jQuery("#toGetCodeDiv").show();
				        jQuery("#verifyCodeNO").show(); 
				    	//alert("请先获取验证码,再进行密码修改操作！");
				    	return;
				    }
				    if(data=='toBack'){
						window.location.href="<%=basePath%>getPwd/toGetPwd";
					}				   
				    if(data=='error'){
				        ys=1; 
				    	alert("网络异常，请稍后再试!");
				    	return;
				    }
			   });	       	
		}	
		

		/* 发送短信验证 */
		var sendSMSLastTime = null;
		function sendSMS(){
			
				//阻止快速连续按
				if(sendSMSLastTime!=null){
					if(new Date().getTime()-sendSMSLastTime.getTime()<60000){//小于60秒
						//console.log("小于3秒")
						return;
					}
				}
				sendSMSLastTime = new Date();//最新时间
				//console.log("通过");
				//阻止快速连续按
				
				var mob = jQuery("#phone").val();
				var account=jQuery("#account").val();
				if(mob=="" || mob==null){
					alert("您的账号中没有预留手机号，请联系管理员进行设置");				
					return;
				}
				if (mob.length !=11){
					alert("您的预留手机号码不是正确格式的手机号，请联系管理员进行设置");				
					return;
				}
				if(!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(mob))){
					alert("您的预留手机号码不是正确格式的手机号，请联系管理员进行设置");
					return false; 
				}
				//加密start
				 try{
					    dwr.engine.setAsync(false);
				       	encrypt.ecrypt(mob,{callback:function(data){
				           	jQuery("#mphone").val(data);
				           	mob = data;
				        }});
				       	encrypt.ecrypt(account,{callback:function(data){
				           	jQuery("#maccount").val(data);
				           	account = data;
				        }});
				        dwr.engine.setAsync(true);
	        	}catch(e){
	        		
	        	}
		       	/* dwr.engine.setAsync(false);
		       	encrypt.ecrypt(mob,{callback:function(data){
		           	jQuery("#mphone").val(data);
		           	mob = data;
		        }});
		       	encrypt.ecrypt(account,{callback:function(data){
		           	jQuery("#maccount").val(data);
		           	account = data;
		        }});
		        dwr.engine.setAsync(true); */
		        //加密end
		        
		        var validateWay = jQuery(":radio[name='validateWay']:checked").val();// 1:手机 , 2:email
		        if(validateWay=="2"){ 
		        	g_suffix="_Email";//邮箱方式后缀
		        }else{
		        	g_suffix="";//手机方式，木有后缀
		        }
		        	
				var params = {
					servMobile : mob,
					account:account,
					validateWay:validateWay
				};	
				jQuery("#infoDiv").show();		
				var url="<%=basePath%>getPwd/getDynamicCode";
				jQuery.post(url, params, function(data) {
				if (data != null && data != "") {
					if (data == "sms_error") {
						alert("发送失败，请稍候再试");
						return;
					} else if (data.indexOf("sms_error") == 0) {
						alert("发送失败，请稍候再试(" + data.substring(10) + ")");
						return;
					} else if (data == "errorPhone") {
						//alert("您输入的手机号与系统预留手机号不符！");							   
						jQuery("#sendCode , #sendCode_Email").hide();
						return;
					} else if (data == "numberOut") {
						jQuery("#outTimes").show();
						jQuery("#noTimes , #noTimes_Email").hide();
						jQuery("#times , #times_Email").hide();
						jQuery("#validCode").hide();
						jQuery("#validCode_gray").val("");
						jQuery("#validCode_gray").hide();
						jQuery("#noValidCode").show();
					} else if(data=='toBack'){
						 window.location.href="<%=basePath%>getPwd/toGetPwd";
					} else {
						//发送成功
						var code = data.substring(0, 6);
						var lastTimes = data.substring(6, 7);
						var time = data.substring(7);					
						jQuery("#code").val(code);
						if (lastTimes == "4") {
							setTime();
							jQuery("#sendCode"+g_suffix).show();
							jQuery("#differentPhone").hide();
							jQuery("#times , #times_Email").hide();
						}else if (lastTimes == '0') {
							//setTime();
							jQuery("#validCode").show();
							jQuery("#noTimes"+g_suffix).show();
							jQuery("#sendCode , #sendCode_Email").hide();
							jQuery("#differentPhone").hide();
							jQuery("#times , #times_Email").hide();
	
						}else {
							setTime();
							jQuery("#time").val(time);
							jQuery("#lastTimes,#lastTimes_Email").html(lastTimes);
							jQuery("#times , #times_Email").hide();
							jQuery("#times"+g_suffix).show();
							jQuery("#sendCode , #sendCode_Email").hide();
							jQuery("#differentPhone").hide();
						}
					}
				}
			});
	
		}
	
	
		function setTime() {
			///* 按钮倒数/////////
			var validCode_gray_text = "秒后可重新获取"; //文本
			var timeOutsecond = 60; //秒数  debug
			this.waitForMinute = function() {
				var cur_minute = parseInt(jQuery("#validCode_gray").val());
				cur_minute--;
				jQuery("#validCode_gray").val(cur_minute + validCode_gray_text);
				if (cur_minute > 0) {
					window.setTimeout(this.waitForMinute, 1000);
				} else {
					jQuery("#validCode_gray").hide();
					jQuery("#validCode").show();
					jQuery("#validCode_gray").val(timeOutsecond + validCode_gray_text);
				}
			}
			jQuery("#validCode").hide();
			jQuery("#validCode_gray").val(timeOutsecond + validCode_gray_text);
			jQuery("#validCode_gray").show();
			window.setTimeout(this.waitForMinute, 1000);
		}
	    
	    /* 第三步提交 */
		function threeCheck() {
			var newPwd1 = jQuery("#newPwd1")[0];
			var newPwd = jQuery("#newPwd")[0];
			if (newPwd.value == null || newPwd.value == "") {
				alert("请输入新密码！");
				newPwd.focus();
				return;
			}
			var r = /^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,24}$/;
			var password = newPwd.value;
			if (!r.test(password)) {
				jQuery("#newPwdError").show();
				jQuery("#newPwdOK").hide();
				jQuery("#newPwd1OK").hide();
				jQuery("#different").hide();
				newPwd.focus();
				return false;
			}
			var reg = /\s/;
			if (reg.test(password)) {
				alert("密码不能包含空格！");
				newPwd.focus();
				return false;
			}
			var re = /[^\x00-\xff]/g;
			if (re.test(password)) {
				alert("您好，密码不允许输入中文，中文符号或全角字符。");
				newPwd.focus();
				return false;
			}
			if (newPwd1.value == null || newPwd1.value == "") {
				alert("请输入确认密码！");
				newPwd1.focus();
				return false;
			}
			if (newPwd.value != newPwd1.value) {
				jQuery("#different").show();
				newPwd1.focus();
				return;
			}
			
			//加密start
			try{
				dwr.engine.setAsync(false);
		       	var newPwdVal = newPwd.value;
		       	var account = jQuery("#account").val();
		       	encrypt.ecrypt(newPwdVal,{callback:function(data){
		           	jQuery("#mnewPwd").val(data);
		           	newPwdVal = data;
		        }});
		       	encrypt.ecrypt(account,{callback:function(data){
		           	jQuery("#maccount").val(data);
		           	account = data;
		        }});
		        dwr.engine.setAsync(true);
	    	}catch(e){
	    		
	    	}
	       	/* dwr.engine.setAsync(false);
	       	var newPwdVal = newPwd.value;
	       	var account = jQuery("#account").val();
	       	encrypt.ecrypt(newPwdVal,{callback:function(data){
	           	jQuery("#mnewPwd").val(data);
	           	newPwdVal = data;
	        }});
	       	encrypt.ecrypt(account,{callback:function(data){
	           	jQuery("#maccount").val(data);
	           	account = data;
	        }});
	        dwr.engine.setAsync(true); */
	        //加密end
	        
	        var params = {
	        	"account" : account,
	        	"newPwd" : newPwdVal
	        };
			
			jQuery.post("<%=basePath%>getPwd/updateUserPwd", params,function(data) {
			
			    if('2'==data){
					window.location.href="<%=basePath%>getPwd/toGetUserPwdSuc";
				}
				if(data=='toBack'){
					window.location.href="<%=basePath%>getPwd/toGetPwd";
				}
				if (data == 'error') {
					alert("网络异常，请稍后再试");
					return;
				}
			});
	
		}
		
		//检查新密码
		function tocheckNewPwd(){
		    jQuery("#newPwdErrorTip").hide();
			var newPwd = jQuery("#newPwd").val();
			var newPwd1 = jQuery("#newPwd1").val();	
			var newPwdStatus= jQuery("#newPwdStatus").val();
			var flag = true;	   
			if(newPwd ==null || newPwd==""){
				//alert("请输入新密码！");			
				jQuery("#newPwdOK").hide();
				jQuery("#newPwd1OK").hide();			
				if(newPwd!=newPwdStatus){
				    jQuery("#newPwd1").val("");
				    jQuery("#different").hide();
				}			
				jQuery("#newPwd").focus();
				return false;
			}
		    var r=/^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,24}$/;
		    if(!r.test(newPwd)){	    	
		    	//alert("您好，您修改的密码强度太弱！\n用户密码需满足以下规则：至少包含字母（大小写其中一种皆可）和数字；特殊符号等可选；至少6位，最高24位。");
		    	jQuery("#newPwdError").show();	    	
		    	jQuery("#newPwdOK").hide();
		    	jQuery("#newPwd1OK").hide();
		    	if(newPwd!=newPwdStatus){
				    jQuery("#newPwd1").val("");
				    jQuery("#different").hide();
				}	
		    	jQuery("#newPwd").focus();
		    	return false;
		    }
		    var reg =/\s/;
		    if(reg.test(newPwd)){
				alert("密码不能包含空格");
				jQuery("#newPwdOK").hide();
				jQuery("#newPwd1OK").hide();			
		    	jQuery("#newPwd").focus();
		    	return false;
		    }
		    var re=/[^\x00-\xff]/g;
			if(re.test(newPwd)){
				alert("您好，密码不允许输入中文，中文符号或全角字符");
				jQuery("#newPwdOK").hide();
				jQuery("#newPwd1OK").hide();
		    	jQuery("#newPwd").focus();
		    	return false;
			}
			if(flag){
				jQuery("#newPwdOK").show();
				jQuery("#newPwdError").hide();
				jQuery("#different").hide();
			}
			
			if(newPwd1!=null && newPwd1!=""){
				if(newPwd != newPwd1){
					jQuery("#different").show();
					jQuery("#newPwd1OK").hide();
			    	jQuery("#newPwd1").focus();
				}else{
					jQuery("#newPwd1OK").show();
				}
			}
			jQuery("#newPwdStatus").val(newPwd);
			
		}
			
		//检查两次输入的密码
		function toConfirmPwd(){
			var newPwd = jQuery("#newPwd").val();
			var newPwd1 = jQuery("#newPwd1").val();
			if(newPwd==null || newPwd==""){
				jQuery("#newPwdOK").hide();
				jQuery("#newPwd1OK").hide();
				jQuery("#different").hide();
				jQuery("#newPwd").focus();
				return false;
			}
			if(newPwd1 ==null || newPwd1==""){
				alert("请输入确认密码！");
				jQuery("#newPwd1OK").hide();
				jQuery("#newPwd1").focus();
				return;
			}
		   
			if(newPwd != newPwd1){
				jQuery("#different").show();
				jQuery("#newPwd1OK").hide();
		    	jQuery("#newPwd1").focus();
				return;
			}
			if(newPwd == newPwd1){
				jQuery("#different").hide();
				jQuery("#newPwd1OK").show();
			}
		}
		
		 /* 展示用户账号输入提示信息 */
	    function showPwdTip(){
	         jQuery("#newPwdOK").hide();
			 jQuery("#newPwdError").hide();		
		     jQuery("#newPwdErrorTip").show();
	    }   
	    
		var sixFlag = 0;
	    function checkVerfiyCode(){
		        var verifyCode=jQuery("#verifyCode").val();
		        verifyCode=jQuery.trim(verifyCode);
	    		if(verifyCode.length == 6) {
	    			sixFlag = 1;
		            var account=jQuery("#account").val();
			        account=jQuery.trim(account);		       
			        if(account==""||account==null){	           
			           return ;
			        }
			        var code=jQuery("#code").val();	      
			        if(code==null||code==""){
			           jQuery("#toGetCodeDiv").show();
			           return ;
			        }
			        if(verifyCode==""||verifyCode==null){
			            ys=1;            
			            jQuery("#codeError").css("display", "none");
			            jQuery("#verifyCodeOK").hide();
			            jQuery("#toGetCodeDiv").hide();
					    jQuery("#outTimeDiv").hide();
			            jQuery("#verifyCodeNO").show(); 
			            jQuery("#codeNullError").show();	                     
			            return ;
			        }
			        //加密start
			        try{
			        	dwr.engine.setAsync(false);
				       	var account = jQuery("#account").val();
				       	encrypt.ecrypt(account,{callback:function(data){
				           	jQuery("#maccount").val(data);
				           	account = data;
				        }});
				        dwr.engine.setAsync(true);
			    	}catch(e){
			    		
			    	}
			       /* 	dwr.engine.setAsync(false);
			       	var account = jQuery("#account").val();
			       	encrypt.ecrypt(account,{callback:function(data){
			           	jQuery("#maccount").val(data);
			           	account = data;
			        }});
			        dwr.engine.setAsync(true); */
			        //加密end
			        var url="<%=basePath%>getPwd/checkDynamicCode";
					var params = {
						account : account,
						verifyCode:verifyCode				
					};
					jQuery.post(url, params, function(data) {
						if('2'==data){	
						    ys=2; 
						    jQuery("#toGetCodeDiv").hide();
					        jQuery("#outTimeDiv").hide();				    			   
						    jQuery("#verifyCodeNO").hide(); 
						    jQuery("#codeError").css("display", "none"); 
						    jQuery("#codeNullError").hide();
						    jQuery("#verifyCodeOK").show(); 					     
					    }
					    if(data=='3'){
					        ys=1; 
					        jQuery("#toGetCodeDiv").hide();
					        jQuery("#codeNullError").hide();
					        jQuery("#verifyCodeOK").hide();
					        jQuery("#outTimeDiv").hide();			    
					        jQuery("#verifyCodeNO").show(); 
					        jQuery("#codeError").css("display", "block");			         	
							return;
					    }			   
					    if(data=='outTime'){
					        ys=1; 
					        jQuery("#codeNullError").hide();
					        jQuery("#verifyCodeOK").hide();
					        jQuery("#verifyCodeNO").show(); 
					        jQuery("#codeError").css("display", "none");
					        jQuery("#toGetCodeDiv").hide();
					        jQuery("#outTimeDiv").show();
					    	//alert("验证码已过期，请重新获取验证码！");
					    	return;
					    } 
					    if(data=='toGetCode'){
					        ys=1; 
					        jQuery("#codeNullError").hide();
					        jQuery("#verifyCodeOK").hide();			        
					        jQuery("#codeError").css("display", "none");
					        jQuery("#outTimeDiv").hide();
					        jQuery("#toGetCodeDiv").show();
					        jQuery("#verifyCodeNO").show(); 
					    	//alert("请先获取验证码,再进行密码修改操作！");
					    	return;
					    }
					    if(data=='toBack'){
						   window.location.href="<%=basePath%>getPwd/toGetPwd";
						}	
					    if(data=='error'){
					        ys=1; 
					    	alert("网络异常，请稍后再试");
					    	return;
					    }
				   });
			}else{
				if(sixFlag == 1) {
			  		jQuery("#verifyCodeOK").hide();
			  		//jQuery("#verifyCodeNO").show(); 		
				}
		        if(verifyCode==null||verifyCode==""){
			           jQuery("#codeError").hide();
			           jQuery("#toGetCodeDiv").hide();
			           jQuery("#verifyCodeNO").hide();
			    }
			}
	    } 
		
		function toLogin(){
		   window.location.href="<%=basePath%>core/toLogin";
		}
	</script>
</head>
<body style="font-family:'微软雅黑'">
	<div class="yfgl_top" >
		<span class="yfgl_top_l" style="height:98px; display:block; background:url(/SRMC/rmpb/images/yfgl_bj_l.jpg) no-repeat left top;">
			<ul class="yfgl_top_r" style="width:100px">              
				<li><span class="yfgl_top_r_a"><img src="/SRMC/rmpb/images/yfgl_bnt01.gif" /></span><span class="yfgl_top_r_b"><a href="javascript:void(0);" id="updatepassword" onclick="toLogin()">登录 </a></span></li>
				
			</ul>
		</span>
	</div>	
	
	<form method="post" name="form" id="submitForm" action="#">
		<input type="hidden" name="codeSize" id="codeSize" value="${codeSize}" />
		<input type="hidden" name="newPwdStatus" id="newPwdStatus" value="" />
		<input type="hidden" name="status" id="status" value="${status}" />
		<input type="hidden" name="code" id="code" value="" />
		<div>
			<div class="ydgl_content">
				<!--进度条 start-->
				<div id="st1">
					<div class="ydgl_tit">
						<span class="ydgl_iconloca l"></span>
						<h2 class="l">找回密码 > 填写帐号信息</h2>
					</div>
					<div class="ydgl_process">
						<ul>
							<li class="active">第一步：填写帐号信息</li>
							<li>第二步：身份验证</li>
							<li>第三步：设置新密码</li>
							<li>第四步：完成</li>
						</ul>
					</div>
				</div>
				<div id="st2" style="display: none">
					<div class="ydgl_tit">
						<span class="ydgl_iconloca l"></span>
						<h2 class="l">找回密码 >身份验证</h2>
					</div>

					<div class="ydgl_process">
						<ul>
							<li>第一步：填写帐号信息</li>
							<li class="active">第二步：身份验证</li>
							<li>第三步：设置新密码</li>
							<li>第四步：完成</li>
						</ul>
					</div>
				</div>
				<div id="st3" style="display: none">
					<div class="ydgl_tit">
						<span class="ydgl_iconloca l"></span>
						<h2 class="l">找回密码 > 设置新密码</h2>
					</div>
					<div class="ydgl_process">
						<ul>
							<li>第一步：填写帐号信息</li>
							<li>第二步：身份验证</li>
							<li class="active">第三步：设置新密码</li>
							<li>第四步：完成</li>
						</ul>
					</div>
				</div>
				<div id="st4" style="display: none">
					<div class="ydgl_tit">
						<span class="ydgl_iconloca l"></span>
						<h2 class="l">找回密码 > 完成</h2>
					</div>
					<div class="ydgl_process">
						<ul>
							<li>第一步：填写帐号信息</li>
							<li>第二步：身份验证</li>
							<li>第三步：设置新密码</li>
							<li class="active">第四步：完成</li>
						</ul>
					</div>
				</div>
				<!--进度条 end-->
				<div class="margint16">
					<div class="ydgl_main">
						<div class="ydgl_cont">
							<div id="accountCheckDiv">
								<div class="ydgl_mtit">
									<h3>请输入您需要重置密码的个人账号信息</h3>
								</div>
								<div class="ydgl_infor">
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px;">
										<span class="ydgl_icon01" style="margin-top:1px;"></span>
										<h4>用户账号：</h4>
										<div class="ydgl_txtbox">
											<input type="text" class="ydgl_txt" onchange="toClearAccountInfoAdd();" onkeyup="toClearAccountInfoAdd();"  id="account"
												value="${account}" /><img src="/SRMC/rmpb/images/OK.png"
												id="accountOK" width="16" height="15" style="display:none;" />
											<input type="hidden" name="account" id="maccount"/>
										</div>
										<div class="clear"></div>
									</div>
									<div id="accountNotExitError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">您输入的用户账号不存在，请核对后重新输入</p>
										<div class="clear"></div>
									</div>
									<div id="accountError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">用户账号在3-20个字符之间</p>
										<div class="clear"></div>
									</div>
									<div id="accountNullError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">用户账号不能为空</p>
										<div class="clear"></div>
									</div>
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px;">
										<span class="ydgl_icon02" style="margin-top:1px;"></span>
										<h4>用户姓名：</h4>
										<div class="ydgl_txtbox">
											<input type="text" class="ydgl_txt" id="userName" onchange="toClearUserNameInfoAdd();" onkeyup="toClearUserNameInfoAdd();"
												value="${userName}" /><img id="userNameOK"
												src="/SRMC/rmpb/images/OK.png" width="16" height="15"
												style="display:none;" />
											<input type="hidden" name="userName" id="muserName" />
										</div>
										<div class="clear"></div>
									</div>
									<div id="userNameError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">用户姓名必须小于20个字符</p>
										<div class="clear"></div>
									</div>
									<div id="userNameNullError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">用户姓名不能为空</p>
										<div class="clear"></div>
									</div>
									<div id="userNamePassError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">您输入的用户姓名有误，请核对后重新输入</p>
										<div class="clear"></div>
									</div>
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px;">
										<span class="ydgl_icon03" style="margin-top:1px;"></span>
										<h4>手机号码：</h4>
										<div class="ydgl_txtbox">
											<input type="text" class="ydgl_txt" id="mobile"
												value="${mobile}" onchange="toClearMobileInfoAdd();" onkeyup="toClearMobileInfoAdd();" /><img
												id="mobileOK" src="/SRMC/rmpb/images/OK.png" width="16"
												height="15" style="display:none;" />
											<input type="hidden"name="mobile" id="mmobile" />
										</div>
										<div class="clear"></div>
									</div>
									<div id="mobileError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">您输入的手机号码格式不对，请重新输入</p>
										<div class="clear"></div>
									</div>
									<div id="mobileNullError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">手机号码不能为空</p>
										<div class="clear"></div>
									</div>
									<div id="mobilePassError" class="ydgl_inforline"
										style="height:24px; line-height:24px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">您输入的手机号码有误，请核对后重新输入</p>
										<div class="clear"></div>
									</div>
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px;">
										<span class="ydgl_icon04" style="margin-top:1px;"></span>
										<h4>
											<em style="letter-spacing :5px;">验证码</em>：
										</h4>
										<div class="ydgl_txtbox">
											<input type="text" class="ydgl_txt" name="randCode"
												id="randCode" onkeyup="toClearRandCodeInfo();"
												 /><img id="randCodeOK"
												src="/SRMC/rmpb/images/OK.png" width="16" height="15"
												style="display:none;" />
										</div>
										<div class="clear"></div>
									</div>
									<div id="randCodeNullError" class="ydgl_inforline"
										style="height:26px; line-height:26px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">验证码不能为空</p>
										<div class="clear"></div>
									</div>
									<div id="randCodePassError" class="ydgl_inforline"
										style="height:26px; line-height:26px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">验证码错误</p>
										<div class="clear"></div>
									</div>
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px; padding:16px 0 0 140px;">
										<img src="randCodeImage" id="randCodeImage" width="104"
											onclick="changeRandCodeImage();" height="36"
											align="absmiddle" />&nbsp;看不清楚？ <a href="javascript:;"
											style="color:red" onclick="changeRandCodeImage();">换一张</a>
										<div class="clear"></div>
									</div>
									<div id="notPassInfo" class="ydgl_inforline"
										style="height:26px; line-height:26px; padding-top:16px;display:none;">
										<p class="ydgl_red" style="padding-left:140px;">您输入的信息有误，请核对后重新输入</p>
										<div class="clear"></div>
									</div>			

								</div>
								<div class="ydgl_btnline" style="padding:27px 0 0 240px;">
									<input type="button" class="ydgl_btnnext01" style="outline:0;" onclick="oneCheck();"/>
								</div>
							</div>
							
							
<%--							核对您的本人信息--%>
							<div id="checkPhoneDiv" style="display:none;"> <!-- debug -->
								<div class="ydgl_cont">

									<div class="ydgl_mtit">
										<h3>请认真核对您的本人信息，并输入手机或邮箱验证码以验证身份</h3>
									</div>
									<div class="ydgl_infor">

										<div class="ydgl_inforline"
											style="height:28px; line-height:28px;font-size:16px;">
											<span class="ydgl_icon01" style="margin:2px 10px 0 0;"></span>
											<em style="letter-spacing : 4px;">用户账号</em>：&nbsp;&nbsp;${account}
										</div>

										<div class="ydgl_inforline"
											style="height:28px; line-height:28px;font-size:16px;">
											<span class="ydgl_icon02" style="margin:2px 10px 0 0;"></span>
											<em style="letter-spacing : 4px;">用户姓名</em>：&nbsp;&nbsp;${userName}
										</div>

										<div class="ydgl_inforline"
											style="height:28px; line-height:28px;font-size:16px;">
											<span class="ydgl_icon055" style="margin:2px 10px 0 0;"></span>
											<em style="letter-spacing : 4px;">用户组织</em>：&nbsp;&nbsp;${orgName}
										</div>

										<div class="ydgl_inforline"
											style="height:28px; line-height:28px;font-size:16px;">
											<span class="ydgl_icon03" style="margin:2px 10px 0 0;"></span>
											<em style="letter-spacing : 4px;">验证方式</em>：&nbsp;
											<%--<c:choose>
												<c:when test="${codeSize==6 }">
													<img src="/SRMC/rmpb/images/btn_yanzhengma_gray.gif"
														allcheck="" disabled="disabled" />
												</c:when>
												<c:otherwise>
													<img src="/SRMC/rmpb/images/btn_yanzhengma.gif" allcheck=""
														id="validCode" onclick="sendSMS()" align="absmiddle" />
												</c:otherwise>
											</c:choose>
											<input id="validCode_gray" type="button" value=""
												disabled="disabled" style="display:none;" /> <img
												src="/SRMC/rmpb/images/btn_yanzhengma_gray.gif" allcheck=""
												id="noValidCode" style="display:none;" disabled="disabled" />--%>
												
												<label><input type="radio" name="validateWay" value="1" checked  />手机（${userPhone}）</label>
												<label><input type="radio" name="validateWay" value="2"  />邮箱（${userEmail}）</label>

										</div>
										
										<div class="ydgl_inforline" id="sendCode"
											style="height:20px; line-height:20px; padding-top:0px;display:none;">
											<p class="ydgl_gray"
												style="padding:3px 0 0 139px;height:26px;">验证码已下发至您的手机，请在3分钟内输入正确的验证码</p>
											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="sendCode_Email"
											style="height:20px; line-height:20px; padding-top:0px;display:none;">
											<p class="ydgl_gray"
												style="padding:3px 0 0 139px;height:26px;">验证码已下发至您的邮箱，请在10分钟内输入正确的验证码</p>
											<div class="clear"></div>
										</div>
										
										<div class="ydgl_inforline" id="times"
											style=" line-height:20px; padding-top:0px;display:none;">
											<p class="ydgl_gray"
												style="padding:3px 0 0 139px;height:26px;">
												验证码已重新下发至您的手机，请在3分钟内输入正确的验证码。请注意，本日您还可获取 <b id="lastTimes">3</b>
												次<!--短信-->验证码
												</p>
											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="times_Email"
											style=" line-height:20px; padding-top:0px;display:none;">
											<p class="ydgl_gray"
												style="padding:3px 0 0 139px;height:26px;">
												验证码已重新下发至您的邮箱，请在10分钟内输入正确的验证码。请注意，本日您还可获取 <b id="lastTimes_Email">3</b>
												次验证码
												</p>
											<div class="clear"></div>
										</div>
										

										<div class="ydgl_inforline" id="noTimes"
											style="  line-height:20px; padding-top:0px;display:none;">
											<p class="ydgl_gray"
												style="padding:3px 0 20px 139px;height:26px;">验证码已重新下发至您的手机，请在3分钟内输入正确的验证码。请注意，您今日获取验证码<!--短信-->次数已达到5次最大限制，为了保障您的信息安全，系统将无法再次进行下发验证码<!--短信-->操作
												</p>
											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="noTimes_Email"
											style="  line-height:20px; padding-top:0px;display:none;">
											<p class="ydgl_gray"
												style="padding:3px 0 20px 139px;height:26px;">验证码已重新下发至您的邮箱，请在10分钟内输入正确的验证码。请注意，您今日获取验证码次数已达到5次最大限制，为了保障您的信息安全，系统将无法再次进行下发验证码操作
												</p>
											<div class="clear"></div>
										</div>
										
										<c:choose>
											<c:when test="${codeSize==6 }">
												<div class="ydgl_inforline"
													style="height:20px; line-height:20px; padding-top:0px;">
													<p class="ydgl_gray"
														style="padding:3px 0 0 139px;height:26px;">您今日获取验证码<!--短信-->次数已超过5次最大限制，为了保障您的信息安全，请您明日再进行相关操作，谢谢</p>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</c:when>
											<c:otherwise>
												<div class="ydgl_inforline" id="outTimes"
													style="height:20px; line-height:20px; padding-top:0px;display:none;">
													<p class="ydgl_gray"
														style="padding:3px 0 0 139px;height:26px;">您今日获取验证码<!--短信-->次数已超过5次最大限制，为了保障您的信息安全，请您明日再进行相关操作，谢谢</p>
													<div class="clear"></div>
												</div>
											</c:otherwise>
										</c:choose>
										<div class="clear"></div>

										<div class="ydgl_inforline"
											style="height:28px; line-height:28px; padding-top:20px;">
											<span class="ydgl_icon04" style="margin-top:2px;"></span>
											<h4 style="letter-spacing : 10px; padding-left:11px;">验证码：</h4>
											<div class="ydgl_txtbox" >
												<input type="text" id="verifyCode" name="verifyCode"
													onclick="toClearRandCodeInfo();" class="ydgl_txt"
													onkeyup="checkVerfiyCode();" maxlength="6"   />
													<img id="verifyCodeOK" src="/SRMC/rmpb/images/OK.png" width="16" height="15" style="display:none;" />
													<img id="verifyCodeNO" src="/SRMC/rmpb/images/NO.png" width="16" height="15" style="display:none;" />
											</div>
											
<%--											按钮--%>
<%--											按钮--%>
											<c:choose>
												<c:when test="${codeSize==6 }">
													<img src="/SRMC/rmpb/images/btn_yanzhengma_gray.gif" allcheck="" disabled="disabled" 
														style="position:relative;left: 33px;" />
												</c:when>
												<c:otherwise>
													<img src="/SRMC/rmpb/images/btn_yanzhengma.gif" allcheck="" id="validCode" onclick="sendSMS()" align="absmiddle" 
														 style="position:relative;left: 33px;" />
												</c:otherwise>
											</c:choose>
											<input id="validCode_gray" type="button" value="" disabled="disabled" style="display:none; position:relative;left: 33px;" />
											<img src="/SRMC/rmpb/images/btn_yanzhengma_gray.gif" allcheck="" id="noValidCode" 
												style="display:none;position:relative;left: 33px;" disabled="disabled" />
												
<%--											按钮--%>


											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="codeError"
											style="height:26px; line-height:26px; padding:3px 0 0 139px;display:none;">
											<p class="ydgl_red" style="">验证码输入错误，请重新输入</p>
											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="codeNullError"
											style="height:26px; line-height:26px; padding:3px 0 0 139px;display:none;">
											<p class="ydgl_red" style="">验证码不能为空</p>
											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="outTimeDiv"
											style="height:26px; line-height:26px; padding:3px 0 0 139px;display:none;">
											<p class="ydgl_red" style="">验证码已过期，请重新获取验证码</p>
											<div class="clear"></div>
										</div>
										<div class="ydgl_inforline" id="toGetCodeDiv"
											style="height:26px; line-height:26px;padding:3px 0 0 139px;display:none;">
											<p class="ydgl_red" style="">请先获取验证码,再进行下一步操作</p>
											<div class="clear"></div>
										</div>										
									</div>
									<div class="ydgl_btnline" style="padding:30px 0 0 240px;">
										<input type="button" class="ydgl_btnnext01" style="outline:0;"
											onclick="twoCheck();" value="" />

									</div>
								</div>
							</div>
<%--							核对您的本人信息 end --%>
							
							
							<div id="updatePwdDiv" style="display:none;">
								<div class="ydgl_mtit">
									<h3>请设置您的新密码</h3>
								</div>
								<div class="ydgl_infor">
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px;">
										<span class="ydgl_icon05" style="margin-top:2px;"></span>
										<h4>设置新密码：</h4>
										<div class="ydgl_txtbox">
											<input type="password" class="ydgl_txt" value="" maxlength="24" onclick="showPwdTip();"
												id="newPwd" onchange="tocheckNewPwd();" autocomplete="off"
												onblur="tocheckNewPwd();" /><img
												src="/SRMC/rmpb/images/OK.png" width="16" height="15"
												id="newPwdOK" style="display:none;" />
											<input type="hidden" name="newPwd" id="mnewPwd" />
										</div>
										<div class="clear"></div>
									</div>
									<div id="newPwdError" class="ydgl_inforline"
										style="height:26px; line-height:26px; padding-top:0px;display:none;">
										<p class="ydgl_red" style="padding-left:139px;">密码必须满足至少包括字母和数字的6-24位字符</p>
										<div class="clear"></div>
									</div>
									<div id="newPwdErrorTip" class="ydgl_inforline"
										style="height:26px; line-height:26px; padding-top:0px;display:none;">
										<p class="ydgl_red"
											style="padding-left:139px;color:grey;font-size:14px">密码强度提示：至少包含字母和数字，特殊符号可选,最少6位,最多24位</p>
										<div class="clear"></div>
									</div>
									<div class="ydgl_inforline"
										style="height:28px; line-height:28px;">
										<span class="ydgl_icon06" style="margin-top:2px;"></span>
										<h4>确认新密码：</h4>
										<div class="ydgl_txtbox">
											<input type="password" class="ydgl_txt" autocomplete="off" maxlength="24" id="newPwd1" onchange="toConfirmPwd();"
												value="" /><img src="/SRMC/rmpb/images/OK.png"
												id="newPwd1OK" width="16" height="15" style="display:none;" />
											<input type="hidden" name="newPwd1" id="mnewPwd1" />
										</div>
										<div class="clear"></div>
									</div>
									<div id="different" class="ydgl_inforline"
										style="height:24px; line-height:20px; padding-top:0px;display:none;">

										<p class="ydgl_red" style="padding-left:139px;">您两次密码不一致，请重新输入</p>
										<div class="clear"></div>
									</div>
								</div>
								<div class="ydgl_btnline" style="padding:30px 0 0 240px;">
									<input type="button" class="ydgl_btnsubmit" value=""
										style="outline:0;" onclick="threeCheck();" />

								</div>
							</div>
						</div>
					</div>
					<div class="ydgl_sider">
						<div class="ydgl_sidertop"></div>
						<div class="ydgl_sidermain">
							<div class="ydgl_sidertit">
								<div class="ydgl_star"></div>
								<p>温馨提示:</p>
							</div>
							<div class="ydgl_sidercont">
								<ul>
									<li>1.为充分保障您的账户信息正确，请认真核对您的个人信息，确定无误；</li>
									<li>2.您的所有操作信息将会被记录和审计，如非本人操作，请立即退出系统，非法操作将会追究法律责任；</li>
									<li>3.如在使用中出现任何问题，请联系系统管理员，我们会及时提供技术支持服务。</li>
								</ul>
							</div>
						</div>
						<div class="ydgl_siderbott"></div>
					</div>
				</div>
			</div>
		</div>

	</form>
</body>
</html>
