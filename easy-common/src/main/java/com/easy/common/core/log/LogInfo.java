package com.easy.common.core.log;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import com.easy.common.core.runtime.BaseModel;
import com.easy.common.core.runtime.ProductContext;
import com.easy.common.core.runtime.ProductContextHolder;

public abstract class LogInfo extends BaseModel
{
  private static final long serialVersionUID = 7419129125801133567L;
  private String interceptorMethod;
  private String interceptorClass;
  private LoggerLevel loggerLevel = LoggerLevel.INFO;
  private String logId;
  private String logFileName;
  private StopWatch stopWatch;

  public String getLogId()
  {
    return this.logId;
  }

  public void setLogId(String logId)
  {
    this.logId = logId;
  }

  public String getInterceptorMethod()
  {
    return this.interceptorMethod;
  }

  public void setInterceptorMethod(String interceptorMethod)
  {
    this.interceptorMethod = interceptorMethod;
  }

  public String getInterceptorClass()
  {
    return this.interceptorClass;
  }

  public void setInterceptorClass(String interceptorClass)
  {
    this.interceptorClass = interceptorClass;
  }

  public LoggerLevel getLoggerLevel()
  {
    return this.loggerLevel;
  }

  public void setLoggerLevel(LoggerLevel loggerLevel)
  {
    this.loggerLevel = loggerLevel;
  }

  public String getLogFileName()
  {
    return this.logFileName;
  }

  public void setLogFileName(String logFileName)
  {
    this.logFileName = logFileName;
  }

  public StopWatch getStopWatch() {
    return this.stopWatch;
  }

  public void setStopWatch(StopWatch stopWatch) {
    this.stopWatch = stopWatch;
  }

  public abstract String toLogString();

  protected String getEnvironmentInfo()
  {
    StringBuffer sb = new StringBuffer(getLogId());
    ProductContext productContext = ProductContextHolder.getProductContext();

    sb.append("[(").append(StringUtils.trimToEmpty(productContext.getEnvironment().getClientIp())).append(",").append(StringUtils.trimToEmpty(productContext.getEnvironment().getServerIp())).append(")");

    sb.append("(").append(StringUtils.trimToEmpty(productContext.getEnvironment().getSessionId())).append(")");
    sb.append("(").append(StringUtils.trimToEmpty(productContext.getReferer())).append(")]-");
    return sb.toString();
  }
}