package com.cdc.dc.advertisement.service.impl;

import java.util.Date;
import java.util.UUID;

import model.sys.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.dc.advertisement.form.AdvertisementForm;
import com.cdc.dc.advertisement.model.Advertisement;
import com.cdc.dc.advertisement.service.IAdvertisementService;
import com.trustel.common.ItemPage;

public class AdvertisementServiceImpl implements IAdvertisementService {
	private IEnterpriseService enterpriseService;
	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	@Override
	public Object getEntity(Class clazz, String id) {
		Session session = enterpriseService.getSessions();
		Object obj = enterpriseService.getById(clazz,id);
		session.clear();
		session = null;
		return  obj;
	}
	
	@Override
	public void saveEntity(Object item) throws ServiceException {
		enterpriseService.save(item);
	}

	@Override
	public void updateEntity(Object item) {
		enterpriseService.updateObject(item);
	}
	
	@Override
	public ItemPage findAdvertisement(AdvertisementForm ad) throws Exception{
		QueryBuilder query = new QueryBuilder(Advertisement.class);
		
		//广告标题
		if (StringUtils.isNotEmpty(ad.getPicTitle())) {
			query.where("picTitle",ad.getPicTitle(),QueryAction.LIKE);
		}
		//图片路径
		if (StringUtils.isNotEmpty(ad.getPicPath())) {
			query.where("picPath",ad.getPicPath(),QueryAction.LIKE);
		}
		//链接
		if (StringUtils.isNotEmpty(ad.getPicUrl())) {
			query.where("picUrl",ad.getPicUrl(),QueryAction.LIKE);
		}
		query.where("picDesc",ad.getPicDesc(),QueryAction.LIKE);
		query.where("picType",ad.getPicType(),QueryAction.LIKE);
		//序号
		if (StringUtils.isNotEmpty(ad.getPicUrl())) {
			query.where("picSort",ad.getPicSort(),QueryAction.LIKE);
		}
		query.where("createTime",ad.getCreateTime(),QueryAction.LIKE);
		query.where("createUserid",ad.getCreateUserid(),QueryAction.EQUAL);
		query.where("createUsername",ad.getCreateUsername(),QueryAction.LIKE);
		query.where("status",ad.getStatus(),QueryAction.EQUAL);
		
		query.where("status","1",QueryAction.EQUAL);
		
		query.orderBy("picSort");
				
		return enterpriseService.query(query, ad);
	}
	
	@Override
	public void saveOrUpdateAdvertisement(Advertisement ad, SysUser sysUser){
		//编辑或保存
		String id = ad.getConfigId();
		if (id == null || "".equals(id.trim())) {
			ad.setConfigId(UUID.randomUUID().toString());
		}
		Advertisement oldad =(Advertisement) getEntity(Advertisement.class, ad.getConfigId());
		if(oldad == null){
			ad.setCreateUserid(sysUser.getUserId());
			ad.setCreateUsername(sysUser.getUserName());
			ad.setCreateTime(new Date());
			ad.setStatus("1");
			saveEntity(ad);
		}else{
			ad.setCreateUserid(oldad.getCreateUserid());
			ad.setCreateUsername(oldad.getCreateUsername());
			ad.setCreateTime(oldad.getCreateTime());
			ad.setStatus("1");
			updateEntity(ad);
		}
	}
	
	@Override
	public void delAdvertisement(Advertisement ad){
		ad.setStatus("0");
		enterpriseService.updateObject(ad);
	}
}
