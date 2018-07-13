package com.cdc.dc.project.attach.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.project.attach.model.GcAttJy;
import com.cdc.dc.project.attach.model.GcAttach;
import com.cdc.dc.project.attach.service.IGcAttachService;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:23:05
 */
public class GcAttachServiceImpl implements IGcAttachService {
	
	private IEnterpriseService enterpriseService;
	
	private Log logger = LogFactory.getLog(getClass());
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public ItemPage findGcAttach(GcAttach attach) throws Exception {
		
		QueryBuilder query = new QueryBuilder(GcAttach.class);
		
		String column01 = attach.getColumn01();
		
		//项目类型
		if (column01 != null && !column01.equals("")) {
			query.where(" column01 = '"+column01+"' ");
		}
		
		String parentId = attach.getParentId();
		
		//附件目录ID
		if (parentId != null && !parentId.equals("")) {
			query.where(" parentId = '"+parentId+"' ");
		}
		
		String column10 = attach.getColumn10();
		
		//业务类型ID
		if (column10 != null && !column10.equals("")) {
			query.where(" column10 = '"+column10+"' ");
		}
		
		String jsxmId = attach.getJsxmId();
		
		//建设项目ID
		if (jsxmId != null && !jsxmId.equals("")) {
			query.where(" jsxmId = '"+jsxmId+"' ");
		}
		
		String column07 = attach.getColumn07();
		//文档类型1电子文档2纸质原件3纸质复印件
		if (column07 != null && !column07.equals("")) {
			query.where(" column07 = '"+column07+"' ");
		}
		
		query.orderBy("createDate asc");
		
		return enterpriseService.query(query, attach);
	}

	@Override
	public GcAttach findGcAttachById(String id) throws Exception {
		
		return (GcAttach)enterpriseService.getById(GcAttach.class, id);
	}

	@Override
	public GcAttach save(GcAttach attach) throws Exception {
		
		enterpriseService.save(attach);
		
		return attach;
	}

	@Override
	public GcAttach update(GcAttach attach) throws Exception {
		
		enterpriseService.updateObject(attach);
		
		return attach;
	}

	@Override
	public void delete(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(GcAttach.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}

	@Override
	public GcAttach saveOrUpdate(GcAttach attach) throws Exception {
		enterpriseService.saveOrUpdate(attach);
		return attach;
	}

	@Override
	public ItemPage findGcAttJy(GcAttJy attach) throws Exception {
		QueryBuilder query = new QueryBuilder(GcAttJy.class);
		
		String parentId = attach.getParentId();
		
		//附件目录ID
		if (parentId != null && !parentId.equals("")) {
			query.where(" parentId = '"+parentId+"' ");
		}
		
		String column07 = attach.getColumn07();
		
		//状态
		if (column07 != null && !column07.equals("")) {
			query.where(" column07 = '"+column07+"' ");
		}
		
		query.orderBy("createDate asc");
		
		return enterpriseService.query(query, attach);
	}

	@Override
	public GcAttJy findGcAttJyById(String id) throws Exception {
		return (GcAttJy)enterpriseService.getById(GcAttJy.class, id);
	}

	@Override
	public GcAttJy save(GcAttJy attach) throws Exception {
		enterpriseService.save(attach);
		
		return attach;
	}

	@Override
	public GcAttJy update(GcAttJy attach) throws Exception {
		enterpriseService.updateObject(attach);
		
		return attach;
	}

	@Override
	public GcAttJy saveOrUpdate(GcAttJy attach) throws Exception {
		enterpriseService.saveOrUpdate(attach);
		return attach;
	}

	@Override
	public void deleteGcAttJy(String id) throws Exception {
		
		QueryBuilder query = new QueryBuilder(GcAttJy.class);
		query.where("id",id,QueryAction.EQUAL);
		
		enterpriseService.delete(query);
	}
	
	
}
