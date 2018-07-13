package com.cdc.dc.purchase.service;

import java.util.List;
import java.util.Map;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysUser;

import com.cdc.dc.purchase.form.PurchaseDataColumnForm;
import com.cdc.dc.purchase.form.PurchaseForm;
import com.cdc.dc.purchase.model.Purchase;
import com.cdc.dc.purchase.model.PurchaseColumn;
import com.cdc.dc.purchase.model.PurchaseRole;
import com.trustel.common.ItemPage;

/**
 * 
 * @author WEIFEI
 * @date 2016-7-26 上午11:22:51
 */
public interface IPurchaseService {
	
	public ItemPage findJsxm(Purchase purchase) throws Exception;
	
	public Purchase findJsxmById(String id) throws Exception;

	public Purchase save(Purchase purchase) throws Exception;
	
	public Purchase update(Purchase purchase) throws Exception;

	public void delete(String id) throws Exception;
	
	public ItemPage queryPurchase(PurchaseForm purchaseForm) throws Exception;
	
	public PurchaseColumn savePurchaseColumn(PurchaseColumn purchaseColumn) throws Exception;
	
	public List<Map<String, Object>> queryPurchaseColumn() throws Exception;
	
	public void coverPurchaseByExcel(final List<String> list,List data) throws Exception;
	public void uncoverPurchaseByExcel(List data) throws Exception;
	
	public void addPurchase(final List<String[]> list, final String account) throws Exception;
	
	public  List<String> getTableMetadatas(String tableName);

	public Map<String, Object> queryPurchaseByid(String id);

	public void deletePurchaseByid(String id);

	public void updatePurchaseByid(final String id, final List<String[]> list, final String account);

	/**
	 * 采购项目明细表
	 * @param purchaseForm
	 * @param flag 是否取消
	 * @return
	 */
	public ItemPage findItem(PurchaseForm purchaseForm, boolean flag);

	public Long queryTimeBetweenDate(String beginTime, String endTime);
	
	public int queryPurchaseByItemid(String itemId);
	
	/**
	 * 采购角色管理
	 */
	public ItemPage queryPurchaseRole(PurchaseForm purchaseForm);
	
	public void SavePurchaseRole(PurchaseForm purchaseForm, String orgIds, String orgNames);

	public void delPurchaseRoleByid(String id);

	public ItemPage queryDataColumn(PurchaseDataColumnForm form) throws Exception;

	public PurchaseRole queryPurchaseRoleByid(String id);

	public void updatePurchaseRole(PurchaseForm purchaseForm, String orgIds,String orgNames);
	
	public void delpurchaseRoleBybatch(String id);

	public List<PurchaseRole> queryPurchaseRoleByAcount(String manageAcount);

	public void deleteDataColumn(PurchaseColumn purchaseColumn);
	
	Object getEntity(Class clazz, String id);
	
	public  List<String> getTableDatas(String columnName, String tableName);

	public List<SysOrganization> queryAllColumnC(String username);

	public void addUserRoleByCode(SysUser user, String string) throws Exception;

}
