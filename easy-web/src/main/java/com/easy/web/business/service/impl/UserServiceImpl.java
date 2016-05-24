/**
 * 
 */
package com.easy.web.business.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.easy.service.dao.UserDao;
import com.easy.service.dao.pojo.User;
import com.easy.service.model.param.UserBo;
import com.easy.service.business.service.UserService;
import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;


/**
 * @author lenovo
 *
 */

public class UserServiceImpl implements UserService{
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource UserDao userDao;
	
	public boolean register(UserBo bo) {
		logger.info("[o2o][service.impl] register user info :{}", new Object[]{JSON.toJSON(bo)});
		User user = new User();
		BeanUtils.copyProperties(bo, user);
		Integer id = userDao.insert(user);
		if(id.intValue()>0){
			return true;
		}
		return false;
	}

}
