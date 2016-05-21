package com.easy.common.web.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.easy.common.core.runtime.ProductContextHolder;

public final class HttpHeaderUtils {

	private static final String HTT_REQUEST_COOKIE_THREAD_CACHE = "_http_request_cookie_thread_cache_";

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0)
				|| ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	public static Map<String, Cookie> getAllCookie(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = (Map<String, Cookie>) ProductContextHolder.getProductContext().find(
				HTT_REQUEST_COOKIE_THREAD_CACHE);
		if (null == cookieMap) {
			cookieMap = new HashMap<String, Cookie>();
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				int length = cookies.length;
				for (int i = 0; i < length; i++) {
					cookieMap.put(cookies[i].getName(), cookies[i]);
				}
			}
			ProductContextHolder.getProductContext().put(HTT_REQUEST_COOKIE_THREAD_CACHE, cookieMap);
		}
		return cookieMap;
	}

	public static String getCookieValue(String cookieName,
			HttpServletRequest request) {
		Cookie cookie = (Cookie) getAllCookie(request).get(cookieName);
		if (cookie != null) {
			return cookie.getValue();
		}
		return "";
	}

	public static String getRequestURLWithParameter(HttpServletRequest request) {
		StringBuffer buffer = request.getRequestURL();
		Map<?, ?> parameter = request.getParameterMap();
		if ((null != parameter) && (!parameter.isEmpty())) {
			buffer.append("?");
			Iterator<?> keys = parameter.keySet().iterator();
			String key = null;
			String[] value = null;
			while (keys.hasNext()) {
				key = (String) keys.next();
				value = request.getParameterValues(key);
				if ((null == value) || (value.length == 0))
					buffer.append(key).append("=").append("");
				else if (value.length > 0) {
					buffer.append(key).append("=").append(value[0]);
				}
				if (keys.hasNext()) {
					buffer.append("&");
				}
			}
		}
		return buffer.toString();
	}

	public static String getRequestURL(HttpServletRequest request) {
		StringBuffer buffer = request.getRequestURL();
		return buffer.toString();
	}

	public static String getHttpRootAddress(HttpServletRequest request) {
		String protocol = request.getProtocol();
		if (StringUtils.isNotBlank(protocol)) {
			int p = protocol.indexOf("/");
			if (p > -1) {
				protocol = protocol.substring(0, p);
			}
		}
		StringBuffer buffer = new StringBuffer(protocol);
		buffer.append("://");
		buffer.append(request.getServerName());
		buffer.append(":");
		buffer.append(request.getServerPort());
		String contextPath = request.getContextPath();
		if (StringUtils.isNotBlank(contextPath)) {
			buffer.append("/").append(contextPath);
		}
		return buffer.toString();
	}
}