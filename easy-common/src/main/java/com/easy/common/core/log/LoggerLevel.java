package com.easy.common.core.log;

import com.easy.common.core.util.JavaEnumUtils;

public enum LoggerLevel{
  DEBUG("DEBUG", "DEBUG������־"), INFO("INFO", "INFO������־"), WAR("WAR", "WAR������־"), ERROR("ERROR", "ERROR������־");

  private String code;
  private String description;

  private LoggerLevel(String code, String description)
  {
    this.code = code;
    this.description = description;
		JavaEnumUtils.put(getClass().getName() + code, this);
  }

  public static LoggerLevel valueByCode(String code)
  {
    Object obj = JavaEnumUtils.get(LoggerLevel.class.getName() + code);
    if (null != obj) {
      return (LoggerLevel)obj;
    }
    return INFO;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}