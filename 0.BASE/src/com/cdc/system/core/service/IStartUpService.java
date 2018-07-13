package com.cdc.system.core.service;

import java.util.List;

import model.sys.entity.SysRole;
import model.sys.entity.SysRolePrivilges;

import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;

/**
 * 
 * 
 * @Description:
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2013-1-25 下午6:07:35
 * @UpdateRemark:
 * @Version: V1.0
 */
public interface IStartUpService {

	public List<SysParameterType> loadParamType() throws Exception;

	public List<SysParameter> loadParam() throws Exception;

	public List<?> loadModule() throws Exception;

	public List<SysRolePrivilges> loadRolePrivilges() throws Exception;

	public List<SysRole> loadRole() throws Exception;


}
