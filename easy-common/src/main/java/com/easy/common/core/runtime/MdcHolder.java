package com.easy.common.core.runtime;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

public class MdcHolder {
	private static final Logger logger = LoggerFactory.getLogger(MdcHolder.class);
	public static final String MDC_REQUEST_ID_KEY = "MDC_REQUEST_ID";
	public static final String MDC_SESSION_KEY = "MDC_SESSION";
	public static final String MDC_CLIENT_IP_KEY = "MDC_CLIENT_IP";
	public static final String MDC_SERVER_IP_KEY = "MDC_SERVER_IP";
	private static ThreadLocal<Long> MDC_COUNT = new ThreadLocal<Long>() {
		public Long initialValue() {
			return Long.valueOf(0L);
		}
	};

	public static void init(ProductContext productContext) {
		try {
			MDC.put(MDC_REQUEST_ID_KEY, productContext.getRequestId());
			if (null != productContext.getEnvironment()) {
				if (StringUtils.isNotBlank(productContext.getEnvironment()
						.getSessionId()))
					MDC.put(MDC_SESSION_KEY, productContext.getEnvironment()
							.getSessionId());
				if (StringUtils.isNotBlank(productContext.getEnvironment()
						.getClientIp()))
					MDC.put(MDC_CLIENT_IP_KEY, productContext.getEnvironment()
							.getClientIp());
				if (StringUtils.isNotBlank(productContext.getEnvironment()
						.getServerIp()))
					MDC.put(MDC_SERVER_IP_KEY, productContext.getEnvironment()
							.getServerIp());
			}
		} catch (Exception e) {
			logger.warn("initMDC:" + e.getMessage());
		} finally {
			MDC_COUNT
					.set(Long.valueOf(((Long) MDC_COUNT.get()).longValue() + 1L));
		}
	}

	public static void clean() {
		Long c = (Long) MDC_COUNT.get();
		if ((null != c) && (c.longValue() > 1L)) {
			MDC_COUNT
					.set(Long.valueOf(((Long) MDC_COUNT.get()).longValue() - 1L));
		} else {
			MDC_COUNT.set(Long.valueOf(0L));
			MDC.clear();
		}
	}
}