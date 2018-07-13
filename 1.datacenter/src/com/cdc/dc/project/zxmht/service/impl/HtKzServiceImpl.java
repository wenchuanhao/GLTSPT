package com.cdc.dc.project.zxmht.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.sys.entity.SysUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.project.GcUtils;
import com.cdc.dc.project.zxmht.model.HtKz;
import com.cdc.dc.project.zxmht.model.ZxmHt;
import com.cdc.dc.project.zxmht.service.IHtKzService;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class HtKzServiceImpl implements IHtKzService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findHtKz(HtKz htKz) throws Exception {
		
		Class<?>[] pojos = new Class<?>[] {HtKz.class,ZxmHt.class};
		
		QueryBuilder query = new QueryBuilder(pojos);
		
		query.where(" a.column01 = b.id ");
		
		String column02 = htKz.getColumn02();
		String column01 = htKz.getColumn01();
		String htName = htKz.getHtName();
		String column13 = htKz.getColumn13();
		
		Date beginCreateTime = htKz.getBeginCreateTime();
		Date endCreateTime = htKz.getEndCreateTime();
		
		//投资编号
		if (column02 != null && !column02.equals("")) {
			query.where(" upper(a.column02) like '%"+column02.toUpperCase()+"%' ");
		}
		//合同编号
		if (column01 != null && !column01.equals("")) {
			query.where(" upper(b.column01) like '%"+column01.toUpperCase()+"%' ");
		}
		//合同名称
		if (htName != null && !htName.equals("")) {
			query.where(" upper(b.column03) like '%"+htName.toUpperCase()+"%' ");
		}
		//合同管理员
		if (column13 != null && !column13.equals("")) {
			query.where(" b.column21 like '%"+column13+"%' ");
		}
		//科室
		String ks = htKz.getKs(),ksSQL = "";
		if(ks != null && !ks.equals("")){
			List<SysUser> listU = GcUtils.findSysUserByorgId(ks);
			for (int i = 0; i < listU.size(); i++) {
				ksSQL += " or b.column21 like '%"+listU.get(i).getUserId()+"%' ";
			}
			if (!ksSQL.equals("")) {
				query.where("("+ksSQL.substring(4)+")");
			}
		}
		//记录开始时间
		if (beginCreateTime != null && !beginCreateTime.equals("")) {
			query.where(" a.column12",beginCreateTime,QueryAction.GT);
		}
		
		//记录结束时间
		if (endCreateTime != null && !endCreateTime.equals("")) {
			Calendar cd = Calendar.getInstance();
            cd.setTime(endCreateTime);
            cd.add(Calendar.DATE, 1);//增加一天
			query.where(" a.column12",cd.getTime(),QueryAction.LE);
		}
		
		//当前登录人认领的合同
		/*String createUserId = htKz.getCreateUserId();
		if (createUserId != null && !createUserId.equals("")) {
			query.where(" b.column21 like '%"+createUserId+"%' ");
		}*/
		
		query.orderBy("a.createDate desc");
		
		return enterpriseService.query(query, htKz);
	}

	@Override
	public HtKz findHtKzById(String id) throws Exception {
		
		return (HtKz)enterpriseService.getById(HtKz.class, id);
	}

	@Override
	public HtKz save(HtKz htKz) throws Exception {
		
		enterpriseService.save(htKz);
		
		return htKz;
	}

	@Override
	public HtKz update(HtKz htKz) throws Exception {
		
		enterpriseService.updateObject(htKz);
		
		return htKz;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(HtKz.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}
	
	
}
