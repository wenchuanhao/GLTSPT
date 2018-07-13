package com.cdc.system.core.cache;

import java.util.List;

import com.cdc.sys.dict.model.SysParameter;

public final class SysParamHelper {

	private final static DataCache cache = DataCache.getInstance();

	private SysParamHelper() {

	}


	/**
	 * 根据参数类型获取参数列表
	 * 
	 * @param paramTypeCode
	 *            参数类型编码
	 * @return
	 */
	public static List<SysParameter> getSysParamListByParamTypeCode(String paramTypeCode) {
		return cache.getParameters().get(paramTypeCode);
	}
	/**
	 * 根据参数编码获取参数信息
	 * 
	 * @param paramCode
	 *            参数编码
	 * @return
	 */
	public static SysParameter getSysParamByCode(String paramCode) {
		List<SysParameter> parameters = cache.getSysParameters();
		for (SysParameter sysParameter : parameters) {
			if (sysParameter.getParameterCode().equals(paramCode))
				return sysParameter;
		}
		return null;
	}
	
	public static SysParameter getSysParamByCode(String paramTypeCode,String paramCode) {
		List<SysParameter> parameters = getSysParamListByParamTypeCode(paramTypeCode);
		if(parameters != null){
			for (SysParameter sysParameter : parameters) {
				if (paramCode.equals(sysParameter.getParameterCode()))
					return sysParameter;
			}
		}
		return new SysParameter();
	}
}
