package com.easy.common.core.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

class InterceptorChainSupport implements MethodInvocation {
	private static final Logger logger = LoggerFactory
			.getLogger(InterceptorChainSupport.class);
	private MethodInvocation proxy;
	private List<Interceptor> chains;

	public InterceptorChainSupport(MethodInvocation proxy,
			List<Interceptor> chains) {
		this.proxy = proxy;
		this.chains = chains;
	}

	public Method getMethod() {
		return this.proxy.getMethod();
	}

	public Object[] getArguments() {
		return this.proxy.getArguments();
	}

	public AccessibleObject getStaticPart() {
		return this.proxy.getStaticPart();
	}

	public Object getThis() {
		return this.proxy.getThis();
	}

	public Object proceed() throws Throwable {
		if ((null != this.chains) && (this.chains.size() > 0)) {
			if (logger.isDebugEnabled()) {
				logger.debug(" [ " + Thread.currentThread().getId()
						+ " ] Invoke Chanin [ " + this.chains.size()
						+ " ] , name is : "
						+ ((Interceptor) this.chains.get(0)).getClass());
			}
			return ((Interceptor) this.chains.remove(0)).invoke(this);
		}

		return this.proxy.proceed();
	}
}