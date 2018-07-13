package com.cdc.system.core.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 角色人员工具类
 * @author WEIFEI
 * @date 2016-8-17 上午10:25:31
 */
public class SysRoleUserUtils {
	
	/**
	 * 当前要用户是否存在参数角色
	 * @author WEIFEI
	 * @date 2016-8-17 上午10:31:05
	 * @param userId
	 * @param roleCode
	 * @return
	 * @throws Exception	Boolean
	 */
	public static Boolean existsRole(String userId,String roleCode)throws Exception {
		
		if (roleCode == null || roleCode.equals("")) {
			return false;
		}
		
		JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringHelper.getBean("jdbcTemplate");
		
		String sql = 
		"select count(*)	count													" +
		"  from sys_role_user sru													" +
		" where sru.role_id in (select role_id								" +
		"                         from 	sys_role r										" +
		"                        where r.role_code = '"+roleCode+"')	" +
		"   and user_id = '"+userId+"'										";
		
		Integer count = 0;
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			count = Integer.valueOf(((BigDecimal)map.get("COUNT")).toString());
		}
		
		if (count != 0) {
			return true;
		}
		return false;
	}
}
