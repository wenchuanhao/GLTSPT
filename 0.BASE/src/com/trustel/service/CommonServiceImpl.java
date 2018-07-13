package com.trustel.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.trustel.common.ItemPage;

public class CommonServiceImpl extends HibernateDaoSupport implements
		CommonService {
	/**
	 * 
	 * @param items
	 * @return
	 */
	private java.util.List arrayToList(Object[] items) {
		java.util.List list = new java.util.ArrayList();
		for (int i = 0; i < items.length; i++)
			list.add(items[i]);

		return list;
	}

	/**
	 * 删除对象
	 * 
	 * @param classType
	 * @param ids
	 */
	public void delete(Class classType, java.util.List ids) {
		delete(getHibernateTemplate(), classType, ids);
	}

	/**
	 * 删除对象
	 * 
	 * @param classType
	 * @param ids
	 */
	public void delete(Class classType, Object[] ids) {
		delete(classType, arrayToList(ids));
	}

	protected void delete(Object object) {
		getHibernateTemplate().delete(object);
	}

	public void delete(Class classType, String id) {
		HibernateTemplate temp = getHibernateTemplate();

		temp.delete(temp.get(classType, id));
	}

	/**
	 * 删除对象
	 * 
	 * @param temp
	 * @param classType
	 * @param ids
	 */
	protected void delete(HibernateTemplate temp, Class classType,
			java.util.List ids) {
		delete(temp, getObjects(temp, classType, ids));
	}

	/**
	 * 删除对象
	 * 
	 * @param temp
	 * @param items
	 */
	protected void delete(HibernateTemplate temp, java.util.List items) {
		temp.deleteAll(items);
	}

	/**
	 * 批量更新/删除
	 * 
	 * @param temp
	 * @param query
	 * @return
	 */
	protected int execute(HibernateTemplate temp, String query) {
		return temp.bulkUpdate(query);
	}

	/**
	 * 批量更新/删除
	 * 
	 * @param query
	 * @return
	 */
	protected int execute(HibernateTemplate temp, String query, Object[] args) {
		return temp.bulkUpdate(query, args);
	}

	/**
	 * 批量更新/删除
	 * 
	 * @param hql
	 * @return
	 */
	public int execute(String hql) {
		return execute(getHibernateTemplate(), hql);
	}

	/**
	 * 批量更新/删除
	 * 
	 * @param query
	 * @return
	 */
	public int execute(String query, Object[] args) {
		return execute(getHibernateTemplate(), query, args);
	}

	/**
	 * 取所有对象
	 * 
	 * @param classType
	 * @param orderBys
	 *            排序字段
	 * @return
	 */
	public List getAll(Class classType, String orderBys) {
		return query("from " + getClassName(classType), null, null, orderBys);
	}

	/**
	 * 分页取所有对象
	 * 
	 * @param classType
	 *            类型
	 * @param orderBys
	 *            排序字段
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            行数
	 * @return
	 */
	public ItemPage getAll(Class classType, String orderBys, int pageNo,
			int pageSize) {
		return getPage("select count(*) from " + getClassName(classType), "from "
				+ getClassName(classType), null, null, orderBys, pageNo,
				pageSize);
	}

	/**
	 * 根据ID取对象
	 * 
	 * @param classType
	 * @param id
	 * @return
	 */
	public Object getById(Class classType, Serializable id) {
		return getById(getHibernateTemplate(), classType, id);
	}

	/**
	 * 根据ID取对象
	 * 
	 * @param temp
	 * @param classType
	 * @param id
	 * @return
	 */
	protected Object getById(HibernateTemplate temp, Class classType,
			Serializable id) {
		return temp.get(classType, id);
	}

	public List getByCondition(Class classType, Formula formula, String orderBys)
			throws RuntimeException {
		List conditions = new ArrayList();
		conditions.add(Condition.newInstance(formula));

		return query("from " + getClassName(classType), conditions, null,
				orderBys);
	}

	public ItemPage getByCondition(Class classType, Formula formula,
			String orderBys, int pageNo, int pageSize) throws RuntimeException {
		List conditions = new ArrayList();
		conditions.add(Condition.newInstance(formula));
		return getPage("select count(*) from " + getClassName(classType),
				"from " + getClassName(classType), conditions, null, orderBys,
				pageNo, pageSize);
	}

	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	/**
	 * 取类名(去掉包名)
	 * 
	 * @param type
	 *            类
	 * @return
	 */
	protected synchronized String getClassName(Class type) {
		String name = type.getName();

		return name.substring(name.lastIndexOf('.') + 1);
	}

	/**
	 * 根据ID取对象
	 * 
	 * @param temp
	 * @param classType
	 * @param ids
	 * @return
	 */
	private java.util.List getObjects(HibernateTemplate temp, Class classType,
			java.util.List ids) {
		java.util.List objects = new java.util.ArrayList();

		for (int i = 0; i < ids.size(); i++) {
			Object object = getById(temp, classType, (Serializable) ids.get(i));
			if (object == null)
				throw new RuntimeException("can not find " + classType + " by "
						+ ids.get(i));

			objects.add(object);
		}

		return objects;
	}

	/**
	 * 保存对象
	 * 
	 * @param temp
	 * @param item
	 * @return
	 */
	protected Serializable save(HibernateTemplate temp, Object item) {
		return temp.save(item);
	}

	/**
	 * 保存对象
	 * 
	 * @param item
	 * @return 记录主键
	 */
	public Serializable save(Object item) {
		return save(getHibernateTemplate(), item);
	}

	/**
	 * 保存对象集合
	 * 
	 * @param temp
	 * @param items
	 * @return
	 */
	protected void saveAll(HibernateTemplate temp, java.util.List items) {
		temp.saveOrUpdateAll(items);
		
		// for (int i = 0; i < items.size(); i++)
		//			temp.save(items.get(i));
	}

	/**
	 * 保存对象集合
	 * 
	 * @param items
	 * @return
	 */
	public void saveAll(java.util.List items) {
		if (items != null)
			saveAll(getHibernateTemplate(), items);
	}

	/**
	 * 保存对象集合
	 * 
	 * @param items
	 * @return
	 */
	public void saveAll(Object[] items) {
		saveAll(arrayToList(items));
	}

	/**
	 * 修改对象
	 * 
	 * @param temp
	 * @param item
	 */
	protected void update(HibernateTemplate temp, Object item) {
		temp.update(item);
	}

	/**
	 * 修改对象
	 * 
	 * @param item
	 */
	public void update(Object item) {
		update(getHibernateTemplate(), item);
	}

	/**
	 * 修改对象集合
	 * 
	 * @param temp
	 * @param items
	 */
	protected void updateAll(HibernateTemplate temp, java.util.List items) {
		for (int i = 0; i < items.size(); i++)
			temp.update(items.get(i));
	}

	/**
	 * 修改对象集合
	 * 
	 * @param items
	 */
	public void updateAll(java.util.List items) {
		if (items != null)
			updateAll(getHibernateTemplate(), items);
	}

	/**
	 * 修改对象集合
	 * 
	 * @param items
	 */
	public void updateAll(Object[] items) {
		updateAll(arrayToList(items));
	}

	public long getCount(String hql) throws RuntimeException {
		List items = getHibernateTemplate().find(hql);
		if (items.size() == 0)
			throw new RuntimeException("no results at CommonService.getCount");
		
		return (Long) items.get(0);
	}

	/**
	 * 取记录总数
	 * 
	 * @param output
	 *            查询输出。例如："select count(*) from Object"
	 * @param conditions
	 *            条件集合
	 * @return
	 */
	public long getCount(String output, List conditions)
			throws RuntimeException {
		return (Long) createQuery(output, conditions, null, null)
				.uniqueResult();
	}

	/**
	 * 取记录集
	 * 
	 * @param output
	 *            查询输出。例如："select o from Object o"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @return
	 * @throws RuntimeException
	 */
	protected List query(String output, List conditions, String groupBys,
			String orderBys) throws RuntimeException {

		return createQuery(output, conditions, groupBys, orderBys).list();
	}

	/**
	 * 取按页记录集
	 * 
	 * @param output
	 *            查询输出。例如："select o from Object o"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            行数
	 * @return
	 * @throws RuntimeException
	 */
	protected List query(String output, List conditions, String groupBys,
			String orderBys, int pageNo, int pageSize) throws RuntimeException {

		return createQuery(output, conditions, groupBys, orderBys)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 执行HQL语句查询
	 * 
	 * @param hql
	 *            HQL语句
	 * @return
	 * @throws RuntimeException
	 */
	public List query(String hql) throws RuntimeException {
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 执行HQL语句查询分页数据
	 * 
	 * @param hql
	 *            查询语句
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            行数
	 * @return
	 * @throws RuntimeException
	 */

	public ItemPage query(String hql, int pageNo, int pageSize)
			throws RuntimeException {
		Query query = getCurrentSession().createQuery(hql).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
		return new ItemPage(query.list(), getCount(hql), pageNo, pageSize);
	}

	/**
	 * 查询一页记录
	 * 
	 * @param countOutput
	 *            总数查询输出。例如："select count(*) from Object"
	 * @param itemOutput
	 *            记录查询输出。例如："select o from Object o"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 * @return 行数
	 * @throws RuntimeException
	 */
	protected ItemPage getPage(String countOutput, String itemOutput,
			List conditions, String groupBys, String orderBys, int pageNo,
			int pageSize) throws RuntimeException {
		return new ItemPage(createQuery(itemOutput, conditions, groupBys,
				orderBys).setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list(), getCount(countOutput,
				conditions), pageNo, pageSize);
	}

	/**
	 * 查询一页记录
	 * 
	 * @param objectName
	 *            对象名称
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 * @return 行数
	 * @throws RuntimeException
	 */
	protected ItemPage getPage(String objectName, List conditions,
			String groupBys, String orderBys, int pageNo, int pageSize)
			throws RuntimeException {
		return getPage("select count(*) from " + objectName, "from "
				+ objectName, conditions, groupBys, orderBys, pageNo, pageSize);
	}

	/**
	 * 生成查询
	 * 
	 * @param output
	 *            查询输出。例如："select a, b.name from A a, B b"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @return
	 * @throws RuntimeException
	 */
	private Query createQuery(String output, List conditions, String groupBys,
			String orderBys) throws RuntimeException {
		Query query = getCurrentSession().createQuery(
				new Select(output, conditions, groupBys, orderBys).toString());
		setValue(query, 0, conditions);

		return query;
	}

	/**
	 * 设置变量集合
	 * 
	 * @param query
	 *            查询实例
	 * @param conditions
	 *            条件集合
	 * @return
	 */
	private int setValue(Query query, int index, List conditions) {
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.formula instanceof Formula) {
					if (((Formula) condition.formula).containValue())
						index = setValue(query, index,
								(Formula) condition.formula);
				} else if (condition.formula instanceof List)
					index = setValue(query, index, (List) condition.formula);
			}
		}

		return index;
	}

	/**
	 * 设置变量
	 * 
	 * @param query
	 *            查询实例
	 * @param index
	 *            变量序号
	 * @param condition
	 *            条件实例
	 */
	private int setValue(Query query, int index, Formula formula) {
		switch (formula.operator) {
		case Formula.IN:
		case Formula.NI:
			if (formula.type == Formula.SELECT)
				index = setValue(query, index,
						((Select) formula.value).conditions);
			else {
				query
						.setParameterList(formula.name,
								(Collection) formula.value);
				index++;
			}
			break;

		default:
			switch (formula.type) {
			case Formula.BIGDECIMAL:
				query.setBigDecimal(index, (BigDecimal) formula.value);
				break;

			case Formula.BIGINTEGER:
				query.setBigInteger(index, (BigInteger) formula.value);
				break;

			case Formula.BINARY:
				query.setBinary(index, (byte[]) formula.value);
				break;

			case Formula.BOOLEAN:
				query.setBoolean(index, (Boolean) formula.value);
				break;

			case Formula.BYTE:
				query.setByte(index, (Byte) formula.value);
				break;

			case Formula.DATE:
				query.setDate(index, (Date) formula.value);
				break;

			case Formula.DOUBLE:
				query.setDouble(index, (Double) formula.value);
				break;

			case Formula.FLOAT:
				query.setFloat(index, (Float) formula.value);
				break;

			case Formula.INTEGER:
				query.setInteger(index, (Integer) formula.value);
				break;

			case Formula.LONG:
				query.setLong(index, (Long) formula.value);
				break;

			case Formula.SHORT:
				query.setShort(index, (Short) formula.value);
				break;

			case Formula.STRING:
				query.setString(index, (String) formula.value);
				break;

			case Formula.TEXT:
				query.setText(index, (String) formula.value);
				break;

			case Formula.TIME:
				query.setTime(index, (Date) formula.value);
				break;

			case Formula.TIMESTAMP:
				query.setTimestamp(index, (Date) formula.value);
				break;
			}

			index++;
			break;
		}

		return index;
	}
}
