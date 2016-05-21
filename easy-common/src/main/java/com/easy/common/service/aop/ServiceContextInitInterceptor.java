package com.easy.common.service.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.easy.common.core.aop.BaseInterceptor;
import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;
import com.easy.common.core.runtime.MdcHolder;
import com.easy.common.core.runtime.ProductContext;
import com.easy.common.core.runtime.ProductContextHolder;
import com.easy.common.core.runtime.ProductEnvironment;
import com.easy.common.web.WebProductContextHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServiceContextInitInterceptor extends BaseInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(ServiceContextInitInterceptor.class);

	private static ThreadLocal<Long> SERVICE_INIT_COUNT = new ThreadLocal<Long>() {
		public Long initialValue() {
			return Long.valueOf(0L);
		}
	};

	public Object bizInvoke(MethodInvocation invocation) throws Throwable {
		try {
			SERVICE_INIT_COUNT.set(Long.valueOf(((Long) SERVICE_INIT_COUNT
					.get()).longValue() + 1L));
			Object[] args = invocation.getArguments();

			if (isLocalProxy(invocation)) {
				pullContext(invocation);

				if (!StringUtils.endsWith(invocation.getThis().getClass()
						.getSimpleName(), "Stub")) {
					ProductContextHolder.getProductContext().put(
							"methodInvocation", invocation);
					ProductContextHolder.getProductContext().put(
							"invokeMethod", invocation.getMethod());
				}
			} else {
				pushContext(invocation);
			}

			MdcHolder.init(ProductContextHolder.getProductContext());
			if (logger.isDebugEnabled()) {
				logger.debug("productContext目前的值是"
						+ ProductContextHolder.getProductContext());
			}

			if (logger.isDebugEnabled()) {
				StringBuffer buff = new StringBuffer();
				for (Object arg : args) {
					buff.append(invocation.getMethod() + " -- arg[")
							.append(ToStringBuilder.reflectionToString(arg,
									ToStringStyle.MULTI_LINE_STYLE))
							.append("]");
				}
				logger.debug(buff.toString());
			}

			Object obj = invocation.proceed();
			StringBuffer buff;
			if (logger.isDebugEnabled()) {
				buff = new StringBuffer();
				buff.append(invocation.getMethod() + " -- result[")
						.append(ToStringBuilder.reflectionToString(obj,
								ToStringStyle.MULTI_LINE_STYLE)).append("]");
				logger.debug(buff.toString());
			}
			Long c;
			return obj;
		} finally {
			if (!WebProductContextHolder.isWebInitContext()) {
				Long c = (Long) SERVICE_INIT_COUNT.get();
				if ((null != c) && (c.longValue() > 1L)) {
					SERVICE_INIT_COUNT.set(Long
							.valueOf(((Long) SERVICE_INIT_COUNT.get())
									.longValue() - 1L));
				} else {
					SERVICE_INIT_COUNT.set(Long.valueOf(0L));
					ProductContextHolder.clean();
				}
			}
			MdcHolder.clean();
		}
	}

	private boolean isLocalProxy(MethodInvocation invocation) {
		if (((invocation.getThis() instanceof EchoService))
				|| ((invocation.getThis() instanceof GenericService))) {
			return false;
		}
		return true;
	}

	private void pullContext(MethodInvocation invocation) {
		if (!WebProductContextHolder.isWebInitContext()) {
			String productContextStr = RpcContext.getContext().getAttachment(
					String.valueOf(ProductContext.class.getName().hashCode()));
			if (StringUtils.isNotBlank(productContextStr)) {
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				ProductContextHolder.setProductContext((ProductContext) gson
						.fromJson(productContextStr, ProductContext.class));
			} else if (logger.isDebugEnabled()) {
				logger.debug("分布式 productContext传递失败");
			}
		} else if (logger.isDebugEnabled()) {
			logger.debug("web app invoke local service:"
					+ ToStringBuilder.reflectionToString(invocation.getThis()));
		}
	}

	private void pushContext(MethodInvocation invocation) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		ProductContext productContext = ProductContextHolder
				.getProductContext();

		ProductEnvironment environment = productContext.getEnvironment();

		if (StringUtils.isBlank(environment.getServerIp()))
			environment.setServerIp(RpcContext.getContext()
					.getLocalAddressString());
		if (StringUtils.isBlank(environment.getServerName()))
			environment.setServerName(RpcContext.getContext().getMethodName());
		String productContextInfo = gson.toJson(productContext);
		if (logger.isDebugEnabled()) {
			logger.debug("pushContext productContext : " + productContextInfo);
		}
		RpcContext.getContext().setAttachment(
				String.valueOf(ProductContext.class.getName().hashCode()),
				productContextInfo);
	}
}