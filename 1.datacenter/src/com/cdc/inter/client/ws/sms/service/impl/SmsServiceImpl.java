package com.cdc.inter.client.ws.sms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;
import org.trustel.util.DateUtils;

import com.cdc.common.properties.DCConfig;
import com.cdc.inter.client.ws.sms.model.InterfaceSmscodeRecords;
import com.cdc.inter.client.ws.sms.model.InterfaceSmsinfoRecords;
import com.cdc.inter.client.ws.sms.service.ISmsService;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.ReceiverCollection;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.ReceiverItem;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvInputCollection;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvInputItem;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvOutputCollection;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvOutputItem;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvRequest;
import com.cmcc.csb.sb_iap_sendsmsinfosrv.SBIAPSendSMSInfoSrvResponse;
import com.cmcc.webservice.sendsmsinfosrv.SendSmsinfoClient;

public class SmsServiceImpl implements ISmsService {

	private IEnterpriseService enterpriseService;
	private SendSmsinfoClient sendSmsinfoClient;  
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	
	public void setSendSmsinfoClient(SendSmsinfoClient sendSmsinfoClient) {
		this.sendSmsinfoClient = sendSmsinfoClient;
	}


	public boolean sendMessage(List<String> moiles,String msg,Date sendTime){
		String suffix = DCConfig.getProperty("SMS_SUFFIX");
		if(StringUtils.isEmpty(suffix)){
			suffix = "【南方基地管理提升平台】";
		}
		boolean flag = false;
		logger.info("发送短信服务接口开始……begin");
		
		if(moiles == null){
			logger.info("手机号码不能为空……");
			return flag;
		}
		
		if(!StringUtils.isNotEmpty(msg)){
			logger.info("短信内容不能为空……");
			return flag;
		}
		List<InterfaceSmsinfoRecords> smsList = new ArrayList<InterfaceSmsinfoRecords>();
		
		SBIAPSendSMSInfoSrvInputItem sBIAPSendSMSInfoSrvInputItem = new SBIAPSendSMSInfoSrvInputItem();//消息主体
		ReceiverCollection receiverCollection = new ReceiverCollection();//号码封装类
		//短信批量列表
		List<ReceiverItem> clist = new ArrayList<ReceiverItem>();
		for (int i = 0; i < moiles.size(); i++) {
			String mobile = moiles.get(i);
			if(StringUtils.isNotEmpty(mobile)){
				ReceiverItem receiverItem = new ReceiverItem();
				receiverItem.setMOBILE(mobile);
				clist.add(receiverItem);
			}
			InterfaceSmsinfoRecords interfaceSmsinfoRecords = new InterfaceSmsinfoRecords();
			interfaceSmsinfoRecords.setCreateTime(new Date());
			interfaceSmsinfoRecords.setContent(msg + suffix);
			interfaceSmsinfoRecords.setMobile(mobile);
			interfaceSmsinfoRecords.setSendTime(sendTime);
			smsList.add(interfaceSmsinfoRecords);
		}
		receiverCollection.getReceiverItem().addAll(clist);
		sBIAPSendSMSInfoSrvInputItem.setRECEIVER(receiverCollection);//待发号码列表
		sBIAPSendSMSInfoSrvInputItem.setCONTENT(msg + suffix);//短信内容
		//如果为空值，信息将即时发送；否则将按指定的发送时间发送。
		if(sendTime != null){
			sBIAPSendSMSInfoSrvInputItem.setSENDTIME(DateUtils.dateToXmlDate(sendTime));//短信发送时间
		}
		
		//请求
		SBIAPSendSMSInfoSrvInputCollection sBIAPSendSMSInfoSrvInputCollection = new SBIAPSendSMSInfoSrvInputCollection();
		sBIAPSendSMSInfoSrvInputCollection.getSBIAPSendSMSInfoSrvInputItem().add(sBIAPSendSMSInfoSrvInputItem);
		SBIAPSendSMSInfoSrvRequest sBIAPSendSMSInfoSrvRequest = new SBIAPSendSMSInfoSrvRequest();
		sBIAPSendSMSInfoSrvRequest.setSBIAPSendSMSInfoSrvInputCollection(sBIAPSendSMSInfoSrvInputCollection);
		SBIAPSendSMSInfoSrvResponse response = sendSmsinfoClient.sendSMSInfo(sBIAPSendSMSInfoSrvRequest);
		
		if(response != null){
			SBIAPSendSMSInfoSrvOutputCollection sBIAPSendSMSInfoSrvOutputCollection = response.getSBIAPSendSMSInfoSrvOutputCollection();
			List<SBIAPSendSMSInfoSrvOutputItem> outputList = sBIAPSendSMSInfoSrvOutputCollection.getSBIAPSendSMSInfoSrvOutputItem();
			//返回结果列表数据
			if(outputList != null && outputList.size() > 0){
				for (int i = 0; i < outputList.size(); i++) {
					SBIAPSendSMSInfoSrvOutputItem sBIAPSendSMSInfoSrvOutputItem = outputList.get(i);
					InterfaceSmsinfoRecords interfaceSmsinfoRecords = smsList.get(i);
					interfaceSmsinfoRecords.setMsgHeader(sBIAPSendSMSInfoSrvRequest.getMsgHeader().toString());//请求体
					interfaceSmsinfoRecords.setServiceFlag(response.getSERVICEFLAG());//服务标识
					interfaceSmsinfoRecords.setServiceMessage(response.getSERVICEMESSAGE());//服务消息
					interfaceSmsinfoRecords.setInstanceId(response.getINSTANCEID());//实例ID
					interfaceSmsinfoRecords.setTotalRecord(response.getTOTALRECORD());//记录总数
					interfaceSmsinfoRecords.setTotalPage(response.getTOTALPAGE());//总页数
					interfaceSmsinfoRecords.setPageSize(response.getPAGESIZE());//页大小
					interfaceSmsinfoRecords.setCurrentPage(response.getCURRENTPAGE());//当前页码
					
					interfaceSmsinfoRecords.setReturnCode(sBIAPSendSMSInfoSrvOutputItem.getRETURNCODE());//返回代码
					interfaceSmsinfoRecords.setReturnMessage(sBIAPSendSMSInfoSrvOutputItem.getRETURNMESSAGE());//代码描述
					interfaceSmsinfoRecords.setSendId(sBIAPSendSMSInfoSrvOutputItem.getSENDID());//发送短信标识
				}
			}
			//存入数据
			enterpriseService.save(smsList);
			
			logger.info("调用接口记录保存成功");
			
			if("Y".equals(response.getSERVICEFLAG())){
				logger.info("调用发送短信服务接口成功");
				flag = true;
			}else{
				logger.info("调用发送短信服务接口失败："+response.getSERVICEMESSAGE());
			}
		}
		
		logger.info("发送短信服务接口结束……end");
		
		return flag;
	}

	@Override
	public boolean sendMessage(String moile, String msg) {
		List<String> list = new ArrayList<String>(1);
		list.add(moile);
		//实时发送短信
		return sendMessage(list, msg, null);
	}


	@Override
	public boolean sendSms(String mobile) {
		boolean result = false;
		if(StringUtils.isEmpty(mobile)){
			logger.info("手机号码不能为空！");
			return result;
		}
		boolean b = true;
		InterfaceSmscodeRecords isr  = (InterfaceSmscodeRecords) enterpriseService.getById(InterfaceSmscodeRecords.class, mobile);
		if(isr != null){
			long pasttime = isr.getSendTime();
			long nowtime = new Date().getTime();
			//九十秒内不能发送验证码
			if(nowtime - pasttime < 90 * 1000){
				b = false;
			}
		}
		b = true;
		//如果超过90秒即可发短信
		if(!b){
			logger.info("90秒内不能重复发送请求");
			return result;
		}
		String code = generateRamdCode();
		System.out.println("验证码："+code);
		boolean res=true;
		//boolean res = sendMessage(mobile, "您的验证码为：" + code + "。");
		if(!res){
			logger.info("接口异常");
			return result;
		}
		Date date = new Date();
		if(isr != null){
			//更新
			isr.setSmscode(code);
			isr.setSendTime(date.getTime());
			isr.setCreateTime(date);
			enterpriseService.updateObject(isr);
		}else{
			//新增
			isr = new InterfaceSmscodeRecords();
			isr.setMobile(mobile);
			isr.setSmscode(code);
			isr.setSendTime(date.getTime());
			isr.setCreateTime(date);
			enterpriseService.save(isr);
		}
		//下发验证码成功
		result = true;
		logger.info("下发验证码成功");
		return result;
	}

	@Override
	public boolean checkSms(String mobile, String smsCode) {
		boolean result = false;
		if(StringUtils.isEmpty(mobile)){
			logger.info("手机号码不能为空！");
			return result;
		}
		if(StringUtils.isEmpty(smsCode)){
			logger.info("验证码不能为空！");
			return result;
		}
		InterfaceSmscodeRecords isr  = (InterfaceSmscodeRecords) enterpriseService.queryEntity(InterfaceSmscodeRecords.class, mobile);
		if(isr == null){
			logger.info("验证码不存在！");
			return result;
		}
		long pasttime = isr.getSendTime();
		long nowtime = new Date().getTime();
		//5分钟
		if(nowtime - pasttime > 5 * 60 * 1000){
			logger.info("验证码失效！");
			return result;
		}
		if(!smsCode.equals(isr.getSmscode())){
			logger.info("验证码错误！");
			return result;
		}
		//验证码校验通过
		result = true;
		//校验通过，删除验证信息
		QueryBuilder query = new QueryBuilder(InterfaceSmscodeRecords.class);
		query.where("mobile",mobile,QueryAction.EQUAL);
		enterpriseService.delete(query);
		logger.info("验证码校验通过");
		return result;
	}
	/**
	 * 生成随机码
	 * @return
	 */
	private static String generateRamdCode(){
		int k = 6;
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for(int i = 0;i < k;i++){
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

}
