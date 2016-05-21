package com.easy.common.core.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.easy.common.core.log.LoggerLevel;
import com.easy.common.core.log.LoggerPrintType;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DigestLogAnnotated {
	public abstract String logFileName();

	public abstract LoggerLevel loggerLevel();

	public abstract String digestIdentificationCode();

	public abstract LoggerPrintType printType();
}