package com.cdc.dc.datacenter.test;

import com.cdc.util.DesUtil;

public class DesUtilTest {

	public static void main(String[] args) throws Exception {
		String str = DesUtil.encrypt("password", "Awe89gzS");
		System.out.println("加密结果："+str);
		String str2 = DesUtil.decrypt(str, "Awe89gzS");
		System.out.println("解密结果："+str2);
	}
}
