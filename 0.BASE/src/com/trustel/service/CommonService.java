package com.trustel.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.trustel.common.ItemPage;

/**
 * @author Administrator
 *
 */
public interface CommonService {
	/**
	 * 删除对象
	 * 
	 * @param classType
	 * @param id
	 */
	public void delete(Class classType, String id);
    /**
     * 删除对象
     * 
     * @param classType
     * @param ids
     */
    public void delete(Class classType, java.util.List ids);
    /**
     * 删除对象
     * 
     * @param classType
     * @param ids
     */
    public void delete(Class classType, Object[] ids);
    /**
     * 批量更新/删除
     * 
     * @param hql SQL语句
     * @return
     */
    public int execute(String hql);
    /**
     * 批量更新/删除
     * 
     * @param query SQL语句
     * @param args 参数
     * @return
     */
    public int execute(String query, Object[] args);
    /**
     * 取所有对象
     * 
     * @param classType 类型
     * @param orderBys 排序字段
     * @return
     */
    public java.util.List getAll(Class classType, String orderBys);
    /**
     * 分页取所有对象
     * 
     * @param classType 类型
     * @param orderBys 排序字段
     * @param pageNo 页码
     * @param pageSize 行数
     * @return
     */
    public com.trustel.common.ItemPage getAll(Class classType, String orderBys, int pageNo, int pageSize);
    /**
     * 根据ID取对象
     * 
     * @param classType 类类型
     * @param id
     * @return
     */
    public Object getById(Class classType, Serializable id);
    
    /**
     * 单个条件取对象
     * 
     * @param classType 类型
     * @param formula 条件公式
     * @param orderBys 排序
     * @return
     */
    public List getByCondition(Class classType, Formula formula, String orderBys) throws RuntimeException;
    
    /**
     * 单个条件取一页对象
     * 
     * @param classType 类型
     * @param formula 条件公式
     * @param orderBys 排序
     * @param pageNo 页码
     * @param pageSize 行数
     * @return
     */
    public ItemPage getByCondition(Class classType, Formula formula, String orderBys, int pageNo, int pageSize) throws RuntimeException;
    
    /**
     * 保存对象
     * 
     * @param item 
     * @return 记录主键
     */
    public Serializable save(Object item);
    /**
     * 保存对象集合
     * 
     * @param items
     */
    public void saveAll(java.util.List items);
    /**
     * 保存对象集合
     * 
     * @param items
     */
    public void saveAll(Object[] items);
    /**
     * 修改对象
     * 
     * @param item
     */
    public void update(Object item);
    
    /**
     * 修改对象集合
     * 
     * @param items
     */
    public void updateAll(java.util.List items);
    
    /**
     * 修改对象集合
     * 
     * @param items
     */
    public void updateAll(Object[] items);
	/**
	 * 取记录总数
	 * 
	 * @param hql
	 *            查询语句
	 * @return
	 */
	public long getCount(String hql) throws RuntimeException;    
	/**
	 * 取记录总数
	 * 
	 * @param output
	 *            查询输出。例如："select count(*) from Object"
	 * @param conditions
	 *            条件集合
	 * @return
	 */
	public long getCount(String output, List conditions) throws RuntimeException;

	/**
	 * 执行HQL语句查询分页数据
	 * 
	 * @param hql
	 *            查询语句
	 * @return
	 * @throws RuntimeException
	 */
	public List query(String hql) throws RuntimeException;
	
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
	public ItemPage query(String hql, int pageNo, int pageSize) throws RuntimeException;
	
	/**
	 * 取当前连接
	 * 
	 * @return
	 */
	public Session getCurrentSession();
}
