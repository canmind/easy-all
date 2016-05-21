package com.easy.common.core.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easy.common.core.log.DefaultDigestLogInfo;
import com.easy.common.core.log.LogInfo;
import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;
import com.easy.common.core.log.LoggerLevel;
import com.easy.common.core.log.LoggerPrintType;
import com.easy.common.core.log.annotation.DigestLogAnnotated;
import com.easy.common.core.runtime.ProductContextHolder;
import com.easy.common.core.util.AnnotatedUtils;

public class AnnotatedLogInterceptor extends BaseInterceptor{
	
  public Object bizInvoke(MethodInvocation invocation) throws Throwable{
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Method method = invocation.getMethod();
    String argumentString = null;

    String className = method.getDeclaringClass().getSimpleName();
    String methodName = method.getName();

    String logId = ProductContextHolder.getProductContext().getRequestId();
    try
    {
      DigestLogAnnotated digestAnnotated = (DigestLogAnnotated)AnnotatedUtils.getAnnotated(DigestLogAnnotated.class, invocation);
      if ((digestAnnotated != null) && 
        (LoggerPrintType.IGNORE_INPUT != digestAnnotated.printType())) {
        argumentString = convert2argumentList(invocation.getArguments());
      }

      Object result = invocation.proceed();
      if (digestAnnotated != null) {
        if (LoggerPrintType.IGNORE_OUTPUT != digestAnnotated.printType())
          digestInvokeLog(className, methodName, digestAnnotated.logFileName(), digestAnnotated.loggerLevel(), stopWatch, result, argumentString, null, true, logId, digestAnnotated.digestIdentificationCode());
        else {
          digestInvokeLog(className, methodName, digestAnnotated.logFileName(), digestAnnotated.loggerLevel(), stopWatch, null, argumentString, null, true, logId, digestAnnotated.digestIdentificationCode());
        }
      }
      return result;
    } catch (Throwable e) {
      boolean needThrow = true;
      DigestLogAnnotated digestAnnotated = (DigestLogAnnotated)AnnotatedUtils.getAnnotated(DigestLogAnnotated.class, invocation);
      if (digestAnnotated != null) {
        if (LoggerPrintType.IGNORE_INPUT != digestAnnotated.printType()) {
          argumentString = StringUtils.isBlank(argumentString) ? convert2argumentList(invocation.getArguments()) : argumentString;
        }
        if (LoggerPrintType.IGNORE_EXCEPTION != digestAnnotated.printType())
          digestInvokeLog(className, methodName, digestAnnotated.logFileName(), digestAnnotated.loggerLevel(), stopWatch, null, argumentString, e, false, logId, digestAnnotated.digestIdentificationCode());
        else {
          digestInvokeLog(className, methodName, digestAnnotated.logFileName(), digestAnnotated.loggerLevel(), stopWatch, null, argumentString, null, true, logId, digestAnnotated.digestIdentificationCode());
        }
      }
      if (needThrow)
        throw e;
    }
    return null;
  }

  private String convert2argumentList(Object[] arguments)
  {
    String returnStr = "";
    if (null != arguments) {
      returnStr = JSON.toJSONString(arguments, new SerializerFeature[] { SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty });
    }
    return returnStr;
  }

  protected void digestInvokeLog(String className, String methodName, String loggerName, LoggerLevel logLevel, StopWatch stopWatch, Object result, String arguments, Throwable e, boolean isInvokeSuccess, String logId, String digestIdentificationCode)
  {
    stopWatch.split();

    DefaultDigestLogInfo digestLogInfo = new DefaultDigestLogInfo();
    digestLogInfo.setLogId(logId);
    digestLogInfo.setInterceptorClass(className);
    digestLogInfo.setInterceptorMethod(methodName);
    digestLogInfo.setStopWatch(stopWatch);
    digestLogInfo.setLogFileName(loggerName);
    digestLogInfo.setLoggerLevel(logLevel);
    digestLogInfo.setDigestIdentificationCode(digestIdentificationCode);
    digestLogInfo.setRequestParams(arguments);
    digestLogInfo.setInvokeResult(result);
    digestLogInfo.setException(e);
    digestLogInfo.setInvokeSuccess(isInvokeSuccess);
    printLog(digestLogInfo);
  }

  protected void printLog(LogInfo logInfo)
  {
    Logger logger = LoggerFactory.getLogger(logInfo.getLogFileName());
    LoggerLevel loggerLevel = logInfo.getLoggerLevel();
    switch (loggerLevel.ordinal()) {
    case 1:
      logger.debug(logInfo.toLogString());
      break;
    case 2:
      logger.info(logInfo.toLogString());
      break;
    case 3:
      logger.warn(logInfo.toLogString());
      break;
    case 4:
      logger.error(logInfo.toLogString());
      break;
    }
  }
}
