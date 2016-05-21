package com.easy.common.web;

import javax.servlet.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.easy.common.core.log.Logger;
import com.easy.common.core.runtime.ProductContext;
import com.easy.common.core.util.StringMatchUtils;
import com.easy.common.web.util.HttpHeaderUtils;
import com.easy.common.web.util.HttpSpringUtils;

public abstract class AbstractFilter implements Filter {
	protected static final String FILTER_TAG = "_filter_tag_";
	public static final String URL_IGNORE_LIST_PATTERN = "ignoreListPattern";
	public static final String URL_IGNORE_LIST_SUFFIX = "ignoreListSuffix";
	private String ignoreList = "gif,css,ico,js,swf,jpg,jpeg,png,tiff,pcx";

	private List<String> ignorePattern = new ArrayList<String>();
	private FilterConfig filterConfig;

	public void destroy() {
	}

	public final void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		HttpSpringUtils.getCurrentApplicationContext(filterConfig
				.getServletContext());

		String temp = filterConfig.getInitParameter("ignoreListPattern");
		if (StringUtils.isNotBlank(temp)) {
			String[] tempIgnorePattern = StringUtils.split(temp, ",");
			for (String tip : tempIgnorePattern) {
				this.ignorePattern.add(tip);
			}
		}

		temp = filterConfig.getInitParameter("ignoreListSuffix");
		if (StringUtils.isNotBlank(temp)) {
			if (!temp.startsWith(",")) {
				this.ignoreList += ",";
			}
			this.ignoreList += temp;
		}
		doInit(filterConfig);
	}

	protected void doInit(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (checkIgnoreFilter((HttpServletRequest) request))
			chain.doFilter(request, response);
		else
			doFilterLogic(request, response, chain);
	}

	protected abstract void doFilterLogic(ServletRequest paramServletRequest,
			ServletResponse paramServletResponse, FilterChain paramFilterChain)
			throws IOException, ServletException;

	protected boolean checkIgnoreFilter(HttpServletRequest request) {
		String uri = HttpHeaderUtils.getRequestURL(request);
		int p = uri.lastIndexOf(".");

		if (p > -1) {
			try {
				String type = uri.substring(p + 1);
				if ((this.ignoreList.indexOf(type) > -1)
						&& (!uri.contains("Captcha")))
					return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (String pattern : this.ignorePattern) {
			if (StringMatchUtils.stringMatch(uri, pattern)) {
				return true;
			}
		}
		return false;
	}

	protected String findInitParameter(String paramName, String defaultValue) {
		String value = trimToNull(getFilterConfig().getInitParameter(paramName));

		if (value == null) {
			value = trimToNull(getServletContext().getInitParameter(paramName));
		}

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	protected FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	protected ServletContext getServletContext() {
		return getFilterConfig().getServletContext();
	}

	protected String trimToNull(String str) {
		if (str != null) {
			str = str.trim();

			if (str.length() == 0) {
				str = null;
			}
		}

		return str;
	}

	protected void debug(Logger log, String msg) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Thread.currentThread().getId()).append(" | ");
		buffer.append(Thread.currentThread().getName()).append(" | ");
		ProductContext productContext = WebProductContextHolder
				.getProductContext();
		buffer.append(productContext.getRequestId()).append(" | [ ");
		buffer.append(msg);
		buffer.append(" ] ");
		log.debug(buffer.toString());
	}
}
