package com.easy.service.dao.base;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;


public class BaseDao extends SqlSessionDaoSupport {


    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    /**
     * Description: 新增一条记录
     *
     * @param statementName SQL语句的ID
     * @param object        参数
     * @Version1.0 2014-1-21 下午3:45:23 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public Object insert(String statementName, Object object) {
        return getSqlSession().insert(statementName, object);
    }

    /**
     * Description: 更新一条记录
     *
     * @param statementName SQL语句的ID
     * @param object        参数
     * @Version1.0 2014-1-21 下午3:45:23 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public Integer update(String statementName, Object object) {
        return getSqlSession().update(statementName, object);
    }

    /**
     * Description: 删除一条记录
     *
     * @param statementName SQL语句的ID
     * @param object        参数
     * @Version1.0 2014-1-21 下午3:45:23 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public Integer delete(String statementName, Object object) {
        return getSqlSession().delete(statementName, object);
    }

    /**
     * Description: 获取一条记录(一般根据逐渐获取)
     *
     * @param statementName SQL语句ID
     * @param object        参数
     * @return 符合条件的一条记录
     * @Version1.0 2014-1-21 下午3:46:07 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public <T> T getOne(String statementName, Object object) {
        return getSqlSession().selectOne(statementName, object);
    }

    /**
     * Description: 获取一条记录(一般根据逐渐获取)
     *
     * @param statementName SQL语句ID
     *                      参数
     * @return 符合条件的一条记录
     * @Version1.0 2014-1-21 下午3:46:07 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public <T> T getOne(String statementName) {
        return getSqlSession().selectOne(statementName);
    }

    /**
     * Description: 查询所有
     *
     * @param statementName SQL语句的ID
     * @return
     * @Version1.0 2014-1-21 下午3:46:39 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public <T> List<T> queryForList(String statementName) {
        return getSqlSession().selectList(statementName);
    }

    /**
     * Description: 根据条件查询
     *
     * @param statementName SQL语句的ID
     * @param paramObj      查询条件
     * @return
     * @Version1.0 2014-1-21 下午3:47:08 by 王星皓（wangxinghao@ucfgroup.com）创建
     */
    public <T> List<T> queryForList(String statementName, Object paramObj) {
        return getSqlSession().selectList(statementName, paramObj);
    }

}
