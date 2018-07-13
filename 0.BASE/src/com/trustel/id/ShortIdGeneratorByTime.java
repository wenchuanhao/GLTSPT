/*
 * Created on Aug 9, 2005
 *
 */
package com.trustel.id;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.trustel.common.SerialNo;

/**
 * @author Administrator
 *
 * ID生成器
 */
public class ShortIdGeneratorByTime implements IdentifierGenerator {

	/* (non-Javadoc)
	 * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
	 */
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {

		return SerialNo.getShortSerial();
	}

}
