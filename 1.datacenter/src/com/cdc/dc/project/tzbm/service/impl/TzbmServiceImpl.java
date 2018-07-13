package com.cdc.dc.project.tzbm.service.impl;

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
import com.cdc.dc.project.tzbm.model.Tzbm;
import com.cdc.dc.project.tzbm.model.TzbmYear;
import com.cdc.dc.project.tzbm.service.ITzbmService;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class TzbmServiceImpl implements ITzbmService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findTzbm(Tzbm tzbm) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Tzbm.class);
		
		String column04 = tzbm.getColumn04();
		
		//建设项目编码
		if (column04 != null && !column04.equals("")) {
			query.where(" column04 = '"+column04+"' ");
		}
		
		String column01 = tzbm.getColumn01();
		String column03 = tzbm.getColumn03();
		String column13 = tzbm.getColumn13();
		String column02 = tzbm.getColumn02();
		String isNew = tzbm.getIsNew();
		String column18 = tzbm.getColumn18();
		String related = tzbm.getRelated();//与我相关
		Date beginCreateTime = tzbm.getBeginCreateTime();
		Date endCreateTime = tzbm.getEndCreateTime();
		
		//项目类型
		if (column01 != null && !column01.equals("")) {
			query.where(" column01 = '"+column01+"' ");
		}
		//投资项目名称
		if (column03 != null && !column03.equals("")) {
			query.where(" upper(column03) like '%"+column03.toUpperCase()+"%' ");
		}
		//投资项目联系人
		if (column13 != null && !column13.equals("")) {
			query.where(" column13 like '%"+column13+"%' ");
		}
		//投资编号
		if (column02 != null && !column02.equals("")) {
			query.where(" column02 like '%"+column02+"%' ");
		}
		//状态
		if (column18 != null && !column18.equals("")) {
			query.where(" column18 = '"+column18+"' ");
		}
		
		//创建开始时间
		if (beginCreateTime != null && !beginCreateTime.equals("")) {
			query.where("column17",beginCreateTime,QueryAction.GT);
		}
		
		//创建结束时间
		if (endCreateTime != null && !endCreateTime.equals("")) {
			Calendar cd = Calendar.getInstance();
            cd.setTime(endCreateTime);
            cd.add(Calendar.DATE, 1);//增加一天
			query.where("column17",cd.getTime(),QueryAction.LE);
		}
		
		//数据创建人ID
		String createUserId = tzbm.getCreateUserId();
		if (createUserId != null && !createUserId.equals("")) {
			query.where(" createUserId = '"+createUserId+"' ");
		}
		
		//投资编码管理员
		String column19 = tzbm.getColumn19();
		if (column19 != null && !column19.equals("")) {
			query.where(" column19 like '%"+column19+"%' ");
		}
		
		//部门
		String dept = tzbm.getDept(),deptSQL = "";
		if(dept != null && !dept.equals("")){
			List<SysOrganization> list = GcUtils.getDeptList(dept);
			for (int j = 0; j < list.size(); j++) {
				List<SysUser> listU = GcUtils.findSysUserByorgId(list.get(j).getOrganizationId());
				for (int i = 0; i < listU.size(); i++) {
					deptSQL += " or column19 like '%"+listU.get(i).getUserId()+"%' ";
				}
			}
			if (!deptSQL.equals("")) {
				query.where("("+deptSQL.substring(4)+")");
			}
		}
		
		//科室
		String ks = tzbm.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or column19 like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				query.where("("+ksSQL.substring(4)+")");
			}
		}		
		//是否置顶
		if (isNew != null && !isNew.equals("")) {
			query.where(" isNew = '"+isNew+"' ");
			query.orderBy("newDate desc");
		}
		//ids查询
		String ids = tzbm.getIds();
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
			query.where(" ( column13 like '%"+related+"%' or column14 like '%"+related+"%' or updateUserId like '%"+related+"%' or createUserId like '%"+related+"%' or column19 like '%"+related+"%' )");
		}
		query.where(" deleteFlag = 'N' ");
		query.orderBy("column17 desc");
		
		return enterpriseService.query(query, tzbm);
	}

	@Override
	public Tzbm findTzbmById(String id) throws Exception {
		
		return (Tzbm)enterpriseService.getById(Tzbm.class, id);
	}

	@Override
	public Tzbm save(Tzbm tzbm) throws Exception {
		
		enterpriseService.save(tzbm);
		
		return tzbm;
	}

	@Override
	public Tzbm update(Tzbm tzbm) throws Exception {
		
		enterpriseService.updateObject(tzbm);
		
		return tzbm;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Tzbm.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}

	@Override
	public ItemPage findTzbmYear(TzbmYear tzbmYear) throws Exception {
		QueryBuilder query = new QueryBuilder(TzbmYear.class);
		
		String parentId = tzbmYear.getParentId();
		
		//投资编码
		if (parentId != null && !parentId.equals("")) {
			query.where(" parentId = '"+parentId+"' ");
		}
		
		query.orderBy("createDate desc");
		
		return enterpriseService.query(query, tzbmYear);
	}

	@Override
	public TzbmYear findTzbmYearById(String id) throws Exception {
		return (TzbmYear)enterpriseService.getById(TzbmYear.class, id);
	}

	@Override
	public TzbmYear save(TzbmYear tzbmYear) throws Exception {
		enterpriseService.save(tzbmYear);
		return tzbmYear;
	}

	@Override
	public TzbmYear update(TzbmYear tzbmYear) throws Exception {
		
		enterpriseService.updateObject(tzbmYear);
		
		return tzbmYear;
	}

	@Override
	public void deleteTzbmYear(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(TzbmYear.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}
	
	
}
