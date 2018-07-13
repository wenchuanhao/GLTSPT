package org.trustel.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cache.ReadWriteCache.Item;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.trustel.service.common.IORTransform;
import org.trustel.service.form.IPageQueryForm;
import org.trustel.service.hibernate.HibernateService;
import org.trustel.service.jdbc.ExecuteResult;
import org.trustel.service.jdbc.ICallableStatementGetter;
import org.trustel.service.jdbc.ICallableStatementSetter;
import org.trustel.service.jdbc.IJdbcService;
import org.trustel.service.jdbc.JdbcService;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IField;
import org.trustel.service.sql.a.IModifiableQuery;
import org.trustel.service.sql.a.IQuery;
import org.trustel.util.DBSegmentHelper;

import com.trustel.common.ItemPage;

public class EnterpriseService extends HibernateService implements
		IEnterpriseService {

	public ItemPage findSqlToVo(String sql, List conditions, IPageQueryForm form, Class cls) {
		QueryRunner qr = new QueryRunner();
		String pageSQL = DBSegmentHelper.buildOraclePagableSQL(sql.toString(), (form.getPageIndex() - 1)*form.getPageSize(), form.getPageSize());
		List result = new ArrayList();
		try {
			result = qr.query(getSessionFactory().getCurrentSession().connection() ,pageSQL.toString() ,new BeanListHandler(cls),conditions.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long total = getRecordCountSql(sql,conditions);
		return new ItemPage(result, total, form.getPageIndex(), form.getPageSize());
	}
	
	private IJdbcService getJdbcService() {
		final JdbcService ret = new JdbcService();
		Connection connection = DataSourceUtils
				.getConnection(SessionFactoryUtils
						.getDataSource(getSessionFactory()));
		ret.setConnection(connection);
		ret.setMaxRows(getMaxRows());
		return ret;

	}

	private void releaseJdbcService(IJdbcService jdbcService) {
		if (jdbcService != null)
			DataSourceUtils.releaseConnection(jdbcService
					.getCurrentConnection(), SessionFactoryUtils
					.getDataSource(getSessionFactory()));
	}

	public ExecuteResult _call(String callSQL, ICallableStatementSetter setter,
			ICallableStatementGetter getter) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._call(callSQL, setter, getter);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public ExecuteResult _execute(final String SQL, final List<IField> values)
			throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._execute(SQL, values);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public ExecuteResult _execute(final String SQL) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._execute(SQL);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public long _getNextCode(final String name, final int minValue,
			final long maxValue) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._getNextCode(name, minValue, maxValue);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public long _getRecordCount(final String SQL, final List<ICondition> values)
			throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._getRecordCount(SQL, values);
		} finally {
			releaseJdbcService(jdbcService);
		}

	}

	public long _getRecordCount(final String SQL) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._getRecordCount(SQL);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public List<?> _query(final IORTransform transform, final IQuery query,
			final int skip, final int maxRows) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._query(transform, query, skip, maxRows);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public ItemPage _query(final IORTransform transform, final IQuery query,
			final IPageQueryForm form) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._query(transform, query, form);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public List<?> _query(final IORTransform transform, final IQuery query)
			throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._query(transform, query);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public List<?> _query(final IORTransform transform, final String SQL,
			final List<ICondition> values, final int skipSize, final int maxRows)
			throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService
					._query(transform, SQL, values, skipSize, maxRows);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public List<?> _query(final IORTransform transform, final String SQL,
			final List<ICondition> values, final int skipSize)
			throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._query(transform, SQL, values, skipSize);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public ItemPage _query(final IORTransform transform, final String SQL,
			final List<ICondition> conditions, final long total,
			final IPageQueryForm form) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._query(transform, SQL, conditions, total, form);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public List<?> _query(final IORTransform transform, final String SQL,
			final List<ICondition> values) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._query(transform, SQL, values);
		} finally {
			releaseJdbcService(jdbcService);
		}

	}

	public long _update(final IModifiableQuery query) throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._update(query);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public long _update(final List<IModifiableQuery> queries)
			throws ServiceException {
		final IJdbcService jdbcService = getJdbcService();
		try {
			return jdbcService._update(queries);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	public void setMaxRows(final int maxRows) {
		final IJdbcService jdbcService = getJdbcService();
		try {
			jdbcService.setMaxRows(maxRows);
		} finally {
			releaseJdbcService(jdbcService);
		}
	}

	/**
	 * @deprecated
	 */
	public Connection getCurrentConnection() {
		return null;
	}

}
