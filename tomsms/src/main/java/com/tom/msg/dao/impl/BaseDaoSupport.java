package com.tom.msg.dao.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class BaseDaoSupport<T> extends SqlMapClientDaoSupport{

	@Autowired
	public void setSqlMapClientForAutowire(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	private Class<T> persistentType;
	
	public BaseDaoSupport(){
	}
	
	public BaseDaoSupport(Class<T> persistentType){
		this.persistentType = persistentType;
	}
	
	/**
	 * 根据主键查询
	 * 
	 * @param statementName
	 * @param id
	 * @return 对应的持久化类，类型:T
	 */
	public T findByPrimaryKey(String statementName,Long id){
		return (T)getSqlMapClientTemplate().queryForObject(statementName, id);
	}
	
	/**
	 * 根据参数查询
	 * 
	 * @param statementName
	 * @param param
	 * @return 对应的持久化类，类型:T
	 */
	public T findByParameter(String statementName,Object param){
		return (T)getSqlMapClientTemplate().queryForObject(statementName,param);
	}
	
	/**
	 * 根据statementname获取List
	 * 
	 * @param statementName
	 * @param parameter
	 * @return 对应的持久化类List，类型:List<T>
	 */
	public List<T> getListByStatementName(String statementName, Object parameter){
		return getSqlMapClientTemplate().queryForList(statementName, parameter);
	}
	
	/**
	 * 根据参数查询结果集
	 * 
	 * @param statementName
	 * @param param
	 * @return 对应的查询结果，类型:Object
	 */
	public Object getByParameter(String statementName,Object param){
		return getSqlMapClientTemplate().queryForObject(statementName,param);
	}
	
	/**
	 * 修改
	 * 
	 * @param statementName
	 * @param record
	 * @return 修改的记录数，类型:int
	 */
	public int update(String statementName,T record){
		return getSqlMapClientTemplate().update(statementName, record);
	}
	
	/**
	 * 新增
	 * 
	 * @param statementName
	 * @param record
	 * @return 新增记录时返回的结果对象，类型:Object
	 */
	public Object insert(String statementName,T record){
		return getSqlMapClientTemplate().insert(statementName, record);
	}
	
	/**
	 * 删除
	 * 
	 * @param statementName
	 * @param record
	 * @return 删除的记录数，类型:int
	 */
	public int delete(String statementName,T record){
		return getSqlMapClientTemplate().delete(statementName, record);
	}

	/**
	 * 将对象转换成Map
	 * 
	 * @param record
	 * @return 以对象的属性为key，对象属性值为value的Map，类型:Map<String,Object>
	 */
	public Map<String, Object> tranObj2Map(T record) {
		Map param = new HashMap();
		Field[] fields = record.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				Object val = fields[i].get(record);
				if ((val == null) || (val.toString().equals("")))
					continue;
				param.put(fields[i].getName(), val);
			}
		} catch (Exception e) {
			throw new RuntimeException("fetch field error..");
		}
		return param;
	}
	
	

}
