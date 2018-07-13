package com.trustel.id;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGeneratorByDB implements IdentifierGenerator {
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		String sequenceName = arg1.getClass().getName();
		String id = null;
		
		try {
			CallableStatement st = arg0.connection().prepareCall(
					"{call pro_getid (?, ?)}");
			st.setString(1, sequenceName.substring(sequenceName.lastIndexOf('.') + 1));
			st.registerOutParameter(2, java.sql.Types.VARCHAR);
			st.executeQuery();

			id = st.getString(2);
			st.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getClass().getName() + ": "
					+ e.getMessage() + " at JobQuery.getOrderId");
		}

		if (id == null || id.length() == 0)
			throw new HibernateException("get sequence by '" + sequenceName
					+ "' failed with null");
		
		return id;
	}

}
