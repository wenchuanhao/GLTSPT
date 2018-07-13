package org.trustel.service.id;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

public class IDGenerator implements IdentifierGenerator, Configurable {

	private int fixLength = 8;

	public synchronized Serializable generate(SessionImplementor session,
			Object entity) throws HibernateException {
		return getNextSequence((Session) session, entity.getClass()
				.getSimpleName());
	}

	private String getNextSequence(Session session, String name) {
		return SequenceUtils.getNextCode(session, name, fixLength);
	}

	public void configure(Type type, Properties prop, Dialect dialect)
			throws MappingException {
		String len = prop.getProperty("length");
		if (len != null && !len.trim().equals(""))
			try {
				fixLength = Integer.parseInt(len.trim());
			} catch (Exception e) {
				e.printStackTrace();

			}
	}
}