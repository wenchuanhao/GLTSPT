package com.cdc.dc.project.zxm.service.impl;

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
import com.cdc.dc.project.zxm.model.Zxm;
import com.cdc.dc.project.zxm.service.IZxmService;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class ZxmServiceImpl implements IZxmService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findZxm(Zxm zxm) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Zxm.class);
		
		String column07 = zxm.getColumn07();
		
		//投资编码
		if (column07 != null && !column07.equals("")) {
			query.where(" column07 = '"+column07+"' ");
		}
		
		String column01 = zxm.getColumn01();
		String column03 = zxm.getColumn03();
		String column09 = zxm.getColumn09();
		String column02 = zxm.getColumn02();
		String related = zxm.getRelated();//与我相关
		Date beginCreateTime = zxm.getBeginCreateTime();
		Date endCreateTime = zxm.getEndCreateTime();
		
		//项目类型
		if (column01 != null && !column01.equals("")) {
			query.where(" column01 = '"+column01+"' ");
		}
		//子项目名称
		if (column03 != null && !column03.equals("")) {
			query.where(" upper(column03) like '%"+column03.toUpperCase()+"%' ");
		}
		//子项目管理员
		if (column09 != null && !column09.equals("")) {
			query.where(" column09 like '%"+column09+"%' ");
		}
		//部门
		String dept = zxm.getDept(),deptSQL = "";
		if(dept != null && !dept.equals("")){
			List<SysOrganization> list = GcUtils.getDeptList(dept);
			for (int j = 0; j < list.size(); j++) {
				List<SysUser> listU = GcUtils.findSysUserByorgId(list.get(j).getOrganizationId());
				for (int i = 0; i < listU.size(); i++) {
					deptSQL += " or column09 like '%"+listU.get(i).getUserId()+"%' ";
				}
			}
			if (!deptSQL.equals("")) {
				query.where("("+deptSQL.substring(4)+")");
			}
		}
		//科室
		String ks = zxm.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or column09 like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				query.where("("+ksSQL.substring(4)+")");
			}
		}		
		//子项目编号
		if (column02 != null && !column02.equals("")) {
			query.where(" column02 like '%"+column02+"%' ");
		}
		
		//创建开始时间
		if (beginCreateTime != null && !beginCreateTime.equals("")) {
			query.where("column08",beginCreateTime,QueryAction.GT);
		}
		
		//创建结束时间
		if (endCreateTime != null && !endCreateTime.equals("")) {
			Calendar cd = Calendar.getInstance();
            cd.setTime(endCreateTime);
            cd.add(Calendar.DATE, 1);//增加一天
			query.where("column08",cd.getTime(),QueryAction.LE);
		}
		
		//数据创建人ID
		String createUserId = zxm.getCreateUserId();
		if (createUserId != null && !createUserId.equals("")) {
			query.where(" createUserId = '"+createUserId+"' ");
		}

		//ids查询
		String ids = zxm.getIds();
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
			query.where(" ( column04 like '%"+related+"%' or column05 like '%"+related+"%' or updateUserId like '%"+related+"%' or createUserId like '%"+related+"%' or column09 like '%"+related+"%' )");
		}
		
		query.where(" deleteFlag = 'N' ");
		query.orderBy("createDate desc");
		
		return enterpriseService.query(query, zxm);
	}

	@Override
	public Zxm findZxmById(String id) throws Exception {
		
		return (Zxm)enterpriseService.getById(Zxm.class, id);
	}

	@Override
	public Zxm save(Zxm zxm) throws Exception {
		
		enterpriseService.save(zxm);
		
		return zxm;
	}

	@Override
	public Zxm update(Zxm zxm) throws Exception {
		
		enterpriseService.updateObject(zxm);
		
		return zxm;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(Zxm.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}
	
	
}
