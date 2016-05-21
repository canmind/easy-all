package com.easy.common.web.product.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;
import com.easy.common.core.runtime.ProductContext;
import com.easy.common.core.runtime.ProductEnvironment;
import com.easy.common.core.util.LocalIPUtils;
import com.easy.common.web.AbstractFilter;
import com.easy.common.web.CodeType;
import com.easy.common.web.WebProductContextHolder;
import com.easy.common.web.util.HttpHeaderUtils;

public class ProductContextFilter extends AbstractFilter implements Filter {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductContextFilter.class);

	protected void doInit(FilterConfig filterConfig) throws ServletException {
		logger.warn("init");
	}

	public void destroy() {
	}

	protected void doFilterLogic(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest hsr = (HttpServletRequest) request;
		try {
			WebProductContextHolder.setHttpSession(hsr.getSession());

			ProductContext productContext = WebProductContextHolder
					.getProductContext();

			ProductEnvironment environment = productContext.getEnvironment();

			String clientIp = HttpHeaderUtils.getClientIP(hsr);
			if ((StringUtils.isNotBlank(clientIp)) && (clientIp.length() > 15)) {
				clientIp = clientIp.split(",")[0];
				if (clientIp.length() > 15) {
					clientIp = clientIp.substring(0, 15);
				}
			}
			environment.setClientIp(clientIp);

			environment.setServerIp(LocalIPUtils.getIp4Single());

			environment.setServerName(hsr.getServerName());

			String session = HttpHeaderUtils.getCookieValue("redis_sessionId",
					hsr);
			environment.setSessionId(StringUtils.isNotBlank(session) ? session
					: hsr.getRequestedSessionId());

			productContext.setTime(new Date());

			productContext.setReferer(HttpHeaderUtils.getReferer(hsr));

			productContext.setUrl(HttpHeaderUtils
					.getRequestURLWithParameter(hsr));
			if (logger.isDebugEnabled()) {
				debug(logger, productContext.toString());
			}
			chain.doFilter(request, response);
		} catch (ServletException sx) {
			logger.error(sx.getMessage(), sx);
		} catch (IOException iox) {
			logger.error(iox.getMessage(), iox);
		} finally {
			CodeType codeType = WebProductContextHolder.encodeResponse();

			if (null == codeType)
				WebProductContextHolder.clean();
		}
	}
}