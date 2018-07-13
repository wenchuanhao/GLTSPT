package com.trustel.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 * 表结构读取类
 * <p>
 * <li> 用户可选择在实例化对象时提供数据库连接或者在调用时提供数据库连接
 * <li> 取数据库表信息实现过程：
 * <li> 1. 调用getInstance()生成一个对象，如果是多次使用，建议初始化对象时提供数据库连接，否则可在调用具体方法时提供数据库连接
 * <li> 2. 调用getTableTypes取出支持的表类型名称(TABLE、VIEW、SYNONYM等)，如果已确定类型，可省略此步，在后续调用中直接输入常数
 * <li> 3. 调用getSchames()，取出所有的schema名称，如果已知schema名称可省略此步
 * <li> 4. 调用getTableNamesByType取出特定schema下的数据库对象名
 * <li> 5. 调用getTableByName()或者getColumnsByTable()取出数据库表字段信息
 */
public class Reader {
	/**
	 * 表
	 */
	public static final String TABLE = "TABLE";

	/**
	 * 视图
	 */
	public static final String VIEW = "VIEW";

	/**
	 * 同义词
	 */
	public static final String SYNONYM = "SYNONYM";

	/**
	 * 数据库连接
	 * <p>
	 * <li> 用户可在创建类实例时提供数据库连接，则调用方法时无须再提供数据库连接；
	 * <li> 用户也可在创建实例时不提供数据库连接，则调用方法时必须提供数据库连接
	 */
	private Connection conn;

	public static Reader getInstance() throws RuntimeException {
		return new Reader();
	}

	public static Reader getInstance(Connection conn) throws RuntimeException {
		Reader reader = null;

		if (conn == null)
			throw new RuntimeException("connection is null");

		reader = new Reader(conn);

		return reader;
	}

	private Reader() {

	}

	private Reader(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 取数据库schema
	 * 
	 * @return schema名列表
	 * @throws RuntimeException
	 */
	public List<String> getSchemas() throws RuntimeException {
		return getSchemas(conn);
	}

	/**
	 * 取数据库schema
	 * 
	 * @param conn
	 *            数据库连接
	 * @return schema名列表
	 * @throws RuntimeException
	 */
	public List<String> getSchemas(Connection conn) throws RuntimeException {
		List<String> names = new ArrayList<String>();

		if (conn == null)
			throw new RuntimeException("connection is null");

		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet results = metadata.getSchemas();
			while (results.next()) {
				names.add(results.getString("TABLE_SCHEM"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQLException: " + e.getMessage()
					+ " at reader.getSchemas");
		}

		return names;
	}

	/**
	 * 取数据库支持的 SQL类型
	 * 
	 * @return SQL类型名列表
	 * @throws RuntimeException
	 */
	public List<String> getAllTypes() throws RuntimeException {
		return getAllTypes(conn);
	}

	/**
	 * 取数据库支持的 SQL类型
	 * 
	 * @param conn
	 *            数据库连接
	 * @return SQL类型名列表
	 * @throws RuntimeException
	 */
	public List<String> getAllTypes(Connection conn) throws RuntimeException {
		List<String> names = new ArrayList<String>();

		if (conn == null)
			throw new RuntimeException("connection is null");

		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet results = metadata.getTypeInfo();
			while (results.next()) {
				names.add(results.getString("TYPE_NAME"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQLException: " + e.getMessage()
					+ " at reader.getAllTypes");
		}

		return names;
	}

	/**
	 * 取数据库表类型
	 * 
	 * @return 表类型名列表 (TABLE、VIEW、SYNONYM等)
	 * @throws RuntimeException
	 */
	public List<String> getTableTypes() throws RuntimeException {
		return getTableTypes(conn);
	}

	/**
	 * 取数据库表类型
	 * 
	 * @param conn
	 *            数据库连接
	 * @return 表类型名列表 (TABLE、VIEW、SYNONYM等)
	 * @throws RuntimeException
	 */
	public List<String> getTableTypes(Connection conn) throws RuntimeException {
		List<String> names = new ArrayList<String>();

		if (conn == null)
			throw new RuntimeException("connection is null");

		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet results = metadata.getTableTypes();
			while (results.next()) {
				names.add(results.getString("TABLE_TYPE"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQLException: " + e.getMessage()
					+ " at reader.getCatalogs");
		}

		return names;
	}

	/**
	 * 根据schema及类型取表名
	 * 
	 * @param schema
	 *            数据库schema (为空或者%时取出所有schema下的表)
	 * @param type
	 *            类型 (TABLE、VIEW、SYNONYM等，为空时取出所有的表)
	 * @return 表名列表
	 * @throws RuntimeException
	 */
	public List<Table> getTablesByType(String schema, String type)
			throws RuntimeException {
		return getTablesByType(conn, schema, type);
	}

	/**
	 * 根据schema及类型取表名
	 * 
	 * @param conn
	 *            数据库连接
	 * @param schema
	 *            数据库schema (为空或者%时取出所有schema下的表)
	 * @param type
	 *            类型 (TABLE、VIEW、SYNONYM等，为空时取出所有的表)
	 * @return 表名列表
	 * @throws RuntimeException
	 */
	public List<Table> getTablesByType(Connection conn, String schema, String type)
			throws RuntimeException {
		List<Table> tables = new ArrayList<Table>();

		if (conn == null)
			throw new RuntimeException("connection is null");

		try {
			DatabaseMetaData metadata = conn.getMetaData();
			String[] types = type == null ? new String[] {TABLE, VIEW, SYNONYM } : new String[] { type };

			ResultSet results = metadata.getTables(null, schema, "%", types);
			while (results.next()) {
				tables.add(new Table(results.getString("TABLE_SCHEM"), results.getString("TABLE_TYPE"), results.getString("TABLE_NAME")));
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQLException: " + e.getMessage()
					+ " at reader.getTablesByType");
		}

		return tables;
	}

	/**
	 * 根据数据库表名取表字段信息
	 * 
	 * @param schemaName schema名称
	 * @param tableName
	 *            数据库表名
	 * @return 字段信息列表
	 * @throws RuntimeException
	 */
	public List<Column> getColumnsByTable(String schemaName, String tableName) throws RuntimeException {
		return getColumnsByTable(conn, schemaName, tableName);
	}

	/**
	 * 根据数据库表名取表字段信息
	 * 
	 * @param conn
	 *            数据库连接
	 * @param schemaName schema名称
	 * @param tableName
	 *            数据库表名
	 * @return 字段信息列表
	 * @throws RuntimeException
	 */
	public List<Column> getColumnsByTable(Connection conn, String schemaName, String tableName)
			throws RuntimeException {
		List<Column> columns = new ArrayList<Column>();

		if (conn == null)
			throw new RuntimeException("connection is null");

		try {
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet results = metadata.getColumns(null, schemaName, tableName, "%");
			while (results.next()) {
				columns.add(new Column(results.getString("COLUMN_NAME"),
						results.getInt("DATA_TYPE"), results
								.getString("TYPE_NAME"), results
								.getInt("COLUMN_SIZE"), results
								.getInt("DECIMAL_DIGITS"), results
								.getInt("NULLABLE"), results
								.getString("COLUMN_DEF"), results
								.getString("REMARKS")));
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQLException: " + e.getMessage()
					+ " at reader.getColumnsByTable");
		}

		return columns;
	}

}