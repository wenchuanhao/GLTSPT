package com.cdc.sys.dict.service;

import java.util.List;

import com.cdc.sys.dict.form.SysParameterForm;
import com.cdc.sys.dict.form.SysParameterTypeForm;
import com.cdc.sys.dict.model.SysParameter;
import com.cdc.sys.dict.model.SysParameterType;
import com.trustel.common.ItemPage;

public interface ISysParameterService
{
	/***************************************************************************
	 * 参数类型管理
	 **************************************************************************/

	/**
	 * 添加参数类型
	 * 
	 * @param parameterType
	 *            参数类型 SystemParameterType
	 */
	public void addParameterType(SysParameterType parameterType) throws Exception;

	/**
	 * 修改参数类型
	 * 
	 * @param parameterType
	 *            参数类型 SystemParameterType
	 */
	public void updateParameterType(SysParameterType parameterType) throws Exception;

	/**
	 * 删除参数类型
	 * 
	 * @param sptId
	 *            参数类型的id String
	 */
	public void deleteParameterType(String sptId) throws Exception;

	/**
	 * 查询参数类型
	 * 
	 * @return 参数类型集合
	 */

	public List<SysParameterType> queryParameterType() throws Exception;

	/**
	 * 分页查询系统参数类型
	 * 
	 * @param form
	 * @return
	 */
	public ItemPage queryParameterType(SysParameterTypeForm form) throws Exception;

	/**
	 * 查询参数类型 按参数id
	 * 
	 * @param parameterType
	 *            参数类型 SystemParameterType
	 * @return 参数类型
	 */
	public SysParameterType queryParameterType(String sptId) throws Exception;

	/***************************************************************************
	 * 参数管理
	 **************************************************************************/

	/**
	 * 添加参数
	 * 
	 * @param parameterType
	 *            参数 SystemParameterType
	 */
	public void addParameter(SysParameter parameter) throws Exception;

	/**
	 * 修改参数
	 * 
	 * @param parameter
	 *            参数 SystemParameter
	 */
	public void updateParameter(SysParameter parameter) throws Exception;

	/**
	 * 删除参数
	 * 
	 * @param spId
	 *            参数的id String
	 */
	public void deleteParameter(String spId) throws Exception;

	/**
	 * 查询参数
	 * 
	 * @return 参数集合
	 */

	public List<SysParameter> queryParameter() throws Exception;
	/**
	 * 根据参数类型id查找参数
	 * @param sptId
	 * @return
	 * @throws Exception
	 */
	public List<SysParameter> queryParameterBySptId(String sptId) throws Exception;

	/**
	 * 分页查询系统参数
	 * 
	 * @param form
	 * @return
	 */
	public ItemPage queryParameter(SysParameterForm form) throws Exception;

	/**
	 * 查询参数 按参数id
	 * 
	 * @param spId
	 *            参数的id String
	 * @return 参数
	 */
	public SysParameter queryParameter(String spId) throws Exception;
	
	/**
	 * 按照参数类型编码查找
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysParameterType queryParameterTypeByCode (String code)throws Exception;

	/**
	 * 按照参数编码查找
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysParameter  queryParameterByCode(String code)throws Exception;
	
	public SysParameter queryParameterByName(String parameterName) throws Exception;

	public SysParameterType queryParameterTypeByName(String parameterTypeName) throws Exception;
	
	public List<SysParameter> checkParameter(String parameterTypeIds) throws Exception;

	/**
	 * 查詢數據字典
	 * @param form
	 * @return
	 */
	public ItemPage queryDict(SysParameterForm form);

	/**
	 * 数据字典提交
	 * @param parameterType
	 * @param parameter
	 * @throws Exception 
	 */
	public String submit(SysParameterType parameterType,SysParameterForm parameter) throws Exception;

	/**
	 * 删除参数数据，逻辑删除，最后一下连带类型一齐删除
	 * @param sysParameter
	 * @throws Exception 
	 */
	public void delDict(String id) throws Exception;

	public void importParameterType(List<SysParameterType> list_type, List<SysParameter> list_parameter) throws Exception;

}
