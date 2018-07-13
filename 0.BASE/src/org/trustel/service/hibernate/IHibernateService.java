package org.trustel.service.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.trustel.service.ServiceException;
import org.trustel.service.form.IPageQueryForm;
import org.trustel.service.sql.a.ICondition;
import org.trustel.service.sql.a.IModifiableQuery;
import org.trustel.service.sql.a.IQuery;

import com.trustel.common.ItemPage;

public interface IHibernateService {

	/**
	 * 获取记录行数
	 * 
	 * @param HQL
	 * @return 行数
	 */
	public long getRecordCount(String HQL) throws ServiceException;

	/**
	 * 获取记录行数
	 * 
	 * @param HQL
	 *            可使用QueryBuilder生成
	 * @param values
	 *            参考Condition定义 可使用QueryBuilder生成
	 * @return 记录数
	 */
	public long getRecordCount(String HQL, List<ICondition> values)
			throws ServiceException;

	public long getRecordCount(IQuery query) throws ServiceException;
	public List findBySqls(String sql, IPageQueryForm form);
	public Session getSessions();
	public HibernateTemplate getHibernateTemplates();

	/**
	 * 根据ID取对象
	 * 
	 * @param classType
	 *            类类型
	 * @param id
	 * @return
	 */
	public Object getById(Class classType, Serializable id)
			throws ServiceException;
	/**
	 * 根据主键和类查询数据
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object queryEntity(Class clazz, String id);
	/**
	 * 查询并返回指定类型对象
	 * 
	 * @param query
	 *            参考CommonQuery定义 可使用QueryBuilder生成
	 * @param maxrows
	 * @return 对象集
	 */
	public List<?> query(IQuery query, int maxrows) throws ServiceException;

	/**
	 * 查询并返回指定对象集
	 * 
	 * @param query
	 *            参考 CommonQuery定义
	 * @param form
	 *            请求模型，请参考PageQueryForm定义
	 * @return 分页对象集
	 */
	public ItemPage query(IQuery query, IPageQueryForm form)
			throws ServiceException;

	/**
	 * 查询并返回指定对象集
	 * 
	 * @param query
	 *            参考 CommonQuery定义
	 * @param form
	 *            请求模型，请参考PageQueryForm定义
	 * @param recordCount
	 *            query对应的数据集行数
	 * @return 分页对象集
	 */
	public ItemPage query(IQuery query, IPageQueryForm form, long recordCount)
			throws ServiceException;

	/**
	 * 查询并返回指定对象集
	 * 
	 * @param HQL
	 *            HQL
	 * @param maxrows
	 *            最多返回记录行数
	 * @return 分页对象集
	 */
	public List<?> query(String HQL, int maxrows) throws ServiceException;

	/**
	 * 查询并返回指定对象集
	 * 
	 * @param HQL
	 * @param conditions
	 *            可使用QueryBuilder生成
	 * @param form
	 *            请求模型，请参考PageQueryForm定义
	 * @return 对象集
	 */
	public List<?> query(String HQL, List<ICondition> conditions, int maxrows)
			throws ServiceException;

	/**
	 * 分页查询
	 * 
	 * @param HQL
	 *            和数据库系统兼容的HQL
	 * @param values
	 *            参考Condition定义
	 * @param form
	 *            参考PageQueryForm定义
	 * @param recordCount
	 *            HQL对应数据集记录数
	 * @return 分页数据集
	 */
	public ItemPage query(String HQL, List<ICondition> values,
			IPageQueryForm form, long recordCount) throws ServiceException;

	/**
	 * 分页查询
	 * 
	 * @param HQL
	 * @param form
	 *            参考PageQueryForm定义
	 * @param recordCount
	 *            HQL对应数据集的记录数
	 * @return 分页数据集，ITEM中存储的是pojo实例
	 */
	public ItemPage query(String HQL, IPageQueryForm form, long recordCount)
			throws ServiceException;

	/**
	 * 
	 * 根据Query构造的SQL删除数据
	 * 
	 * @param query
	 *            参考[sql]Query定义
	 * @return 删除记录行数
	 */
	public long delete(IQuery query);

	/**
	 * 
	 * 根据SQL删除数据
	 * 
	 * @param sql
	 *            参考[sql]Query定义
	 * @return 删除记录行数
	 */
	public long delete(String sql, List<ICondition> values)
			throws ServiceException;

	/**
	 * 更新数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义
	 * @param transaction
	 *            是否添加事务控制语句
	 * @return 影响记录行数
	 */
	public long execute(List<IModifiableQuery> queries) throws ServiceException;

	/**
	 * 修改数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义 可以是InsertBuilder 或UpdateBuilder
	 * @return 影响记录行数
	 */
	public long execute(IModifiableQuery query) throws ServiceException;

	/**
	 * 执行存SQL
	 * 
	 * @param sql
	 *            SQL
	 * @return 影响记录行数
	 */
	public long execute(String sql, List<ICondition> values)
			throws ServiceException;

	/**
	 * 插入数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义
	 * @param transaction
	 *            是否添加事务控制语句
	 * @return 影响记录行数
	 */
	public long insert(List<IModifiableQuery> queries) throws ServiceException;

	/**
	 * 插入数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义
	 * @return 影响记录行数
	 */
	public long insert(IModifiableQuery query) throws ServiceException;

	/**
	 * 更新数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义
	 * @param transaction
	 *            是否添加事务控制语句
	 * @return 影响记录行数
	 */
	public long update(List<IModifiableQuery> list) throws ServiceException;

	/**
	 * 数据映射对象持久化(保存到数据库中)
	 * 
	 * @param list
	 *            数据映射对象列表(DO)(更新)
	 */
	public void updateAll(List<?> list) throws ServiceException;

	/**
	 * 数据映射对象持久化(保存到数据库中)(更新)
	 * 
	 * @param item
	 *            数据映射对象(DO)
	 */
	public void updateObject(Object item) throws ServiceException;

	/**
	 * 数据映射对象持久化(保存到数据库中)(新增)
	 * 
	 * @param list
	 *            数据映射对象列表(DO)
	 */
	public void save(List<?> list) throws ServiceException;

	/**
	 * 数据映射对象持久化(保存到数据库中)(新增)
	 * 
	 * @param item
	 *            数据映射对象(DO)
	 */
	public Serializable save(Object item) throws ServiceException;

	/**
	 * 更新数据
	 * 
	 * @param query
	 *            请参考 ModifiableQuery定义
	 * @param transaction
	 *            是否添加事务控制语句
	 * @return 影响记录行数
	 */
	public long update(IModifiableQuery query) throws ServiceException;
	
	/**
	 * 保存或者修改
	 * @date 2015-11-26 下午4:15:11
	 * @param demand
	 * @return	Demand
	 */
	public void saveOrUpdate(Object item)throws ServiceException;
	
	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param form
	 *            参考PageQueryForm定义
	 * @param recordCount
	 *            HQL对应数据集的记录数
	 * @return 分页数据集，ITEM中存储的是pojo实例
	 */
	public ItemPage findBySql(String sql, IPageQueryForm form)
			throws ServiceException;
	
	public List<String> findBySql(String string, String userId);
	
	
	/**
	 * 在query里面必须使用普通SQL来查询 分页数据
	 * @param query
	 * @param form
	 * @param useMap 是否使用map映射
	 * @return itemPage
	 */
	public ItemPage queryBySql(IQuery query, IPageQueryForm form , boolean useMap);
	
}
