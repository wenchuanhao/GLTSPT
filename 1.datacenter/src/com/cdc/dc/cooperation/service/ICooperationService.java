package com.cdc.dc.cooperation.service;

import java.util.List;
import java.util.Map;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;

import org.trustel.service.ServiceException;

import com.cdc.dc.cooperation.form.CooperationForm;
import com.cdc.dc.cooperation.model.CooperationDatasourceRecords;
import com.cdc.dc.cooperation.model.CooperationDatasourceType;
import com.cdc.dc.cooperation.model.CooperationTypeRoleUser;
import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.trustel.common.ItemPage;

public interface ICooperationService{
	/**
	 * 已审核总数
	 * @param cooperationForm
	 * @param type
	 * @param user
	 * @param map
	 * @return
	 */
	ItemPage queryMyDataSH(CooperationForm cooperationForm, SysUser user,Map map);
	
	/**
	 * 审核中总数
	 * @param cooperationForm
	 * @param type
	 * @param user
	 * @param map
	 * @return
	 */
	ItemPage queryMyDataSHZ(CooperationForm cooperationForm, SysUser user,Map map);

	/**
	 * 草稿总数
	 * @param cooperationForm
	 * @param type
	 * @param user
	 * @param map
	 * @return
	 */
	ItemPage queryMyDataCG(CooperationForm cooperationForm,  SysUser user,Map map);

	/**
	 * 修订总数
	 * @param cooperationForm
	 * @param type
	 * @param user
	 * @param map
	 * @return
	 */
	ItemPage queryMyDataXD(CooperationForm cooperationForm,  SysUser user,Map map);
	
	/**
	 * 删除数据源
	 */
	void delMyData(String id);

	/**
	 * 新增实体
	 * @param item
	 * @throws ServiceException
	 */
	public void saveEntity(Object item)throws ServiceException;
	
	public void saveEntity(List<?> list);
	
	/**
	 * 更新实体
	 * @param fileUpload
	 */
	public void updateEntity(Object item);
	
	/**
	 * 批量更新
	 * @param list
	 */
	public void updateAll(List<?> list);
	
	/**
	 * 删除实体
	 * @param clazz
	 * @param obj
	 */
	void delEntity(Object obj);
	
	void addDatasourceType(CooperationDatasourceType datasourceType,SysUser sysUser, String busTypes);

	List<CooperationDatasourceType> queryCooperationDatasourceTypeList(String busTypes, String parentDatasourceId, String status);

	void delDatasourceType(SysUser sysUser,String datasourceId);

	ItemPage queryPrivilgesItemPage(CooperationForm cooperationForm);

	/**
	 * 权限配置提交，保存
	 * @param sysUser
	 * @param datasourceId	数据源ID
	 * @param importUserId	录入员
	 * @param remainUserId	审核员
	 * @param queryUserId	查询员
	 * @param flag 是否覆盖更新
	 */
	void configureSubmit(SysUser sysUser, String datasourceId,String[] importUserId, String[] remainUserId, String[] queryUserId, String busTypes, boolean flag);

	/**
	 * 根据数据源ID，角色ID查询配置的用户列表
	 * @param datasourceId
	 * @param roleId
	 * @return
	 */
	List<SysUser> fingSysUserListById(String datasourceId, String roleId);
	
	SysRole querySysRoleByCode(String roleCode);

	/**
	 * 根据角色id，用户查询配置
	 * @param roleId
	 * @param userId
	 * @return
	 */
	List<CooperationTypeRoleUser> queryCooperationTypeRoleUserList(String roleId, String userId);

	/**
	 * 根据数据源类型ID查询列表
	 * @param datasourceIds
	 * @param datasourcestatusSave
	 * @return
	 */
	List<CooperationDatasourceType> queryCooperationDatasourceByIds(String datasourceIds, String status);

	/**
	 * 根据临时外键ID查询文件信息
	 * @param fileTempId
	 * @return
	 */
	RulesFileUpload queryRulesFileUploadByInfoId(String fileTempId);

	/**
	 * 根据录入记录查询录入结果
	 * @param datasourceRecords
	 * @param cooperationForm 
	 * @return
	 */
	public ItemPage queryReportsItemPage(CooperationDatasourceRecords datasourceRecords, CooperationDatasourceType cdt, CooperationForm cooperationForm);

	/**
	 * 根据配置信息查询表信息
	 * @param cdt
	 * @return
	 */
	public List queryReportTableVoByTableName(CooperationDatasourceType cdt);

	/**
	 * 提交或暂存方法
	 * @param datasourceRecords
	 * @param sysUser
	 */
	public void submit(CooperationDatasourceRecords datasourceRecords,SysUser sysUser);

	/**
	 * 我的数据源查询
	 * @param cooperationForm
	 * @param type TODO
	 * @param user TODO
	 * @return
	 */
	public ItemPage queryMydatasourceItemPage(CooperationForm cooperationForm, String type, SysUser user,Map map);

	/**
	 * 数据审核分页查询
	 * @param cooperationForm
	 * @return
	 */
	public ItemPage queryRemainItemPage(CooperationForm cooperationForm);

	/**
	 * 数据源审核通过
	 * @param datasourceRecords
	 * @param sysUser
	 */
	public void pastRemain(CooperationDatasourceRecords datasourceRecords,SysUser sysUser);

	/**
	 * 数据源审核不通过
	 * @param datasourceRecords
	 * @param sysUser
	 * @param handelOpinion
	 */
	public void returnRemain(CooperationDatasourceRecords datasourceRecords,SysUser sysUser, String handelOpinion, String checkboxSMS);

	/**
	 * 分页查询报表
	 * @param cooperationForm
	 * @param datasourceType 
	 * @return
	 */
	ItemPage queryReportQueryItemPage(CooperationForm cooperationForm, CooperationDatasourceType datasourceType);

	/**
	 * 我的数据源其他查询
	 * @param cooperationForm
	 * @param datasourceTypeList
	 * @return
	 */
	ItemPage queryMydatasourceItemPage(CooperationForm cooperationForm,String datasourceTypeList);

	/**
	 * 我的数据源列表查询
	 * @param cooperationForm
	 * @return
	 */
	ItemPage queryMydatasourceItemPage(CooperationForm cooperationForm,SysUser sysUser,SysRole sysRoleqImporter,SysRole	sysRoleQuery,SysRole sysRoleRemainer);

	/**
	 * 数据更新查询接口类数据源
	 * @param cooperationForm
	 * @param bustype
	 * @param status
	 * @return
	 */
	ItemPage queryCooperationDatasourceTypeItemPage(CooperationForm cooperationForm, String bustype,String status);

	/**
	 * 根据配置ID查询记录
	 * @param datasourceId
	 * @return
	 */
	CooperationDatasourceRecords querySyncDataByDatasourceId(String datasourceId);

	/**
	 * 根据sql查询图表数据
	 * @param sql
	 * @return
	 */
	List<Object[]> getSeriesDataByType(String sql);

	String queryResultBySql(String column, String table, int year);

	List<YuanTableColumnManage> queryManageByTableName(int length,String tableName);

	/**
	 * 从数据字典查询数据源明细的表名
	 * @param id 参数编码
	 * @return
	 */
	List queryTableNameByDict(String id);

	/**
	 * 根据表名查询表信息，字段，注释等
	 * @param tab
	 * @return
	 */
	List queryReportTableVoByTableName(String tab);

	/**
	 * 根据表名分页查询表数据
	 * @param tab
	 * @return
	 */
	ItemPage queryTableItemPageByTableName(String tab, CooperationForm cooperationForm);

	/**
	 * 根据 用户查询用户信息
	 * @param userName
	 * @return
	 */
	SysUser querySysUserByName(String userName);

}
