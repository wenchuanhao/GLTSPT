package com.cdc.dc.rules.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.XMLGregorianCalendar;

import model.sys.entity.SysOrganization;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysUser;

import org.trustel.service.ServiceException;

import com.cdc.dc.metadata.yuan.model.YuanTableColumnManage;
import com.cdc.dc.rules.form.RulesForm;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.cdc.dc.rules.model.RulesFlowInfo;
import com.cdc.dc.rules.model.RulesFlowInsactor;
import com.cdc.dc.rules.model.RulesInfo;
import com.cdc.dc.rules.model.RulesType;
import com.trustel.common.ItemPage;
/**
 * 制度接口业务
 * @author ZENGKAI
 * @date 2016-04-07 09:58:29
 */
public interface IRulesService {

	/**
	 * 新增实体
	 * @param item
	 * @throws ServiceException
	 */
	public void saveEntity(Object item)throws ServiceException;
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
	 * 更新或保存实体
	 * 判断依据，是否有ID，主键值
	 * @param item
	 */
	public void saveOrUpdateEntity(Object item);
	/**
	 * 根据主键和类查询数据
	 * @param clazz
	 * @param id
	 * @return
	 */
	Object getEntity(Class clazz, String id);
	
	/**
	 * 删除实体
	 * @param clazz
	 * @param obj
	 */
	void delEntity(Object obj);
	
	/**
	 * 设置通用的Attribute，制度等级、制度分类
	 * @param request
	 */
	public void setCommonAttribute(HttpServletRequest request);
	
	/**
	 * 更新或新增文件信息
	 * @param fileUpload
	 * @return
	 */
	String saveOrUpdateFileInfo(RulesFileUpload fileUpload);

	/**
	 * 查询附件列表
	 * @param fileTempId
	 * @param type
	 * @return
	 */
	List<RulesFileUpload> queryRulesFileList(String fileTempId, String type);
	/**
	 * 根据部门简拼获取部门编号
	 * @param string
	 * @return
	 */
	public String getRulesId(String org);
	
	/**
	 * 查询费用类型是否重复
	 * @param typeName
	 * @return
	 */
	public String checkCostType(String typeName);
	
	/**
	 * 查询工作日
	 * @param id
	 * @return
	 */
	public RulesType queryWoryDay(String id);
	
	/**
	 * 根据业务类型查询制度分类
	 * @param bustype 业务类型
	 * @param parentTypeId 父节点编号
	 * @return
	 */
	public List<RulesType> queryAllByBusType(String bustype,String parentTypeId);
	/**
	 * 根据文档主键查询文档父信息
	 * 制度相关文档，知识库，模板等
	 * @param rulesId
	 * @return
	 */
	public List<RulesFileUpload> queryRulesFileUploadByRulesId(String rulesId,String type);
	/**
	 * 根据系统配置类型，查询类型配置值
	 * @param bustype
	 * @return
	 */
	public String getConfigStr(String code,String def);
	/**
	 * 根据系统配置类型，查询类型长整形配置值
	 * @param code
	 * @param def
	 * @return
	 */
	public Long getConfigLong(String code,Long def);
	
	
	/**
	 * 分页查询草稿箱列表
	 * @param rulesForm
	 * @return
	 */
	public ItemPage queryDraftRulesInfoItemPage(RulesForm rulesForm);
	/**
	 * 制度提交
	 * @param rulesInfo
	 */
	public void submit(RulesInfo rulesInfo,SysUser sysUser);
	/**
	 * 分页查询我的制度列表
	 * @param rulesForm
	 * @return
	 */
	public ItemPage queryMineRulesInfoItemPage(RulesForm rulesForm);
	/**
	 * 分页查询待办等制度审核
	 * @param rulesForm
	 * @return
	 */
	public ItemPage queryRemainRulesInfoItemPage(RulesForm rulesForm,String insactorsHandelStatus,String insactorsType);
	
	/**
	 * 已办、已阅查询
	 * @param rulesForm
	 * @param rulesflowinsactorshandelstatusDone
	 * @param rulesflowinsactorstypeDb
	 * @return
	 */
	public ItemPage queryDonesRulesInfoItemPage(RulesForm rulesForm,String insactorsHandelStatus,String insactorsType);
	/**
	 * 制度发布
	 * @param rulesInfo
	 * @param sysUser
	 */
	public void releaseRemain(RulesInfo rulesInfo, SysUser sysUser);
	/**
	 * 制度修订
	 * @param rulesInfo
	 * @param sysUser
	 */
	public void reviseRules(RulesInfo rulesInfo, SysUser sysUser);
	/**
	 * 制度废止
	 * @param rulesInfo
	 * @param sysUser
	 * @param handelOpinion 
	 */
	public void repealRules(RulesInfo rulesInfo, SysUser sysUser, String handelOpinion);
	/**
	 * 制度提醒页面
	 * @param rulesForm
	 * @return
	 */
	public ItemPage queryRemindRulesInfoItemPage(RulesForm rulesForm);
	/**
	 * 制度状态批量变更
	 */
	public void updateRulesFlowInsactorTimeoutStatus();
	/**
	 * 已阅操作
	 * @param rulesInfo
	 * @param sysUser
	 */
	public void releaseRead(RulesInfo rulesInfo, SysUser sysUser);
	/**
	 * 制度退回操作
	 * @param rulesInfo
	 * @param sysUser
	 * @param handelOpinion
	 */
	public void returnRemain(RulesInfo rulesInfo, SysUser sysUser,String handelOpinion);
	/**
	 * 制度查询
	 * @param rulesForm
	 * @return
	 */
	public ItemPage queryAllRulesInfoItemPage(RulesForm rulesForm,String roleStr);
	/**
	 * 流程信息列表查询
	 * @param rulesId
	 * @return
	 */
	public ItemPage queryRulesFlowInfoList(String rulesId);
	
	/**
	 * 查询流程
	 * @param rulesInfoid
	 * @param flowLink
	 * @param handelStatus
	 * @param handelResult
	 * @return
	 */
	public RulesFlowInfo queryRulesFlowInfoStatus(String rulesInfoid,String flowLink,String handelStatus,String handelResult);
	
	public RulesFlowInsactor queryRulesFlowInsactorStatus(String rulesInfoid,String insactorsType,String handelStatus);
	/**
	 * 文档管理查询文档列表
	 * @param rulesForm
	 * @return
	 */
	public ItemPage queryDocumentsItemPage(RulesForm rulesForm);
	/**
	 * 更新文档类型、业务类型、关联ID
	 * @param fileTempId
	 * @param fileId
	 * @param types
	 * @param busTypes
	 */
	public void updateFileTempId(String fileTempId, String fileId, String types, String busTypes);
	
	/**
	 * 用户查询
	 * @param userValue
	 * @return
	 */
	public List<Object[]> searchUserSuggest(String userValue);
	/**
	 * 删除文档及子文件
	 * @param fileUpload
	 */
	public void delRulesFile(RulesFileUpload fileUpload,SysUser visitor);
	/**
	 * 添加制度类型
	 * @param rulesType
	 * @param sysUser
	 */
	public void addRulesType(RulesType rulesType, SysUser sysUser,String type);
	/**
	 * 
	 * @param rulesForm
	 * @return
	 */
	public List<Object[]> queryChartSItemPage(String sql,RulesForm rulesForm);
	/**
	 * 导出查询
	 * @param rulesForm
	 * @return
	 */
	public List<Object[]> queryExportRulesInfoItemPage(RulesForm rulesForm,String roleStr);
	/**
	 * shanchu 
	 * @return
	 */
	public List<RulesFileUpload> queryDelRulesFileUpload();
	/**
	 * 更新配置ID删除配置信息
	 * @param typeId
	 */
	public void delRulesType(String typeId);
	/**
	 * 根据制度名称查询制度信息，已发布
	 * @param rulesName
	 * @return
	 */
	public RulesInfo queryRulesInfoByName(String rulesName,String... status);
	/**
	 * 根据组织编号查询父组织
	 * @param organizationId
	 * @return
	 */
	public SysOrganization queryParentOrg(String organizationId);
	/**
	 * 根据用户ID查询角色
	 * @param userId
	 * @param userId 
	 * @return
	 */
	public SysRoleUser getRoleUsersByUserId(String roleId, String userId);
	/**
	 * 根据角色编码查询角色
	 * @param roleCode
	 * @return
	 */
	public SysRole querySysRoleByCode(String roleCode);
	/**
	 * 设置权限公用方法
	 * @param request
	 */
	public String setUserRoles(HttpServletRequest request,RulesForm rulesForm);
	/**
	 * OA待办记录接口
	 * @param ACTION	处理类型  1:新增 2:删除 3:完成 已完成的任务不能在删除
	 * @param sysUser	上一处理人，当前登录用户
	 * @param SUBJECT	任务主题
	 * @param TASKID	任务ID
	 * @param TASKTYPE	任务类型  1:待办  2:待阅
	 * @param TASKURL	处理任务的URL
	 * @param toUserList	任务所有者
	 * @param WARNINGTIME	任务提醒时间
	 * @param recordId	业务记录ID
	 * @param handelstatus	处理状态
	 */
	public void sendManagetask(int action, SysUser sysUser, String subject,List<String> taskId, int taskType, String taskURL, List<SysUser> toUserList,
			XMLGregorianCalendar warningTime, String recordId,String handelstatus);
	/**
	 * 
	 * @param string
	 * @return
	 */
	public List<SysUser> getUserByMenu(String menuCode);
	/**
	 * 查询数据源检验配置
	 * @param length
	 * @param tableName
	 * @return
	 */
	public List<YuanTableColumnManage> queryManageByTableName(int length, String tableName);
}
