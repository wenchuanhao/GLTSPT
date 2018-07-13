package com.cdc.dc.cooperation.model;

import java.util.Date;
import java.util.List;

import model.sys.entity.SysUser;

import com.cdc.dc.cooperation.service.ICooperationService;
import com.cdc.system.core.util.SpringHelper;

/**
 * 用户-角色-数据源/业务类型配置表
 * @author ZENGKAI
 * @date 2016-05-19 10:38:29
 */

public class CooperationTypeRoleUser  implements java.io.Serializable {

	/**
	 * 配置ID
	 */
	private String typeRoleUserId;
	/**
	 * 数据源/业务报表类型ID
	 */
     private String datasourceId;
     /**
      * 角色ID 
      */
     private String roleId;
     /**
      * 配置用户
      */
     private String userId;
     /**
      * 创建时间
      */
     private Date createTime;
     /**
      * 创建人ID
      */
     private String createUserid;
     /**
      * 创建人名称
      */
     private String createUsername;
     /**
      * 状态
      */
     private String status;
     

    // Constructors

    /** default constructor */
    public CooperationTypeRoleUser() {
    }

	/** minimal constructor */
    public CooperationTypeRoleUser(String typeRoleUserId) {
        this.typeRoleUserId = typeRoleUserId;
    }
    
    /** full constructor */
    public CooperationTypeRoleUser(String typeRoleUserId, String datasourceId, String roleId, String userId, Date createTime, String createUserid, String createUsername, String status) {
        this.typeRoleUserId = typeRoleUserId;
        this.datasourceId = datasourceId;
        this.roleId = roleId;
        this.userId = userId;
        this.createTime = createTime;
        this.createUserid = createUserid;
        this.createUsername = createUsername;
        this.status = status;
    }

   
    // Property accessors

    public String getUserList() {
    	ICooperationService cooperationService = (ICooperationService)SpringHelper.getBean("cooperationService");
    	List<SysUser> list = cooperationService.fingSysUserListById(this.datasourceId,this.roleId);
    	String str = "";
    	if(list != null && list.size() > 0){
    		for (int i = 0; i < list.size(); i++) {
				if(i == 0){
					str += list.get(i).getUserName();
				}else{
					str += "，"+list.get(i).getUserName();
				}
			}
    	}
		return str;
	}

    
    public String getTypeRoleUserId() {
        return this.typeRoleUserId;
    }
    
    public void setTypeRoleUserId(String typeRoleUserId) {
        this.typeRoleUserId = typeRoleUserId;
    }

    public String getDatasourceId() {
        return this.datasourceId;
    }
    
    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserid() {
        return this.createUserid;
    }
    
    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUsername() {
        return this.createUsername;
    }
    
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   








}