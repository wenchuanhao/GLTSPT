package com.cdc.sys.service;

import java.util.ArrayList;
import java.util.List;

import model.sys.entity.RRoleGroup;
import model.sys.entity.RUserGroup;
import model.sys.entity.SysRole;
import model.sys.entity.SysRoleGroup;
import model.sys.entity.SysRoleUser;
import model.sys.entity.SysRolegroupRole;
import model.sys.entity.SysUser;
import model.sys.entity.UserRoleGroup;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.ServiceException;
import org.trustel.service.sql.QueryAction;
import org.trustel.service.sql.QueryBuilder;

import com.cdc.sys.form.SysRoleForm;
import com.cdc.sys.form.SysRoleGroupForm;
import com.trustel.common.ItemPage;

public class SysRoleGroupService implements ISysRoleGroupService {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public void saveEntity(Object item) throws ServiceException {
		enterpriseService.save(item);
	}
	
	public ItemPage querySysRoleGroup(SysRoleGroupForm form,String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleGroup.class);
		query.where("roleGroupcode", form.getRoleGroupcode(), QueryAction.LIKE);
		query.where("roleGroupname", form.getRoleGroupname(), QueryAction.LIKE);
		query.orderBy("createTime", false);
		ItemPage itemPage = enterpriseService.query(query, form);
		return itemPage;
	}
	public String getGroupIdsByUserIds(String userId) throws Exception{
		QueryBuilder query1=new QueryBuilder("RUserGroup ug,SysRoleUser ru,RRoleGroup rg");
		query1.select("distinct rg.roleId");
		query1.where("ru.userId",userId);
		query1.where("ug.roleId=ru.roleId");
		query1.where("rg.roleGroupId=ug.roleGroupId");
		//拥有权限的角色组Id集合。。。
		List<String> list=(List<String>) enterpriseService.query(query1, 0);
		String roleIds="";
		for (String roleId : list) {
			roleIds += "'" + roleId + "',";
		}
		if (!roleIds.equals("")) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		} else {
			roleIds = "1";
		}
		QueryBuilder query=new QueryBuilder();
		query.where("select distinct roleGroupId from RRoleGroup where roleId in("+roleIds+")");
		//拥有这些权限的角色组Id集合
		List<String> groupList=(List<String>) enterpriseService.query(query, 0);
		
		String groups = "";
		for(String group : groupList){
			List<String> groupAllIds=getStrRoleAndRoleGroupR(group);
			if(list.containsAll(groupAllIds)){
				groups += "'" + group + "',";
			}
		}
		if (!groups.equals("")) {
			groups = groups.substring(0, groups.length() - 1);
		} else {
			groups = "1";
		}
		return groups;
	}
//	@SuppressWarnings("unchecked")
//	public String getGroupIdsByRoleIds(String UserId) throws Exception{
//		QueryBuilder query1=new QueryBuilder("RUserGroup u,RRoleGroup r");
//		query1.select("r.roleId");
//		query1.where("u.userId",UserId);
//		query1.where("u.roleGroupId=r.roleGroupId");
//		List<String> list=(List<String>) enterpriseService.query(query1, 0);
//		String roleIds = "";
//		for (String roleId : list) {
//			
//			roleIds += "'" + roleId + "',";
//		}
//		if (!roleIds.equals("")) {
//			roleIds = roleIds.substring(0, roleIds.length() - 1);
//		} else {
//			roleIds = "1";
//		}
//		QueryBuilder query=new QueryBuilder();
//		query.where("select distinct roleGroupId from RRoleGroup where roleId in("+roleIds+")");
//		List<String> groupList=(List<String>) enterpriseService.query(query, 0);
//		String groups = "";
//		for(String group : groupList){
//			List<String> groupAllIds=getStrRoleAndRoleGroupR(group);
//			if(list.containsAll(groupAllIds)){
//				groups += "'" + group + "',";
//			}
//		}
//		if (!groups.equals("")) {
//			groups = groups.substring(0, groups.length() - 1);
//		} else {
//			groups = "1";
//		}
//		return groups;
//	}
	
	public String addSysRoleGroup(SysRoleGroup sysRoleGroup) throws Exception {
		return enterpriseService.save(sysRoleGroup).toString();

	}

	public void updateSysRoleGroup(SysRoleGroup sysRoleGroup) throws Exception {
		enterpriseService.updateObject(sysRoleGroup);

	}

	public void deleteSysRoleGroup(String sysRoleGroupid) throws Exception {
		QueryBuilder query = new QueryBuilder(SysRoleGroup.class);
		query.where("roleGroupid", sysRoleGroupid);
		enterpriseService.delete(query);
	}

	public SysRoleGroup getSysRoleGroupById(String sysRoleGroupid)
			throws Exception {
		return (SysRoleGroup) enterpriseService.getById(SysRoleGroup.class,
				sysRoleGroupid);
	}

	@SuppressWarnings("unchecked")
	public List<RRoleGroup> getRoleAndRoleGroupR(String roleGroupId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(RRoleGroup.class);
		query.where("roleGroupId", roleGroupId);
		return (List<RRoleGroup>) enterpriseService.query(query, 0);
	}

	@SuppressWarnings("unchecked")
	public List<String> getStrRoleAndRoleGroupR(String roleGroupId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(RRoleGroup.class);
		query.select("roleId");
		query.where("roleGroupId", roleGroupId);
		return (List<String>) enterpriseService.query(query, 0);
	}
	public List<?> getRole(String roleGroupId) throws Exception {
		QueryBuilder query = new QueryBuilder("RRoleGroup rg,SysRole r");
		query.where("rg.roleGroupId", roleGroupId);
		query.where("rg.roleId=r.roleId");
		return (List<?>) enterpriseService.query(query, 0);
	}

	public void addRRoleGroup(String roleGroupId, String roles)
			throws Exception {
		String[] roleTemp = roles.split(",");
		List<RRoleGroup> list = new ArrayList<RRoleGroup>();
		for (String roleId : roleTemp) {
			if (roleId != null && !"".equals(roleId))
				list.add(new RRoleGroup(roleGroupId, roleId));
		}
		enterpriseService.save(list);
	}

	public void deleteRRoleGroup(String roleGroupId) throws Exception {
		QueryBuilder query = new QueryBuilder(RRoleGroup.class);
		query.where("roleGroupId", roleGroupId);
		enterpriseService.delete(query);
	}

	@SuppressWarnings("unchecked")
	public List<RUserGroup> listRUserGroup(String roleGroupId) throws Exception {
		QueryBuilder query = new QueryBuilder(RUserGroup.class);
		query.where("roleGroupId", roleGroupId);
		return (List<RUserGroup>) enterpriseService.query(query, 0);
	}

	@SuppressWarnings("unchecked")
	public List<RUserGroup> listRUserGroupByU_RG(String roleGroupId,
			String userId) throws Exception {
		QueryBuilder query = new QueryBuilder(RUserGroup.class);
		query.where("roleGroupId", roleGroupId);
		query.where("userId", userId);
		return (List<RUserGroup>) enterpriseService.query(query, 0);
	}

	public void deleteRUserGroupByU_RG(String roleGroupId, String roleId)
			throws Exception {
		QueryBuilder query = new QueryBuilder(RUserGroup.class);
		query.where("roleGroupId", roleGroupId);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
	}

	public void addRUserGroup(RUserGroup rUserGroup) throws Exception {
		enterpriseService.save(rUserGroup);
	}
	public void addUserGroup(UserRoleGroup userRoleGroup) throws Exception {
		enterpriseService.save(userRoleGroup);
	}

	@SuppressWarnings("unchecked")
	public List<?> listRoleGroupByUserId(String userId) throws Exception {
		QueryBuilder builder = new QueryBuilder(RUserGroup.class);
		builder.select("roleGroupId");
		List<String> roleGroupId = (List<String>) enterpriseService.query(
				builder, 0);
		if (roleGroupId != null) {
			String roleGroupIds = "";
			for (String roleGroupIdstr : roleGroupId) {
				roleGroupIds += "'" + roleGroupIdstr + "',";
			}
			roleGroupIds = roleGroupIds.substring(0, roleGroupIds.length() - 1);
			QueryBuilder query = new QueryBuilder(
					"SysRoleGroup srg ,SysRole sr ,RRoleGroup rg");
			query.where("rg.roleGroupId in(" + roleGroupIds + ")");
			query.where("srg.roleGroupid = rg.roleGroupId and sr.roleId = rg.roleId");
			List<?> list = enterpriseService.query(query, 0);
			return list;
		}
		return null;
	}
	public void deleteRoleVisitorBySrId(String srId, String userId) throws Exception{
		QueryBuilder query = new QueryBuilder(SysRoleUser.class);
		query.where("roleId", srId, QueryAction.EQUAL);
		query.where("userId", userId, QueryAction.EQUAL);
		enterpriseService.delete(query);

	}
	@SuppressWarnings("unchecked")
	public List<UserRoleGroup> queryUserGroup(String userId) throws Exception{
		QueryBuilder query = new QueryBuilder(UserRoleGroup.class);
		query.where("currentUserId", userId, QueryAction.EQUAL);
		return (List<UserRoleGroup>) enterpriseService.query(query,0);
	}
	@SuppressWarnings("unchecked")
	public void GroupUser( String userId, String roleGroupId,String flag) throws Exception{
		SysRoleGroup roleGroup=(SysRoleGroup) enterpriseService.getById(SysRoleGroup.class, roleGroupId);
		//用户所拥有的角色ID集合；
		QueryBuilder builder=new QueryBuilder(SysRoleUser.class);
		builder.select("roleId");
		builder.where("userId",userId,QueryAction.EQUAL);
		List<String> roles=(List<String>) enterpriseService.query(builder, 0);
		//所属角色组所拥有的角色ID集合；
		QueryBuilder rolebuilder=new QueryBuilder(RRoleGroup.class);
		rolebuilder.select("roleId");
		rolebuilder.where("roleGroupId",roleGroup.getRoleGroupid(),QueryAction.EQUAL);
		List<String> roleIdsInGroup=(List<String>) enterpriseService.query(rolebuilder, 0);
		for(String roleId:roleIdsInGroup){
			if(!roles.contains(roleId)){
				if(flag.equals("1")){
					enterpriseService.save(new SysRoleUser(userId,roleId));
				}else{
					deleteRoleVisitorBySrId(roleId,userId);
				}
			}
		}
	}

	public void undo(String roleId, String groupId) throws Exception {
		QueryBuilder query = new QueryBuilder(UserRoleGroup.class);
		query.where("roleId", roleId, QueryAction.EQUAL);
		query.where("groupId", groupId, QueryAction.EQUAL);
		enterpriseService.delete(query);
		
	}

	@Override
	public List<SysRole> querySysRoleByGroupId(String roleGroupId) {
		List<SysRole> result = new ArrayList<SysRole>();
		if(StringUtils.isEmpty(roleGroupId)){
			return result;
		}
		QueryBuilder query = new QueryBuilder("SysRolegroupRole rg,SysRole r");
		query.where("rg.roleGroupid", roleGroupId);
		query.where("rg.roleId=r.roleId");
		List<?> list = (List<?>) enterpriseService.query(query, 0);
		if(list != null && list.size() > 0 ){
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				result.add((SysRole) obj[1]);
			}
		}
		return result;
	}

	@Override
	public List<SysRole> querySysRoleNotInList(List<SysRole> list, String roleName) {
		String roleIds = "(";
		if(list != null && list.size() > 0 ){
			for (int i = 0; i < list.size(); i++) {
				if(i == 0 ){
					roleIds += "'"+list.get(i).getRoleId()+"'";
				}else{
					roleIds += ",'"+list.get(i).getRoleId()+"'";
				}
			}
		}
		roleIds += ")";
		QueryBuilder query = new QueryBuilder(SysRole.class);
		if(list != null && list.size() > 0 ){
			query.where("roleId not in " + roleIds);
		}
		query.where("roleName",roleName,QueryAction.LIKE);
		return (List<SysRole>) enterpriseService.query(query, 0);
	}

	@Override
	public void delRoleById(String roleGroupid) {
		//删除角色组信息
		QueryBuilder query = new QueryBuilder(SysRoleGroup.class);
		query.where("roleGroupid", roleGroupid);
		enterpriseService.delete(query);
		//删除角色组角色配置
		QueryBuilder query1 = new QueryBuilder(SysRolegroupRole.class);
		query1.where("roleGroupid", roleGroupid);
		enterpriseService.delete(query1);
	}

	@Override
	public String querySysRoleGroupByCodeOrName(String roleGroupid, String roleGroupcode, String roleGroupname) {
		QueryBuilder query = new QueryBuilder(SysRoleGroup.class);
		query.where("roleGroupcode", roleGroupcode);
		query.where("roleGroupid", roleGroupid, QueryAction.NOEQUAL);
		List<SysRoleGroup> list = (List<SysRoleGroup>) enterpriseService.query(query, 0);
		//角色组编号
		if(list != null && list.size() > 0){
			return "1";
		}
		
		QueryBuilder query1 = new QueryBuilder(SysRoleGroup.class);
		query1.where("roleGroupname", roleGroupname);
		query1.where("roleGroupid", roleGroupid, QueryAction.NOEQUAL);
		List<SysRoleGroup> list1 = (List<SysRoleGroup>) enterpriseService.query(query1, 0);
		//角色组名称
		if(list1 != null && list1.size() > 0){
			return "2";
		}
		return "0";
	}

	@Override
	public List<SysRoleGroup> querySysRoleGroupByGroupId(String roleGroupid) {
		QueryBuilder query = new QueryBuilder(SysRoleGroup.class);
		query.where("roleGroupid", roleGroupid, QueryAction.EQUAL);
		return (List<SysRoleGroup>) enterpriseService.query(query, 0);
	}

	@Override
	public List<SysRoleUser> getRoleUsersByGroupRoleId(String roleGroupid) {
		//查询角色组下面所有角色
		List<SysRole> list = querySysRoleByGroupId(roleGroupid);
		String roleIds = "(";
		if(list != null && list.size() > 0 ){
			for (int i = 0; i < list.size(); i++) {
				if(i == 0 ){
					roleIds += "'"+list.get(i).getRoleId()+"'";
				}else{
					roleIds += ",'"+list.get(i).getRoleId()+"'";
				}
			}
		}else{
			return null;
		}
		roleIds += ")";
		
		//查询拥有
		List<SysRoleUser> result = new ArrayList<SysRoleUser>();
		String sql = "select t.user_id from ( select t.user_id,count(1) count from  sys_role_user t,sys_user tt " +
				" where t.user_id=tt.user_id and t.role_id in "+roleIds+" group by t.user_id  ) t where t.count = " + list.size();
		
		Query query = enterpriseService.getSessions().createSQLQuery(sql.toString());
		List<String> temp = query.list();
		if(temp != null && temp.size() > 0 ){
			for (String userid : temp) {
				SysRoleUser model = new SysRoleUser();
				model.setUserId(userid);
				result.add(model);
			}
		}
		
		return result;
	}

	@Override
	public ItemPage querySysRoleUser(SysRoleForm form) {
		
		String roleIds = "";
		//根据角色组id查询配置人员
		if(form != null && StringUtils.isNotEmpty(form.getSysRoleGroupid())){
			//查询角色组下面所有角色
			List<SysRole> list = querySysRoleByGroupId(form.getSysRoleGroupid());
			roleIds = "(";
			if(list != null && list.size() > 0 ){
				for (int i = 0; i < list.size(); i++) {
					if(i == 0 ){
						roleIds += "'"+list.get(i).getRoleId()+"'";
					}else{
						roleIds += ",'"+list.get(i).getRoleId()+"'";
					}
				}
			}else{
				return null;
			}
			roleIds += ")";
			
			String sql = "select t.user_id from ( select t.user_id,count(1) count from  sys_role_user t,sys_user tt " +
					" where t.user_id=tt.user_id and t.role_id in "+roleIds+" group by t.user_id  ) t where t.count = " + list.size();
			
			List<SysUser> result = new ArrayList<SysUser>();
			ItemPage itemPage = enterpriseService.findBySql(sql, form);
			if(itemPage != null && itemPage.getItems() != null) {
				if(itemPage.getPageIndex() == 1){
					List<String> objsList = (List<String>) itemPage.getItems();
					for(String userid : objsList) {
						SysUser sys = (SysUser) enterpriseService.queryEntity(SysUser.class, userid);
						result.add(sys);
					}
					
				}else{
					List<Object[]> objsList = (List<Object[]>) itemPage.getItems();
					for(Object[] objs : objsList) {
						String userid = (String) objs[0];
						SysUser sys = (SysUser) enterpriseService.queryEntity(SysUser.class, userid);
						result.add(sys);
					}
				}
			}
			return new ItemPage(result, itemPage.getTotal(), itemPage.getPageIndex(),itemPage.getPageSize());
			
			
		}
		return null;
		
	}

	@Override
	public void delGroupRoleById(String roleGroupid, String roleId) {
		if(StringUtils.isEmpty(roleGroupid) || StringUtils.isEmpty(roleGroupid)){
			return;
		}
		QueryBuilder query = new QueryBuilder(SysRolegroupRole.class);
		query.where("roleGroupid", roleGroupid);
		query.where("roleId", roleId);
		enterpriseService.delete(query);
	}
}
