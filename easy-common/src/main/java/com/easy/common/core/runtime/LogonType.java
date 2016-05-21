package com.easy.common.core.runtime;

public enum LogonType
{
  ZHANNEI(1);

  int type;

  private LogonType(int type) {
    this.type = type;
  }

  public int value() {
    return this.type;
  }
}