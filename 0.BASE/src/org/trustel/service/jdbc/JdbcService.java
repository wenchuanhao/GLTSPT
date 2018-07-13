package org.trustel.service.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.trustel.service.ServiceException;
import org.trustel.service.common.IORTransform;
import org.trustel.service.form.IPageQueryForm;
import org.trustel.service.sql.a.DefaultColumn;
import org.trustel.service.sql.a.IColumn;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IField;
import org.trustel.service.sql.a.IModifiableQuery;
import org.trustel.service.sql.a.IQuery;
import org.trustel.util.SimpleHashMap;

import com.trustel.common.ItemPage;

public class JdbcService implements IJdbcService {

	private Connection conn;

	protected final Log logger = LogFactory.getLog(getClass());

	private int maxRows = 100000;

	// private Object[] field2Array(List<Field> values) {
	// Object[] items = new Object[values.size()];
	// for (int i = 0; i < items.length; i++) {
	// items[i] = values.get(i).getValue();
	// }
	// return items;
	// }

	/**
	 * 执行存储过程
	 * 
	 * @param callSQL
	 * @param inputs
	 * @param outputs
	 * @throws SQLException
	 */
	public ExecuteResult _call(String callSQL, ICallableStatementSetter setter,
			ICallableStatementGetter getter) throws ServiceException {
		CallableStatement cs = null;
		ExecuteResult ret = new ExecuteResult();
		try {
			cs = conn.prepareCall(callSQL);
			if (setter != null)
				setter.setParameterValues(cs);

			ret.ret = cs.execute();
			getter.readOutParameter(cs);
			ret.updateCount = cs.getUpdateCount();
			ResultSet rs = cs.getResultSet();
			try {
				ret.sets = resultSet2List(rs, getter, 0);
			} finally {
				closeResultSet(rs);
			}
			return ret;

		} catch (Exception e) {
			logger.error("_call-->" + e.getMessage());
			logger.error("  " + callSQL);
			throw new ServiceException(e.getMessage(), e);

		} finally {
			closeStatement(cs);
		}

	}

	protected void closeResultSet(ResultSet rs) throws ServiceException {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				logger.error("closeResultSet-->" + e.getMessage());
				throw new ServiceException(e.getMessage(), e);
			}
		}
	}

	// private Object[] cond2Array(List<Condition> values) {
	// Object[] items = new Object[values.size()];
	// for (int i = 0; i < items.length; i++) {
	// items[i] = values.get(i).getValue();
	// }
	// return items;
	// }

	protected void closeStatement(Statement stmt) throws ServiceException {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				logger.error("closeStatement-->" + e.getMessage());
				throw new ServiceException(e.getMessage(), e);
			}
		}
	}

	private int[] _execute(final List<IModifiableQuery> queries)
			throws SQLException {
		if (queries == null || queries.size() == 0)
			return null;
		String SQL = queries.get(0).getPrepareSQL();
		logger.info("execute-->" + SQL);
		int[] ret = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL);
			for (int i = 0; i < queries.size(); i++) {
				prepareStatement(ps, queries.get(i).getValues());
				ps.addBatch();
			}
			ret = ps.executeBatch();
			closeStatement(ps);
			logger.info("execute-->" + ret.toString());
		} finally {
			closeStatement(ps);

		}

		return ret;
	}

	/**
	 * 执行存SQL(insert/delete/update/exec /call 无参数)
	 * 
	 * @param sql
	 *            SQL
	 * @param transaction
	 *            是否添加事务支持语句
	 * @return 影响记录行数
	 * @throws SQLException
	 */
	public ExecuteResult _execute(String SQL) throws ServiceException {
		ExecuteResult ret = new ExecuteResult();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL);
			ret.ret = ps.execute();
			ret.updateCount = ps.getUpdateCount();
		} catch (SQLException e) {
			ret.ret = false;
			logger.error("_execute-->" + e.getMessage());
			logger.error("  " + SQL);
			throw new ServiceException("execute(SQL)-->" + e.getMessage(), e);
		}
		closeStatement(ps);
		return ret;

	}

	/**
	 * 执行查询(Insert /delete/update )
	 * 
	 * @param SQL
	 *            推荐使用UpdateBuilder/InsertBuilder构建
	 * @param values
	 *            推荐使用UpdateBuilder/InsertBuilder构建
	 * @return 执行对象
	 * @throws SQLException
	 */
	public ExecuteResult _execute(String SQL, final List<IField> values)
			throws ServiceException {
		PreparedStatement ps = null;
		ExecuteResult ret = new ExecuteResult();
		try {
			ps = conn.prepareStatement(SQL);
			prepareStatement(ps, values);
			if (ps.execute()) {
				ret.updateCount = ps.getUpdateCount();
				ret.ret = true;
			}
		} catch (SQLException e) {
			logger.error("_execute-->" + e.getMessage());
			logger.error("  " + SQL);
			throw new ServiceException(e.getMessage(), e);
		}
		return ret;
	}

	private long _executeA(final List<IModifiableQuery> queries)
			throws ServiceException {
		int[] ret = null;
		try {
			ret = _execute(queries);
		} catch (SQLException e) {
			logger.error("_executeA-->" + e.getMessage());
			throw new ServiceException(e.getMessage(), e);
		}
		long r = 0;
		for (int i = 0; i < ret.length; i++)
			r = ret[i] + r;
		return r;
	}

	protected List<IColumn> getColumnInformations(ResultSetMetaData data)
			throws SQLException {
		List<IColumn> arr = new ArrayList<IColumn>();
		for (int i = 1; i <= data.getColumnCount(); i++) {
			IColumn column = new DefaultColumn(data.getColumnName(i),
					data.getColumnType(i), i, data.getColumnLabel(i));
			arr.add(column);
		}
		return arr;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public long _getNextCode(final String name, final int minValue,
			final long maxValue) throws ServiceException {
		// getnextcode ret,name,min,max
		NextCodeGetter nextCode = new NextCodeGetter(minValue);
		_call("{call getnextcode(?,?,?,?) }", new ICallableStatementSetter() {

			public void setParameterValues(CallableStatement cs)
					throws SQLException {
				cs.registerOutParameter(1, Types.BIGINT);
				cs.setString(2, name);
				cs.setInt(3, minValue);
				cs.setLong(4, maxValue);
			}
		}, nextCode);
		return nextCode.getNextCode();
	}

	/**
	 * 获取记录行数
	 * 
	 * @param SQL
	 * @return 行数
	 */
	public long _getRecordCount(String SQL) throws ServiceException {
		return _getRecordCount(SQL, new ArrayList<ICondition>());
	}

	/**
	 * 获取记录行数
	 * 
	 * @param SQL
	 * @param values
	 *            参考Condition定义
	 * @return 记录数
	 */
	public long _getRecordCount(String SQL, List<ICondition> values)
			throws ServiceException {
		ResultSet rs = null;
		try {
			rs = getResultSet(SQL, values, 1);
		} catch (SQLException e) {
			logger.error("_getRecordCount" + e.getMessage());
			logger.error("  " + SQL);
			throw new ServiceException(e.getMessage(), e);
		}
		int iCount = 0;
		if (rs != null) {
			try {
				if (rs.next())
					iCount = rs.getInt(1);
				rs.close();
				rs = null;
			} catch (Exception e) {
				logger.error("_getRecordCount" + e.getMessage());
				logger.error("  " + SQL);
				throw new ServiceException(e.getMessage(), e);
			}

		}
		return iCount;
	}

	protected ResultSet getResultSet(String SQL, int maxRows)
			throws SQLException {
		ResultSet rs = null;
		Statement st = conn.createStatement();
		if (maxRows >= 0)
			st.setMaxRows(maxRows);
		rs = st.executeQuery(SQL);

		logger.info("DefaultConnection.getDataset with nonvalues");
		logger.info(SQL);
		logger.info("OK!");
		return rs;
	}

	protected ResultSet getResultSet(String SQL, List<ICondition> values,
			int maxRows) throws SQLException {
		if (values == null || values.size() < 1)
			return getResultSet(SQL, maxRows);
		// if (maxresults < maxRows)
		// throw new OverflowException();
		ResultSet rsTemp = null, rsRet = null;
		logger.info("DefaultConnection.getDataset");
		logger.info(SQL);
		CallableStatement query = null;
		query = conn.prepareCall(SQL);
		if (values != null)
			for (int i = 0; i < values.size(); i++) {
				ICondition value = values.get(i);
				switch (value.getType()) {
				case Types.INTEGER:
					query.setInt(i + 1, ((Integer) value.getValue()).intValue());
					break;
				case Types.SMALLINT:
					query.setShort(i + 1,
							((Integer) value.getValue()).shortValue());
					break;
				case Types.VARCHAR:
				case Types.CHAR:
					query.setString(i + 1, (String) value.getValue());
					break;
				case Types.FLOAT:
					query.setFloat(i + 1,
							((Float) value.getValue()).floatValue());
					break;
				case Types.DECIMAL:
				case Types.DOUBLE:
					query.setDouble(i + 1,
							((Double) value.getValue()).doubleValue());
					break;
				case Types.DATE:
					Date d = (Date) value.getValue();
					java.sql.Date d1 = new java.sql.Date(d.getTime());
					// java.sql.Date s = java.sql.Date.valueOf(DateUtils
					// .format(d, "yyyy-MM-dd HH:mm", ""));
					query.setDate(i + 1, d1);
					break;
				// case Types.l:
				// query.setDate(i, (Date) value.getValue());

				}
			}
		if (maxRows > 0)
			query.setMaxRows(maxRows);
		rsTemp = query.executeQuery();
		rsRet = rsTemp;
		logger.info("OK!");
		return rsRet;
	}

	private void prepareStatement(PreparedStatement ps, List<IField> values)
			throws SQLException {
		for (int j = 1; j <= values.size(); j++) {
			IField item = values.get(j - 1);
			switch (item.getType()) {
			case Types.INTEGER:
				ps.setInt(j, item.toInteger(0));
				break;
			case Types.SMALLINT:
				ps.setShort(j, item.toShort(0));
			case Types.VARCHAR:
			case Types.CHAR:
				ps.setString(j, item.toString());
				break;
			case Types.FLOAT:
				ps.setFloat(j, item.toFloat(0));
				break;
			case Types.DECIMAL:
			case Types.DOUBLE:
				ps.setDouble(j, item.toDouble(0));
			}
		}
	}

	public List<?> _query(IORTransform transform, IQuery query)
			throws ServiceException {
		return _query(transform, query.getHQL(), query.getValues());
	}

	/**
	 * 根据查询构造器及数据转换器查询指定行之后的数据
	 * 
	 * @param transform
	 *            OR转换器(对象-关系转换器)
	 * @param query
	 *            查询构造器
	 * @param skip
	 *            跳过记录行数 skip>=0
	 * @param maxRows
	 *            最多返回 记录数 实际记录数为maxRow-skip
	 * @return OR转换器返回指定对象列表
	 * @throws SQLException
	 */
	public List<?> _query(IORTransform transform, IQuery query, int skip,
			int maxRows) throws ServiceException {
		maxRows = maxRows > 0 ? maxRows : getMaxRows();
		maxRows = maxRows > getMaxRows() ? getMaxRows() : maxRows;

		return _query(transform, query.getSQL(), query.getValues(), skip,
				maxRows);
	}

	/**
	 * 分页查询
	 * 
	 * @param transform
	 *            OR转换器(对象-关系转换器)
	 * @param query
	 *            查询构造器
	 * @param form
	 *            分页请求模型
	 * @return 分页数据
	 * @throws SQLException
	 */
	public ItemPage _query(IORTransform transform, IQuery query,
			IPageQueryForm form) throws ServiceException {
		long total = _getRecordCount(query.getTotalSQL(), query.getValues());
		return _query(transform, query.getSQL(), query.getValues(), total, form);
	}

	/**
	 * 查询
	 * 
	 * @param transform
	 *            参考ORTransform定
	 * @param query
	 *            参考CommonQuery定义
	 * @param form
	 *            参考PageQueryForm定义
	 * @return 数据集(OR转换器返回对象集) 最大行数受maxRows配置参数限制
	 */
	public List<?> _query(IORTransform transform, String SQL,
			List<ICondition> values) throws ServiceException {
		return _query(transform, SQL, values, 0, maxRows);

	}

	/**
	 * 查询
	 * 
	 * @param transform
	 *            参考ORTransform定
	 * @param query
	 *            参考CommonQuery定义
	 * @param form
	 *            参考PageQueryForm定义
	 * @param skipSize
	 *            返回指 定行数数据skipSize>=0
	 * @return 数据集(OR转换器返回对象集) 最大行数受maxRows配置参数限制
	 */
	public List<?> _query(IORTransform transform, String SQL,
			List<ICondition> values, int skipSize) throws ServiceException {
		return _query(transform, SQL, values, skipSize, maxRows);
	}

	/**
	 * 查询
	 * 
	 * @param transform
	 *            参考ORTransform定
	 * @param query
	 *            参考CommonQuery定义
	 * @param form
	 *            参考PageQueryForm定义
	 * @param skipSize
	 *            返回指 定行数数据skipSize>=0
	 * @param maxRows
	 *            最多返回N行记录,不包括skipSize指定的记录行
	 * @return 数据集(OR转换器返回对象集)
	 * @throws SQLException
	 * @throws Exception
	 */
	protected List<?> resultSet2List(ResultSet rs, IORTransform transform,
			int skipSize) throws SQLException {
		List<Object> list = new ArrayList<Object>();
		logger.info("query-->skip record number:" + skipSize);
		if (rs != null) {
			List<IColumn> columns;
			columns = getColumnInformations(rs.getMetaData());

			int index = 0;
			while (rs.next()) {

				if (index >= skipSize)
					list.add(transform.transform(index + 1 - skipSize,
							row2Map(rs, columns)));
				index++;
			}
			logger.info("query-->dataset.size:" + index);
			rs.close();
		}
		return list;
	}

	public List<?> _query(IORTransform transform, String SQL,
			List<ICondition> values, int skipSize, int maxRows)
			throws ServiceException {
		maxRows = maxRows > getMaxRows() ? getMaxRows() : maxRows;

		ResultSet rs = null;
		try {
			rs = getResultSet(SQL, values, maxRows);
		} catch (SQLException e) {
			logger.error("" + e.getMessage());
			throw new ServiceException(e.getMessage(), e);
		} finally {
			// closeResultSet(rs);
		}
		logger.info("query-->skip record number:" + skipSize);
		try {
			return resultSet2List(rs, transform, skipSize);
		} catch (SQLException e) {
			logger.error("" + e.getMessage());
			throw new ServiceException(e.getMessage(), e);
		} finally {
			closeResultSet(rs);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param transform
	 *            OR转换器(对象-关系转换器)
	 * @param SQL
	 *            标准SQL查询(SELECT)
	 * @param conditions
	 *            查询条件,通常由querybuilder构建,可以为空
	 * @param total
	 *            SQL对应数据集记录数
	 * @param form
	 *            分页请求模型
	 * @return 分页记录/Items成员为OR转换器返回对象
	 * @throws SQLException
	 */
	public ItemPage _query(IORTransform transform, String SQL,
			List<ICondition> conditions, long total, IPageQueryForm form)
			throws ServiceException {
		int skipSize = form.isExport() ? form.getSkipSize() : (form
				.getPageIndex() - 1) * form.getPageSize();
		int maxRows = form.isExport() ? form.getSkipSize() : form.getPageSize()
				* form.getPageIndex();
		List<?> items = _query(transform, SQL, conditions, skipSize, maxRows);

		return new ItemPage(items, total, form.getPageIndex(),
				form.getPageSize());
	}

	/**
	 * 
	 * @param row
	 *            当前行
	 * @param columns
	 *            数据集字段定义 参考getColumnInformations
	 * @return 数据行(哈唏表)
	 */
	protected SimpleHashMap row2Map(ResultSet row, List<IColumn> columns) {
		SimpleHashMap map = new SimpleHashMap();
		try {
			for (int i = 0; i < columns.size(); i++) {
				IColumn column = columns.get(i);
				String fieldName = column.getName();
				if (!fieldName.equalsIgnoreCase(column.getAlais()))
					fieldName = column.getAlais();

				switch (column.getType()) {
				case Types.TIMESTAMP:
					map.put(fieldName, row.getTimestamp(fieldName));
					break;
				case Types.DATE:
					map.put(fieldName, row.getDate(fieldName));
					break;
				case Types.NUMERIC:
					map.put(fieldName, row.getDouble(fieldName));
					break;
				default:
					map.put(fieldName, row.getObject(fieldName));
				}

			}
		} catch (Exception e) {
			logger.error("row2Event");
			throw new ServiceException(e.getMessage(), e);
		}
		return map;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * 插入数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义
	 * @param transaction
	 *            是否添加事务控制语句
	 * @return 影响记录行数
	 * @throws SQLException
	 */
	public long _update(List<IModifiableQuery> queries) throws ServiceException {
		return _executeA(queries);
	}

	public long _update(IModifiableQuery query) throws ServiceException {
		ExecuteResult ret = _execute(query.getPrepareSQL(), query.getValues());
		if (ret.ret)
			return ret.updateCount;
		else
			return -1;
	}

	public Connection getCurrentConnection() {
		return conn;
	}
	
	
}
