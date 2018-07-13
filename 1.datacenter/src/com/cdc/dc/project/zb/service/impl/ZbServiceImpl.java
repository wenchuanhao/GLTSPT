package com.cdc.dc.project.zb.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.zb.model.GcZbAudits;
import com.cdc.dc.project.zb.model.GczbListView;
import com.cdc.dc.project.zb.model.Zb;
import com.cdc.dc.project.zb.service.IZbService;
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.rules.common.RulesCommon;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class ZbServiceImpl implements IZbService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findZb(Zb zb) throws Exception {
		
		QueryBuilder query = new QueryBuilder(GczbListView.class);
		query.where("column14", zb.getColumn14(), QueryAction.EQUAL);//业务类型
		query.where("column01", zb.getColumn01(), QueryAction.LIKE);//周报名称
		query.where("column02", zb.getColumn02(), QueryAction.LIKE);//项目编号
		query.where("projectName", zb.getProjectName(), QueryAction.LIKE);//项目名称
		query.where("column07Departmen", zb.getColumn07Departmen(), QueryAction.EQUAL);//汇报人科室
		query.where("column07", zb.getColumn07(), QueryAction.EQUAL);//汇报人
		query.where("column10", zb.getColumn10(), QueryAction.EQUAL);//事项状态
		query.where("column11", zb.getColumn11(), QueryAction.EQUAL);//本周状态
		query.where("deleteFlag", "N", QueryAction.EQUAL);//删除标志
		query.where("zbStatus",Zb.zbStatus_FB,QueryAction.EQUAL);//已发布
		
		if(StringUtils.isNotEmpty(zb.getZbIds())){
			query.where(" id in ("+zb.getZbIds()+") ");
		}
		
		if( zb.getColumn08() != null ){
			query.where("column13", zb.getColumn08(), QueryAction.GT);
		}
		if( zb.getColumn09() != null ){
			Calendar cd = Calendar.getInstance();   
            cd.setTime(zb.getColumn09());   
            cd.add(Calendar.DATE, 1);//增加一天
			query.where("column13", cd.getTime(), QueryAction.LE);
		}
//		管理员，可编辑、删除，查看所有人的周报
		if(!FxkForm.GCZB_ADMIN.equals(zb.getUserRoles())){
//			项目创建人：可编辑、删除自己创建的周报，查看所有人该项目的周报
//			周报创建人：只可编辑、删除、查看自己的周报
			query.where(" ( createUserId='"+zb.getCreateUserId()+"' or projectUserid='"+zb.getCreateUserId()+"' ) ");//周报创建人与项目创建人
		}
		
		String related = zb.getRelated();//与我相关
		if(StringUtils.isNotEmpty(related)){
			query.where(" ( column07UserId like '%"+related+"%' or column12UserId like '%"+related+"%' or updateUserId like '%"+related+"%' or createUserId like '%"+related+"%' or projectUserid like '%"+related+"%'  or auditUserid like '%"+related+"%' )");
		}
//		query.orderBy("updateDate desc");
		
		return enterpriseService.query(query, zb);
	}

	@Override
	public Zb findZbById(String id) throws Exception {
		Session session = enterpriseService.getSessions();
		Zb obj = (Zb) enterpriseService.getById(Zb.class,id);
		session.clear();
		session = null;
		return  obj;
//		return (Zb)enterpriseService.getById(Zb.class, id);
	}

	@Override
	public Zb save(Zb Zb) throws Exception {
		
		enterpriseService.save(Zb);
		
		return Zb;
	}

	@Override
	public Zb update(Zb Zb) throws Exception {
		
		enterpriseService.updateObject(Zb);
		
		return Zb;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Zb.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}

	@Override
	public List<Object[]> searchProjectByCode(String code) {
		code = code.toLowerCase().trim();
		//建设项目 id,项目类型，项目编号，项目名称，审核人
		String sql1 = "select id,COLUMN_01,column_02,column_03,COLUMN_10,CREATE_USER_ID,'1' protype from GC_JSXM t " +
				" where t. delete_flag = 'N' and ( Lower(t.column_02) like ? or Lower(t.column_03) like ? or Lower(t.id)=? )";
		//投资编码
		String sql2 = "select id,COLUMN_01,column_02,column_03,COLUMN_19,CREATE_USER_ID,'2' protype from GC_TZBM t " +
				" where t. delete_flag = 'N' and ( Lower(t.column_02) like ? or Lower(t.column_03) like ? or Lower(t.id)=? )";
		//子项目
		String sql3 = "select id,COLUMN_01,column_02,column_03,COLUMN_09,CREATE_USER_ID,'3' protype from GC_ZXM  t " +
				" where t. delete_flag = 'N' and ( Lower(t.column_02) like ? or Lower(t.column_03) like ? or Lower(t.id)=? )";
		//子项目合同
		String sql4 = "select t.id,tt.COLUMN_01,t.column_01 as column_02,t.column_03,t.COLUMN_21,t.CREATE_USER_ID,'4' protype from GC_ZXMHT t left join GC_ZXM tt on t.COLUMN_04=tt.id " +
				" where t. delete_flag = 'N' and ( Lower(t.column_01) like ? or Lower(t.column_03) like ? or Lower(t.id)=? )";
        Query query1 = enterpriseService.getSessions().createSQLQuery(sql1.toString());
        query1.setString(0,"%"+code+"%");
        query1.setString(1,"%"+code+"%");
        query1.setString(2,code);
        Query query2 = enterpriseService.getSessions().createSQLQuery(sql2.toString());
        query2.setString(0,"%"+code+"%");
        query2.setString(1,"%"+code+"%");
        query2.setString(2,code);
        Query query3 = enterpriseService.getSessions().createSQLQuery(sql3.toString());
        query3.setString(0,"%"+code+"%");
        query3.setString(1,"%"+code+"%");
        query3.setString(2,code);
        Query query4 = enterpriseService.getSessions().createSQLQuery(sql4.toString());
        query4.setString(0,"%"+code+"%");
        query4.setString(1,"%"+code+"%");
        query4.setString(2,code);
        List<Object[]> list = query1.list();
        list.addAll(query2.list());
        list.addAll(query3.list());
        list.addAll(query4.list());
        return list;
	}

	@Override
	public List<SysOrganization> querySysOrganizationList() {
		QueryBuilder query = new QueryBuilder(SysOrganization.class);
		query.where("organizationId",RulesCommon.sysorganizationrootID,QueryAction.NOEQUAL);
		query.where("parentId",RulesCommon.sysorganizationrootID,QueryAction.EQUAL);
		return (List<SysOrganization>)enterpriseService.query(query, 0);
	}

	@Override
	public List<Zb> ajaxLoadHistory(String proCode, String userId) {
		QueryBuilder query = new QueryBuilder(Zb.class);
		query.where("projectId", proCode, QueryAction.EQUAL);//项目编号
		query.where("createUserId",userId, QueryAction.EQUAL);//创建人
		query.where("deleteFlag", "N", QueryAction.EQUAL);//删除标志
		query.orderBy("updateDate desc");
		return (List<Zb>) enterpriseService.query(query, 0);
	}

	@Override
	public List<Object[]> searchDepartmenByName(String code) {
		code = code.toLowerCase();
		String sql = "select organization_id,root_name from SYS_ORGANIZATION_VIEW t where t.root_name like ?";
		Query query = enterpriseService.getSessions().createSQLQuery(sql);
        query.setString(0,"%"+code+"%");
		return query.list();
	}

	@Override
	public List<SysUser> ajaxLoadAuditUser(String proId, String productType) {
		List<SysUser> result = new ArrayList<SysUser>();
		if(StringUtils.isEmpty(proId)  || StringUtils.isEmpty(productType)){
			return result;
		}
		Jsxm jsxm = null;
		//建设项目
		if("1".equals(productType)){
			jsxm = (Jsxm) enterpriseService.queryEntity(Jsxm.class, proId);
		}
		//投资编码
		if("2".equals(productType)){
			Tzbm tzbm = (Tzbm) enterpriseService.queryEntity(Tzbm.class, proId);
			if(tzbm != null){
				jsxm = tzbm.getJsxm();
			}
		}
		//子项目
		if("3".equals(productType)){
			Zxm zxm = (Zxm) enterpriseService.queryEntity(Zxm.class, proId);
			if(zxm!=null){
				Tzbm tzbm = zxm.getTzbm();
				if(tzbm!=null){
					jsxm  = tzbm.getJsxm();
				}
			}
		}
		//子项目合同
		if("4".equals(productType)){
			ZxmHt zxmht = (ZxmHt) enterpriseService.queryEntity(ZxmHt.class, proId);
			if(zxmht != null){
				Zxm zxm = zxmht.getZxm();
				if(zxm!=null){
					Tzbm tzbm = zxm.getTzbm();
					if(tzbm!=null){
						jsxm  = tzbm.getJsxm();
					}
				}
			}
		}
		if(jsxm == null){
			return result;
		}
		 String userId = jsxm.getColumn04();//建设项目总控
		 if(StringUtils.isNotEmpty(userId)){
			 String[] userIds = userId.split(",");
			 if(userIds != null && userIds.length > 0){
				 for (int j = 0; j < userIds.length; j++) {
					 String userid = userIds[j];
					 if(StringUtils.isNotEmpty(userid)){
						 //根据id查询用户信息
						 SysUser user = (SysUser) enterpriseService.getById(SysUser.class, userid);
						 if(user != null){
							 result.add(user);
						 }
					 }
				 }
			 }
		 }
		return result;
	}

	@Override
	public ItemPage findRemainZb(Zb zb) {
		
		QueryBuilder query = new QueryBuilder(Zb.class);
		query.where("column14", zb.getColumn14(), QueryAction.EQUAL);//业务类型
		query.where("column01", zb.getColumn01(), QueryAction.LIKE);//周报名称
		query.where("column02", zb.getColumn02(), QueryAction.LIKE);//项目编号
		query.where("projectName", zb.getProjectName(), QueryAction.LIKE);//项目名称
		query.where("column07Departmen", zb.getColumn07Departmen(), QueryAction.EQUAL);//汇报人科室
		query.where("column07", zb.getColumn07(), QueryAction.EQUAL);//汇报人
		query.where("column10", zb.getColumn10(), QueryAction.EQUAL);//事项状态
		query.where("column11", zb.getColumn11(), QueryAction.EQUAL);//本周状态
		query.where("deleteFlag", "N", QueryAction.EQUAL);//删除标志
		query.where("zbStatus",Zb.zbStatus_FB,QueryAction.NOEQUAL);//已发布
		
		if( zb.getColumn08() != null ){
			query.where("column13", zb.getColumn08(), QueryAction.GT);
		}
		if( zb.getColumn09() != null ){
			Calendar cd = Calendar.getInstance();   
            cd.setTime(zb.getColumn09());   
            cd.add(Calendar.DATE, 1);//增加一天
			query.where("column13", cd.getTime(), QueryAction.LE);
		}
		
		query.where(" (column07UserId='"+zb.getCreateUserId()+"' or auditUserid='"+zb.getCreateUserId()+"' )");
		
		query.orderBy("updateDate desc");
		
		return enterpriseService.query(query, zb);
	}

	@Override
	public void ajaxPast(Zb zb, SysUser sysUser) {
		saveGcZbAudits(zb,"通过",sysUser,Zb.zbStatus_FB);//已发布
	}

	@Override
	public void returnRemain(Zb zb, String auditDesc, SysUser sysUser) {
		saveGcZbAudits(zb,auditDesc,sysUser,Zb.zbStatus_TH);//不通过
	}
	
	
	private void saveGcZbAudits(Zb zb, String auditDesc, SysUser sysUser, String status){

		zb.setZbStatus(status);//已发布
		enterpriseService.updateObject(zb);
		
		GcZbAudits gza = new GcZbAudits();
		gza.setZbId(zb.getId());
		gza.setAuditUserid(sysUser.getUserId());
		gza.setAuditUsername(sysUser.getUserName());
		gza.setAuditDepid(sysUser.getOrganizationId());
		gza.setAuditTime(new Date());
		gza.setAuditResult(status);//不通过
		gza.setAuditDesc(auditDesc);
		enterpriseService.save(gza);
	
	}

	@Override
	public List<GcZbAudits> queryGcZbAuditsList(String id) {
		if(StringUtils.isEmpty(id)){
			return null;
		}
		QueryBuilder query = new QueryBuilder(GcZbAudits.class);
		query.where("zbId", id, QueryAction.EQUAL);//项目编号
		query.orderBy("auditTime desc");
		return (List<GcZbAudits>) enterpriseService.query(query, 0);
	}

	@Override
	public List<SysUser> querySysUserByOrg(String organizationId) {
		if(StringUtils.isEmpty(organizationId)){
			return null;
		}
		QueryBuilder query = new QueryBuilder(SysUser.class);
		query.where("organizationId", organizationId, QueryAction.EQUAL);
		query.where("title", "室经理", QueryAction.LIKE);
		return (List<SysUser>) enterpriseService.query(query, 0);
	}

	@Override
	public ItemPage selectList(Zb zb) {
		String sql = "select * from ( select id,COLUMN_01,column_02,column_03,COLUMN_10 user_ids,COLUMN_07 CREATE_DATE,'1' protype,'建设项目' proName from GC_JSXM t where t. delete_flag = 'N' " +
				" union all " +
				" select id,COLUMN_01,column_02,column_03,COLUMN_19 user_ids,COLUMN_17 CREATE_DATE,'2' protype,'投资编码' proName from GC_TZBM t where t. delete_flag = 'N' " +
				" union all " +
				" select id,COLUMN_01,column_02,column_03,COLUMN_09 user_ids,COLUMN_08 CREATE_DATE,'3' protype,'子项目' proName from GC_ZXM  t  where t. delete_flag = 'N' " +
				" union all " +
				" select t.id,tt.COLUMN_01,t.column_01 as column_02,t.column_03,t.COLUMN_21 user_ids,t.CREATE_DATE CREATE_DATE,'4' protype,'子项目合同' proName from GC_ZXMHT t left join GC_ZXM tt on t.COLUMN_04=tt.id where t. delete_flag = 'N' " +
				" ) where 1=1 ";
		//项目业务类型
		if(StringUtils.isNotEmpty(zb.getColumn14())){
			sql += " and protype='"+zb.getColumn14()+"' ";
		}
		//项目编号
		if(StringUtils.isNotEmpty(zb.getColumn02())){
			sql += " and column_02 like '%"+zb.getColumn02()+"%' ";
		}
		//项目名称
		if(StringUtils.isNotEmpty(zb.getProjectName())){
			sql += " and column_03 like '%"+zb.getProjectName()+"%' ";
		}
		//创建时间
		if(StringUtils.isNotEmpty(zb.getBeginCreateTime())){
			sql += " and CREATE_DATE >= to_date('"+zb.getBeginCreateTime()+" 00:00:01','yyyy-MM-dd HH24:MI:SS') ";
		}
		if(StringUtils.isNotEmpty(zb.getEndCreateTime())){
			sql += " and CREATE_DATE <= to_date('"+zb.getEndCreateTime()+" 23:59:59','yyyy-MM-dd HH24:MI:SS') ";
		}
		sql += " order by create_date  desc NULLS LAST ";
		return enterpriseService.findBySql(sql, zb);
	}
	
}
