package com.easy.common.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInvocation;

public final class AnnotatedUtils {
	public static <T>Annotation getAnnotated(Class<? extends Annotation> annotatedClass,
			MethodInvocation invocation) {
		Method impMethod = null;

		Method method = invocation.getMethod();

		if (!method.isAnnotationPresent(annotatedClass)) {
			impMethod = implLogInterceptor(invocation, method);
			if ((impMethod == null)
					|| (!impMethod.isAnnotationPresent(annotatedClass))) {
				return null;
			}
			return impMethod.getAnnotation(annotatedClass);
		}

		return method.getAnnotation(annotatedClass);
	}

	private static Method implLogInterceptor(MethodInvocation invocation,
			Method method) {
		Method[] methods = invocation.getThis().getClass().getMethods();
		for (Method templateMethod : methods) {
			if (templateMethod.getName().equals(method.getName())) {
				return templateMethod;
			}
		}
		return null;
	}
}
