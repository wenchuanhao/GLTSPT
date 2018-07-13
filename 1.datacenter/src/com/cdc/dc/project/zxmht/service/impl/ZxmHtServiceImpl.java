package com.cdc.dc.project.zxmht.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.model.ZxmHtAttach;
import com.cdc.dc.project.zxmht.service.IZxmHtService;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class ZxmHtServiceImpl implements IZxmHtService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findZxmHt(ZxmHt zxmHt) throws Exception {
		
		QueryBuilder query = new QueryBuilder(ZxmHt.class);
		
		String column04 = zxmHt.getColumn04();
		String xmType = zxmHt.getXmType();
		String column03 = zxmHt.getColumn03();
		String column21 = zxmHt.getColumn21();
		String column01 = zxmHt.getColumn01();
		String related = zxmHt.getRelated();//与我相关
		Date beginCreateTime = zxmHt.getBeginCreateTime();
		Date endCreateTime = zxmHt.getEndCreateTime();
		
		//项目类型
		if (xmType != null && !xmType.equals("")) {
			query.where(" xmType = '"+xmType+"' ");
		}
		//合同名称
		if (column03 != null && !column03.equals("")) {
			query.where(" upper(column03) like '%"+column03.toUpperCase()+"%' ");
		}
		//合同管理员
		if (column21 != null && !column21.equals("")) {
			query.where(" column21 like '%"+column21+"%' ");
		}
		//部门
		String dept = zxmHt.getDept(),deptSQL = "";
		if(dept != null && !dept.equals("")){
			List<SysOrganization> list = GcUtils.getDeptList(dept);
			for (int j = 0; j < list.size(); j++) {
				List<SysUser> listU = GcUtils.findSysUserByorgId(list.get(j).getOrganizationId());
				for (int i = 0; i < listU.size(); i++) {
					deptSQL += " or column21 like '%"+listU.get(i).getUserId()+"%' ";
				}
			}
			if (!deptSQL.equals("")) {
				query.where("("+deptSQL.substring(4)+")");
			}
		}
		//科室
		String ks = zxmHt.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or column21 like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				query.where("("+ksSQL.substring(4)+")");
			}
		}		
		//子项目编码
		if (column04 != null && !column04.equals("")) {
			String sql = "select id from gc_zxm where column_02 like '%"+column04+"%' ";
			Query query1 = enterpriseService.getSessions().createSQLQuery(sql.toString());
			String ids = ""; 
			List<String> temp = query1.list();
			if(temp != null && temp.size() > 0 ){
				ids += "(";
				for (int i = 0; i < temp.size(); i++) {
					if(i == 0){
						ids += "'"+temp.get(i)+"'";
					}else{
						ids += ",'"+temp.get(i)+"'";
					}
				}
				ids += ")";
			}
			if(StringUtils.isNotEmpty(ids)){
				query.where("( column04 in "+ids+" or column04 = '"+column04+"' )");
			}else{
				query.where(" column04 = '"+column04+"' ");
			}
		}
		//合同编号
		if (column01 != null && !column01.equals("")) {
			query.where(" column01 like '%"+column01.trim()+"%' ");
		}		
		//合同签订开始时间
		if (beginCreateTime != null && !beginCreateTime.equals("")) {
			query.where(" column19",beginCreateTime,QueryAction.GT);
		}
		
		//合同签订结束时间
		if (endCreateTime != null && !endCreateTime.equals("")) {
			Calendar cd = Calendar.getInstance();
            cd.setTime(endCreateTime);
            cd.add(Calendar.DATE, 1);//增加一天
			query.where(" column19",cd.getTime(),QueryAction.LE);
		}
		
		//查找合同所属上级不为空
		String createUserId = zxmHt.getCreateUserId();
		if (createUserId != null && !createUserId.equals("")) {
			query.where(" column04  is not null ");
		}
		
		//认领状态
		String rlStatus = zxmHt.getRlStatus();
		if (rlStatus != null && !rlStatus.equals("")) {
			query.where(" column21 is "+(rlStatus.equals("Y") == true ? "not":"")+" null ");
		}
		//预算项目
		String column15 = zxmHt.getColumn15();
		if (column15 != null && !column15.equals("")) {
			query.where(" upper(column15) like '%"+column15.toUpperCase()+"%' ");
		}
		//ids查询
		String ids = zxmHt.getIds();
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
			query.where(" (  updateUserId like '%"+related+"%' or createUserId like '%"+related+"%' or column21 like '%"+related+"%' )");
		}
		
		query.where(" deleteFlag = 'N' ");
		query.orderBy(" nvl(createDate,to_date('1970-01-01','yyyy-mm-dd')) desc");
		
		return enterpriseService.query(query, zxmHt);
	}

	@Override
	public ZxmHt findZxmHtById(String id) throws Exception {
		
		return (ZxmHt)enterpriseService.getById(ZxmHt.class, id);
	}

	@Override
	public ZxmHt save(ZxmHt zxmHt) throws Exception {
		
		enterpriseService.save(zxmHt);
		
		return zxmHt;
	}

	@Override
	public ZxmHt update(ZxmHt zxmHt) throws Exception {
		
		enterpriseService.updateObject(zxmHt);
		
		return zxmHt;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(ZxmHt.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}

	@Override
	public ItemPage findZxmHtAttach(ZxmHtAttach htAttach) throws Exception {
		QueryBuilder query = new QueryBuilder(ZxmHtAttach.class);
		
		String parentId = htAttach.getParentId();
		
		//父ID
		if (parentId != null && !parentId.equals("")) {
			query.where(" parentId = '"+parentId+"' ");
		}
		query.orderBy("createDate desc");
		
		return enterpriseService.query(query, htAttach);
	}

	@Override
	public ZxmHtAttach saveZxmHtAttach(ZxmHtAttach htAttach) throws Exception {
		
		enterpriseService.saveOrUpdate(htAttach);
		
		return htAttach;
	}

	@Override
	public void deleteZxmHtAttach(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(ZxmHtAttach.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}

	@Override
	public ZxmHtAttach findZxmHtAttachById(String id) throws Exception {
			return (ZxmHtAttach)enterpriseService.getById(ZxmHtAttach.class, id);
	}
	
	
}
