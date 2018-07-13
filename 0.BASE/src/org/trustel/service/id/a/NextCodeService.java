package org.trustel.service.id.a;

import java.sql.Connection;

import org.hibernate.Session;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.trustel.service.jdbc.JdbcService;

public class NextCodeService implements INextCodeService {
	private Session session;

	public NextCodeService(Session session) {
		this.session = session;
	}

	public long getNextCode(String sequenceName, int minValue, long maxValue)
			throws Exception {
		Connection conn = null;
		long value = minValue;
		try {
			conn = SessionFactoryUtils.getDataSource(
					session.getSessionFactory()).getConnection();

			JdbcService service = new JdbcService();
			service.setConnection(conn);
			value = service._getNextCode(sequenceName, minValue, maxValue);
		} finally {
			DataSourceUtils.releaseConnection(conn, SessionFactoryUtils
					.getDataSource(session.getSessionFactory()));
		}
		return value;
	}
}
