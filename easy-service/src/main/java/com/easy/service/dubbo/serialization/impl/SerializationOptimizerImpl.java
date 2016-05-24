package com.easy.service.dubbo.serialization.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.easy.service.model.param.UserBo;

public class SerializationOptimizerImpl implements SerializationOptimizer {

	/**
	 * 增加自己的bo result
	 */
	@Override
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<Class>();
		classes.add(UserBo.class);

		return classes;
	}

}
