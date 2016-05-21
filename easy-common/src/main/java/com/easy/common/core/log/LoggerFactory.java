package com.easy.common.core.log;

import com.easy.common.core.log.impl.LoggerImpl;


/**
 * create by haifeng.chen
 */
public class LoggerFactory {

	public static Logger getLogger(String name) {
		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(name);
		LoggerImpl loggerImpl = new LoggerImpl();
		loggerImpl.setLogger(logger);
		return loggerImpl;
	}

	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}



}
