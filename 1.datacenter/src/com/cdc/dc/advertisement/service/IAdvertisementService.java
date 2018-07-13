package com.cdc.dc.advertisement.service;

import org.trustel.service.ServiceException;

import model.sys.entity.SysUser;

import com.cdc.dc.advertisement.form.AdvertisementForm;
import com.cdc.dc.advertisement.model.Advertisement;
import com.trustel.common.ItemPage;

public interface IAdvertisementService {

	public ItemPage findAdvertisement(AdvertisementForm ad) throws Exception;

	public void saveOrUpdateAdvertisement(Advertisement ad, SysUser sysUser);

	Object getEntity(Class clazz, String id);

	public void saveEntity(Object item) throws ServiceException;

	public void updateEntity(Object item);

	public void delAdvertisement(Advertisement ad);

}
