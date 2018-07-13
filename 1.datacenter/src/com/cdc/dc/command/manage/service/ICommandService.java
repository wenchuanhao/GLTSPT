package com.cdc.dc.command.manage.service;

import java.util.List;

import model.sys.entity.SysRole;
import model.sys.entity.SysUser;

import org.hibernate.SQLQuery;
import org.trustel.service.ServiceException;

import com.cdc.dc.command.manage.form.CommandForm;
import com.cdc.dc.command.manage.model.CommandFlows;
import com.cdc.dc.command.manage.model.CommandFolder;
import com.cdc.dc.command.manage.model.CommandInfo;
import com.cdc.dc.command.manage.model.CommandMaterials;
import com.cdc.dc.command.manage.model.CommandMoborg;
import com.cdc.dc.command.manage.model.CommandQks;
import com.cdc.dc.command.manage.model.CommandWorks;
import com.cdc.dc.project.fxk.form.FxkForm;
import com.cdc.dc.rules.model.RulesFileUpload;
import com.trustel.common.ItemPage;

public interface ICommandService {

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
	 * 指令管理首页
	 * @param command
	 * @return
	 */
	ItemPage findCommand(CommandForm command);
	/**
	 * 根据支撑名称查询支撑详情
	 * @param supportorgName
	 * @return
	 */
	public List<Object[]> searchSupportorg(String supportorgName);
	/**
	 * 根据合同编号查询合同信息
	 * @param code
	 * @return
	 */
	public List<Object[]> searchProjectByCode(String code);
	/**
	 * 保存或更新工作类指令
	 * A1，A2，B，C
	 * @param cw
	 * @param sysUser 
	 */
	public void saveOrUpdateCommandWorks(CommandWorks cw, SysUser sysUser);
	/**
	 * 施工过程资料
	 * @param cw
	 * @param sysUser
	 */
	public void saveOrUpdateCommandMaterials(CommandMaterials cw, SysUser sysUser);
	/**
	 * 请款类指令
	 * @param cw
	 * @param sysUser
	 */
	public void saveOrUpdateCommandQks(CommandQks cw, SysUser sysUser);
	/**
	 * 根据外键查询指令信息
	 * @param worksId
	 * @return
	 */
	public CommandInfo queryCommandInfoByForid(String worksId);
	
	/**
	 * 获取全部合同
	 * @return
	 */
	public List<Object[]> queryCommandInfoAll();
	/**
	 * 根据合同名查询项目名
	 * @param name
	 * @return
	 */
	public List<Object[]> queryorgName(String orgName); 
	/**
	 * 作废
	 * @param cdinfo
	 * @param sysUser
	 */
	public void delCommandInfo(CommandInfo cdinfo, SysUser sysUser);
	/**
	 * 根据id查询流转信息
	 * @param id
	 * @return
	 */
	public List<CommandFlows> queryCommandFlowsById(String id);
	/**
	 * 设置归档信息及流程
	 * @param cdfolder
	 * @param sysUser
	 */
	public void setCommandFolder(CommandFolder cdfolder, SysUser sysUser);
	/**
	 * 根据状态查询最新的归档信息
	 * @param commandId
	 * @return
	 */
	public CommandFolder queryLeastCommandFolderById(String commandId,String status);
	/**
	 * 撤销归档
	 * @param cdinfo
	 * @param sysUser
	 */
	public void undoCommandFolder(CommandInfo cdinfo, SysUser sysUser);
	/**
	 * 根据归档id查询扫描附件
	 * @param folderId
	 * @return
	 */
	public List<RulesFileUpload> queryRulesFileUploadById(String folderId);
	/**
	 * 获取当前用户指令相关角色
	 * @param sysUser 
	 * @return
	 */
	public List<SysRole> getCommandRoles(SysUser sysUser);
	
	/**
	 * 根据号码查询用户信息
	 * @param mobile
	 * @return
	 */
	public SysUser querySysUserByMobile(String mobile);
	
	/**
	 * 根据支撑单位名称是否存在保存
	 * @param supportorgName
	 * @param sysUser
	 * @return
	 */
	public void saveOrNotSupportorg(String supportorgName, SysUser sysUser);
	/**
	 * 根据号码查询用户 组织 架构绑定关系
	 * @param mobile
	 * @param orgId 
	 * @return
	 */
	public CommandMoborg queryCommandMoborgByMobile(String mobile, String orgId);
	/**
	 * 根据号码查询绑定的组织架构
	 * @param mobile
	 * @return
	 */
	public List<CommandMoborg> queryCommandMoborgListByMobile(String mobile);
	/**
	 * 打印操作，未流转变流转中
	 * @param cdinfo
	 */
	public void printCommandInfo(CommandInfo cdinfo);
	/**
	 * 前端接收文档
	 * @param cdfolder
	 * @param sysUser
	 * @return
	 */
	public String setCommandReceive(CommandInfo cdinfo, SysUser sysUser,String mobile);
	/**
	 * 根据指令编号查询指令信息
	 * @param commandNum
	 * @return
	 */
	public CommandInfo queryCommandInfoByNum(String commandNum);
	/**
	 * 接收新文档分页查询
	 * @param command
	 * @param sysUser
	 * @return
	 */
	public ItemPage receiveNewDoc(CommandForm command, String mobile,String userid);
	/**
	 * 关联查询投资项目
	 * @param code
	 * @return
	 */
	public List<Object[]> searchProByCode(String code);
	/**
	 * 根据编码查询A类，B类，C类指令
	 * @param code
	 * @return
	 */
	public List<Object[]> searchCommandNum(String code);


}
