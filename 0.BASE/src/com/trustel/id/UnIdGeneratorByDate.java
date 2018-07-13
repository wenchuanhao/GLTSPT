package com.trustel.id;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.trustel.common.SerialNo;

/*
 * 创建日期 2005-6-5
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

/**
 * @author Administrator
 *
 * UNID生成器(根据当前时间)
 */
public class UnIdGeneratorByDate implements IdentifierGenerator {

	/* （非 Javadoc）
	 * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
	 */
	public Serializable generate(SessionImplementor arg0, Object arg1)
		throws HibernateException {

		return SerialNo.getUNID();
	}

}
