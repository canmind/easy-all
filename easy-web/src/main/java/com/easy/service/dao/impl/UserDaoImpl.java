package com.easy.service.dao.impl;

import org.springframework.stereotype.Repository;

import com.easy.service.dao.UserDao;
import com.easy.service.dao.base.BaseDao;
import com.easy.service.dao.pojo.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao{

	public Integer insert(User user) {
		return (Integer) this.insert("com.o2o.dao.pojo.UserMapper.insert", user);
	}

}
