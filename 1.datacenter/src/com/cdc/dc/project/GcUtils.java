package com.cdc.dc.project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.common.properties.DCConfig;
import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.service.IGcAttachService;
import com.cdc.dc.rules.common.RulesCommon;
import com.cdc.inter.client.ws.sms.service.ISmsService;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.system.core.cache.SysParamHelper;
import com.cdc.system.core.util.SpringHelper;
import com.trustel.common.ItemPage;

/**
 * 工程模块utils
 * @author WEIFEI
 * @date 2016-8-23 下午4:52:51
 */
public class GcUtils {

	private static final String DICT_TYPE = "GC_WDGQ";
	
	private static Log logger = LogFactory.getLog(GcUtils.class);
	
	public static void main() throws Exception{
		try {
			
			if (!Boolean.valueOf(DCConfig.getProperty("GC_ATTACH_SEND_MESS"))) {
				logger.info("过期文档，定时发送短信功能已经关闭！");
				return;
			}
			
			GcAttach attach = new GcAttach();
			attach.setPageSize(Integer.MAX_VALUE - 1);
			
			attach.setColumn07("2");//文档类型2纸质原件
			getGcAttach(attach);
			
			attach.setColumn07("3");//文档类型3纸质复印件
			getGcAttach(attach);
			
			logger.info("定时发送过期文档预警----成功----！");
			
		} catch (Exception e) {
			logger.info("定时发送过期文档预警----失败----！");
			e.printStackTrace();
		}

	}
	
	private static void getGcAttach(GcAttach attach) throws Exception{
		
		List<SysParameter> listDict  = SysParamHelper.getSysParamListByParamTypeCode(DICT_TYPE);
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		IGcAttachService gcAttachService = (IGcAttachService)SpringHelper.getBean("gcAttachService");
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		List<GcAttach> list = new ArrayList<GcAttach>();
		ItemPage itemPage = gcAttachService.findGcAttach(attach);
		list = (List<GcAttach>) itemPage.getItems();
		
		for (int i = 0; i < list.size(); i++) {
			GcAttach vo = list.get(i);
			Date endDate = vo.getColumn02();
			//文档上传人
			SysUser sysUser = (SysUser)enterpriseService.getById(SysUser.class, vo.getCreateUserId());
			if (endDate == null || sysUser == null) continue;
			//文档上传人室经理
			String sjlMobile = "";
	        String sql = "select u.user_id,u.title,u.mobile from sys_user u where u.organization_id = '"+sysUser.getOrganizationId()+"'";
  	        List<Map<String, Object>> listMap = (List<Map<String, Object>>)jdbcTemplate.queryForList(sql);
                
            if (listMap != null && listMap.size() > 0) {
                for (int j=0;j<listMap.size();j++) {
                	Map<String, Object> objMap = listMap.get(j);
    				String title = (String)objMap.get("title");
    				String mobile = (String)objMap.get("mobile");
    				if (title != null && !title.equals("") && title.indexOf("室经理") != -1 && mobile != null && !mobile.equals("")) {
    					sjlMobile = mobile;
    					break;
    				}
                }
             }
			
			long day = getDay(new Date(), endDate);
			
			for (int j = 0; j < listDict.size(); j++) {
				SysParameter sys = listDict.get(j);
				Integer v = Integer.valueOf(sys.getParameterValue());
				if(v == day){
					String d = "还有"+day+"天";
					if (day == 0) {
						d = "过了今天";
					}
					//发短信
					String msg = "你提交归档的"+vo.getColumn01()+""+ d + "将要过期，请尽快续办，如已完成，请忽略。";
					logger.info(msg);
					ISmsService smsService = (ISmsService)SpringHelper.getBean("smsService");
					if(sysUser.getMobile() != null && !sysUser.getMobile().equals("")){
						smsService.sendMessage(sysUser.getMobile(), msg);
					}
					if (!sjlMobile.equals("")) {
						smsService.sendMessage(sjlMobile, msg);
					}
				}
			}
		}
	}
	
	
	private static long getDay(Date startDate,Date endDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		long day = ((sdf.parse(sdf.format(endDate))).getTime()-(sdf.parse(sdf.format(startDate))).getTime())/(24*60*60*1000);
		return day;      
	}
	
	/**
	 * 部门列表
	 * @author WEIFEI
	 * @date 2016-8-26 下午12:59:10
	 * @return	List<SysOrganization>
	 */
	public static List<SysOrganization> getDeptList(){
		
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("orgOrder > 0 ");
		query.where("orgOrder < 9 ");
		query.where("parentId",RulesCommon.sysorganizationrootID,QueryAction.EQUAL);
		query.orderBy("orgOrder asc ");
		List<SysOrganization> list = (List<SysOrganization>)enterpriseService.query(query, 0);
		
		return list;
	}
	
	/**
	 * 部门列表
	 * @author WEIFEI
	 * @date 2016-8-26 下午12:59:10
	 * @return	List<SysOrganization>
	 */
	public static List<SysOrganization> getDeptList(String deptId){
		
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("parentId",deptId,QueryAction.EQUAL);
		query.orderBy("orgOrder asc ");
		List<SysOrganization> list = (List<SysOrganization>)enterpriseService.query(query, 0);
		
		return list;
	}
	
	/**
	 * 科室名称
	 * @author WEIFEI
	 * @date 2016-10-10 下午4:37:02
	 * @param id
	 * @return	String
	 */
	public static String getKsName(String id) {
		if(id == null || id.equals(""))
			return "";
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		SysOrganization org = (SysOrganization)enterpriseService.getById(SysOrganization.class, id);
		return  org.getOrgName();
	}
	
	public static List<SysUser> findSysUserByorgId(String orgId){
		
		IEnterpriseService enterpriseService = (IEnterpriseService)SpringHelper.getBean("enterpriseService");
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("organizationId",orgId,QueryAction.EQUAL);
		List<SysUser> list = (List<SysUser>)enterpriseService.query(query, 0);
		
		return list;
	}
}
