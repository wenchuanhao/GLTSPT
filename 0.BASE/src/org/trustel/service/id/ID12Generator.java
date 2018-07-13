package org.trustel.service.id;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ID12Generator implements IdentifierGenerator {

	public synchronized Serializable generate(SessionImplementor session,
			Object entity) throws HibernateException {
		return getNextSequence((Session) session, entity.getClass()
				.getSimpleName());
	}

	private String getNextSequence(Session session, String name) {
		return SequenceUtils.getNextCode(session, name, 12);
	}

}
