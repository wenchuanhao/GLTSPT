package org.trustel.service.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.trustel.service.ServiceException;
import org.trustel.service.common.IORTransform;
import org.trustel.service.form.IPageQueryForm;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IField;
import org.trustel.service.sql.a.IModifiableQuery;
import org.trustel.service.sql.a.IQuery;

import com.trustel.common.ItemPage;

public interface IJdbcService {
	/**
	 * 获取记录行数
	 * 
	 * @param SQL
	 * @return 行数
	 */
	public long _getRecordCount(String SQL) throws ServiceException;

	/**
	 * 获取记录行数
	 * 
	 * @param SQL
	 * @param values
	 *            参考Condition定义
	 * @return 记录数
	 */
	public long _getRecordCount(String SQL, List<ICondition> values)
			throws ServiceException;

	public List<?> _query(IORTransform transform, IQuery query)
			throws ServiceException;

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
			int maxRows) throws ServiceException;

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
			IPageQueryForm form) throws ServiceException;

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
			List<ICondition> values) throws ServiceException;

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
			List<ICondition> values, int skipSize) throws ServiceException;

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
	 */

	public List<?> _query(IORTransform transform, String SQL,
			List<ICondition> values, int skipSize, int maxRows)
			throws ServiceException;

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
			throws ServiceException;

	/**
	 * 
	 * @param maxRows
	 *            JDBC查询最大返回记录行数
	 */
	public void setMaxRows(int maxRows);

	public ExecuteResult _call(String callSQL, ICallableStatementSetter setter,
			ICallableStatementGetter getter) throws ServiceException;

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
	public ExecuteResult _execute(String SQL) throws ServiceException;

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
			throws ServiceException;

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
	public long _update(List<IModifiableQuery> queries) throws ServiceException;

	public long _update(IModifiableQuery query) throws ServiceException;

	/**
	 * 
	 * @param name
	 *            序号名称
	 * @param minValue
	 *            最小值
	 * @param maxValue
	 *            最大值
	 * @return 下一个序号
	 */
	public long _getNextCode(String name, int minValue, long maxValue)
			throws ServiceException;
	
	public Connection getCurrentConnection();
}
