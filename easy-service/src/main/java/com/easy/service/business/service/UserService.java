package com.easy.service.business.service;

import com.easy.service.model.param.UserBo;

/**
 * Created by lenovo on 2014/10/1.
 */
public interface UserService {

	/**
	 * 注册接口
	 * @param bo
	 * @return
	 */
    public boolean register(UserBo bo);
}
