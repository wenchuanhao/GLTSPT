package com.cdc.dc.project.jsxm.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.jsxm.model.Jsxm;
import com.cdc.dc.project.jsxm.service.IJsxmService;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class JsxmServiceImpl implements IJsxmService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findJsxm(Jsxm jsxm) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Jsxm.class);
		
		String column01 = jsxm.getColumn01();
		String column02 = jsxm.getColumn02();
		String column04 = jsxm.getColumn04();
		String column08 = jsxm.getColumn08();
		String column03 = jsxm.getColumn03();
		String isNew = jsxm.getIsNew();
		String related = jsxm.getRelated();//与我相关
		
		Date beginCreateTime = jsxm.getBeginCreateTime();
		Date endCreateTime = jsxm.getEndCreateTime();
		
		//根据ID查找内容
		if(StringUtils.isNotEmpty(jsxm.getId())){
			query.where(" id in ("+jsxm.getId()+") ");
		}
		
		//项目类型
		if (column01 != null && !column01.equals("")) {
			query.where(" column01 = '"+column01+"' ");
		}
		//建设总控
		if (column04 != null && !column04.equals("")) {
			query.where(" column04 like '%"+column04+"%' ");
		}
		//项目状态
		if (column08 != null && !column08.equals("")) {
			query.where(" column08 = '"+column08+"' ");
		}
		//建设项目编码
		if (column02 != null && !column02.equals("")) {
			query.where(" column02 like '%"+column02+"%' ");
		}
		//建设项目名称
		if (column03 != null && !column03.equals("")) {
			query.where(" upper(column03) like '%"+column03.toUpperCase()+"%' ");
		}
		//创建开始时间
		if (beginCreateTime != null && !beginCreateTime.equals("")) {
			query.where("column07",beginCreateTime,QueryAction.GT);
		}
		
		//创建结束时间
		if (endCreateTime != null && !endCreateTime.equals("")) {
			Calendar cd = Calendar.getInstance();
            cd.setTime(endCreateTime);
            cd.add(Calendar.DATE, 1);//增加一天
			query.where("column07",cd.getTime(),QueryAction.LE);
		}
		
		//建设项目管理员
		String column10 = jsxm.getColumn10();
		if (column10 != null && !column10.equals("")) {
			query.where(" column10 like '%"+column10+"%' ");
		}
		
		//部门
		String dept = jsxm.getDept(),deptSQL = "";
		if(dept != null && !dept.equals("")){
			List<SysOrganization> list = GcUtils.getDeptList(dept);
			for (int j = 0; j < list.size(); j++) {
				List<SysUser> listU = GcUtils.findSysUserByorgId(list.get(j).getOrganizationId());
				for (int i = 0; i < listU.size(); i++) {
					deptSQL += " or column10 like '%"+listU.get(i).getUserId()+"%' ";
				}
			}
			if (!deptSQL.equals("")) {
				query.where("("+deptSQL.substring(4)+")");
			}
		}
		
		//科室
		String ks = jsxm.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or column10 like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				query.where("("+ksSQL.substring(4)+")");
			}
		}
		
		query.where(" deleteFlag = 'N' ");
		
		//是否置顶
		if (isNew != null && !isNew.equals("")) {
			if (isNew.equals("Y")) {
				query.where(" isNew = '"+isNew+"' ");
				query.orderBy("newDate desc");
			}else {
				query.where(" isNew = '"+isNew+"' ");
			}
		}
		//ids查询
		String ids = jsxm.getIds();
		if (ids != null && !ids.equals("")) {
			String idS = "";
			for (int i = 0; i < ids.split(",").length; i++) {
				if(!ids.split(",")[i].equals("")){
					idS += ",'"+ids.split(",")[i]+"'";
				}
			}
			query.where(" id in ("+idS.substring(1)+")");
		}
		
		//与我相关
		if(StringUtils.isNotEmpty(related)){
			query.where(" ( column04 like '%"+related+"%' or column05 like '%"+related+"%' or updateUserId like '%"+related+"%' or createUserId like '%"+related+"%' or column10 like '%"+related+"%' )");
		}
		
		query.orderBy("column07 desc");
		
		return enterpriseService.query(query, jsxm);
	}

	@Override
	public Jsxm findJsxmById(String id) throws Exception {
		
		return (Jsxm)enterpriseService.getById(Jsxm.class, id);
	}

	@Override
	public Jsxm save(Jsxm jsxm) throws Exception {
		
		enterpriseService.save(jsxm);
		
		return jsxm;
	}

	@Override
	public Jsxm update(Jsxm jsxm) throws Exception {
		
		enterpriseService.updateObject(jsxm);
		
		return jsxm;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Jsxm.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}
	
	
}
