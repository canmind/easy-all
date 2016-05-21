package com.easy.common.web.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

public class HttpSpringUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpSpringUtils.class);

	private static ApplicationContext applicationContext = null;

	public static ApplicationContext getCurrentApplicationContext(
			ServletContext servletContext) {
		if (null == applicationContext) {
			applicationContext = (ApplicationContext) servletContext
					.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		}
		return applicationContext;
	}

	public static Object getBean(String beanName, ServletContext servletContext) {
		ApplicationContext tempApplicationContext = getCurrentApplicationContext(servletContext);
		if (null == tempApplicationContext)
			return null;
		try {
			return tempApplicationContext.getBean(beanName);
		} catch (Exception e) {
			logger.error("Get Bean ,It's Name : " + beanName, e);
		}
		return null;
	}

	public static Object getBean(String beanName) {
		ApplicationContext tempApplicationContext = applicationContext;
		if (null == tempApplicationContext)
			return null;
		try {
			return tempApplicationContext.getBean(beanName);
		} catch (Exception e) {
			logger.error("Get Bean ,It's Name : " + beanName, e);
		}
		return null;
	}
}