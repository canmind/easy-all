/**
 * 
 */
package com.easy.common.core.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author lenovo
 * 
 */
public abstract class BaseInterceptor implements Interceptor {
	private static Map<Method, Boolean> methods = null;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (null == methods) {
			methods = new HashMap<Method, Boolean>();
			for (Method m : Object.class.getMethods()) {
				methods.put(m, Boolean.valueOf(true));
			}
		}

		if (null != methods.get(invocation.getMethod()))
			return invocation.proceed();
		return bizInvoke(invocation);
	}

	public abstract Object bizInvoke(MethodInvocation paramMethodInvocation)
			throws Throwable;
}
