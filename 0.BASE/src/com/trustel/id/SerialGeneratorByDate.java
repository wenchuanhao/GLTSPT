package com.trustel.id;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.trustel.common.SerialNo;

public class SerialGeneratorByDate implements IdentifierGenerator {

	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return SerialNo.getSerialforDB();
	}

}
