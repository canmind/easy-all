package com.easy.common.core.aop;

import java.util.ArrayList;
import java.util.List;
import org.aopalliance.intercept.MethodInvocation;

public class InterceptorChain extends BaseInterceptor
{
  private List<Interceptor> chains = new ArrayList<Interceptor>();

  public Object bizInvoke(MethodInvocation invocation) throws Throwable
  {
    InterceptorChainSupport support = new InterceptorChainSupport(invocation, new ArrayList<Interceptor>(this.chains));

    return support.proceed();
  }

  public void setChains(List<Interceptor> chains) {
    this.chains = chains;
  }
}
