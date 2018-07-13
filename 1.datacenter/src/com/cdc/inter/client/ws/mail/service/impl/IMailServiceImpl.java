package com.cdc.inter.client.ws.mail.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;

import com.cdc.common.properties.DCConfig;
import com.cdc.inter.client.ws.mail.service.IMailService;
import com.cdc.inter.client.ws.mail.service.model.InterfaceMailinfoRecords;
import com.cmcc.csb.sb_iap_sendmailinfosrv.CcToAddressCollection;
import com.cmcc.csb.sb_iap_sendmailinfosrv.CcToAddressItem;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvInputCollection;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvInputItem;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvOutputCollection;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvOutputItem;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvRequest;
import com.cmcc.csb.sb_iap_sendmailinfosrv.SBIAPSendMailInfoSrvResponse;
import com.cmcc.csb.sb_iap_sendmailinfosrv.ToAddressCollection;
import com.cmcc.csb.sb_iap_sendmailinfosrv.ToAddressItem;
import com.cmcc.webservice.sendmailinfosrv.SendMailInfoClient;

public class IMailServiceImpl implements IMailService {
	private IEnterpriseService enterpriseService;
	private SendMailInfoClient sendMailInfoClient; 
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}


	public void setSendMailInfoClient(SendMailInfoClient sendMailInfoClient) {
		this.sendMailInfoClient = sendMailInfoClient;
	}



	@Override
	public boolean SendMail(List<String> to_addess, List<String> cc_address,
			String subject, String content,String account) {
		String suffix = DCConfig.getProperty("SMS_SUFFIX");
		if(StringUtils.isEmpty(suffix)){
			suffix = "【南方基地管理提升平台】";
		}
		// TODO Auto-generated method stub
		boolean flag = false;
		logger.info("发送邮件服务接口开始……begin");
		
		if( null == to_addess || to_addess.size()==0 ){
			logger.info("邮件发送地址不能为空……");
			return flag;
		}
		
		if(!StringUtils.isNotEmpty(subject)){
			logger.info("邮件主题不能为空……");
			return flag;
		}
		if(!StringUtils.isNotEmpty(content)){
			logger.info("邮件内容不能为空……");
			return flag;
		}
		System.out.println("发送的邮件地址是："+to_addess.get(0));
		//接口调用记录
		List<InterfaceMailinfoRecords> mailRecList = new ArrayList<InterfaceMailinfoRecords>();
		//CcToAddressItem 发送邮件地址列表
		List<ToAddressItem> tlist = new ArrayList<ToAddressItem>();
		for (int i = 0; i < to_addess.size(); i++){
			ToAddressItem  tItem = new ToAddressItem();
			tItem.setEMAIL(to_addess.get(i));
			tlist.add(tItem);
			InterfaceMailinfoRecords interfaceMailinfoRecords = new InterfaceMailinfoRecords();
			interfaceMailinfoRecords.setCreateTime(new Date());
			interfaceMailinfoRecords.setCreateUserid(account);
			interfaceMailinfoRecords.setSemail(to_addess.get(i));
			interfaceMailinfoRecords.setContent(content+suffix);
			mailRecList.add(interfaceMailinfoRecords);
		}
		//抄送邮件地址列表
		List<CcToAddressItem> clist = new ArrayList<CcToAddressItem>();
		//CcToAddressItem cItem = new CcToAddressItem();
		//cItem.setEMAIL("611482@qq.com");
		//clist.add(cItem);
		if(null != cc_address && cc_address.size()>0){
			for (int i = 0; i < cc_address.size(); i++) {
				CcToAddressItem cItem = new CcToAddressItem();
				cItem.setEMAIL(cc_address.get(i));
				clist.add(cItem);
			}
		}
		//List<AttachmentItem> alist = new ArrayList<AttachmentItem>();
		//AttachmentItem aItem = new AttachmentItem();
		//aItem.setFILENAME("aaa");
		//aItem.setFILEDATA("haha".getBytes());
		//alist.add(aItem);
		
		ToAddressCollection   toAddressCollection = new ToAddressCollection();//发送邮件地址封装类
		toAddressCollection.getToAddressItem().addAll(tlist);
		CcToAddressCollection ccToAddressCollection = new CcToAddressCollection();//抄送邮件封装类
		ccToAddressCollection.getCcToAddressItem().addAll(clist);
		//AttachmentCollection attachmentCollection = new AttachmentCollection();
		//attachmentCollection.getAttachmentItem().addAll(alist);
		//封装请求体
		SBIAPSendMailInfoSrvInputItem   sItem = new SBIAPSendMailInfoSrvInputItem();//邮件封装类
		sItem.setFROMADDRESS("");
		sItem.setTOADDRESS(toAddressCollection);
		sItem.setSUBJECT(subject);
		sItem.setCCTOADDRESS(ccToAddressCollection);
		sItem.setCONTENT(content+suffix);
		sItem.setTYPE("1");//邮件类型1：普通文本 2：html
		//sItem.setATTACHMENT(attachmentCollection);
		
		SBIAPSendMailInfoSrvInputCollection sbc = new SBIAPSendMailInfoSrvInputCollection();//消息主题
		sbc.getSBIAPSendMailInfoSrvInputItem().add(sItem);
		SBIAPSendMailInfoSrvRequest sbr = new SBIAPSendMailInfoSrvRequest();
		sbr.setSBIAPSendMailInfoSrvInputCollection(sbc);
		//SendMailInfoClient sendMailInfoClient1 = new SendMailInfoClient();
		SBIAPSendMailInfoSrvResponse response = sendMailInfoClient.sendMailInfo(sbr);
		
	
		//请求返回值
		logger.info("调用邮件接口开始保存记录啦啦啦");
		if(null != response){
			SBIAPSendMailInfoSrvOutputCollection sbiapSendMailInfoSrvOutputCollection = response.getSBIAPSendMailInfoSrvOutputCollection();
			List<SBIAPSendMailInfoSrvOutputItem> outputList = sbiapSendMailInfoSrvOutputCollection.getSBIAPSendMailInfoSrvOutputItem();
			if(outputList != null && outputList.size() > 0){
				for (int i = 0; i < outputList.size(); i++) {
					SBIAPSendMailInfoSrvOutputItem sBIAPSendMailInfoSrvOutputItem = outputList.get(i);
					InterfaceMailinfoRecords interfaceMailinfoRecords=  mailRecList.get(i);
					interfaceMailinfoRecords.setMsgHeader(sbr.getMsgHeader().toString());
					interfaceMailinfoRecords.setServiceFlag(response.getSERVICEFLAG());//服务标识
					interfaceMailinfoRecords.setServiceMessage(response.getSERVICEMESSAGE());//服务消息
					interfaceMailinfoRecords.setInstanceId(response.getINSTANCEID());//实例ID
					interfaceMailinfoRecords.setTotalRecord(response.getTOTALRECORD());//记录总数
					interfaceMailinfoRecords.setTotalPage(response.getTOTALPAGE());//总页数
					interfaceMailinfoRecords.setPageSize(response.getPAGESIZE());//页大小
					interfaceMailinfoRecords.setCurrentPage(response.getCURRENTPAGE());//当前页码
					
					interfaceMailinfoRecords.setReturnCode(sBIAPSendMailInfoSrvOutputItem.getRETURNCODE());//返回代码
					interfaceMailinfoRecords.setReturnMessage(sBIAPSendMailInfoSrvOutputItem.getRETURNMESSAGE());//代码描述
					interfaceMailinfoRecords.setSendId(sBIAPSendMailInfoSrvOutputItem.getSENDID());//发送短信标识
					logger.info("邮件服务端返回描述:"+sBIAPSendMailInfoSrvOutputItem.getRETURNMESSAGE());
				  }
				}
		}
		
		//存入数据
		enterpriseService.save(mailRecList);
		
		logger.info("调用邮件接口记录保存成功");
		if(null != response){
			if ("Y".equals(response.getSERVICEFLAG())) {
				logger.info("调用邮件发送服务接口成功");
				flag = true;
			} else {
				logger.info("调用邮件发送服务接口失败：" + response.getSERVICEMESSAGE());
			}
		}
		
		return flag;
	}
	
	public static void main(String[] args) {
		//List<String> a  = new ArrayList<String>();
		//a.add("6@163.com");
		//IMailServiceImpl aa = new IMailServiceImpl();		
		//aa.SendMail(a, null,"haha", "assdsd555","admin") ;
		
	}
	

}
