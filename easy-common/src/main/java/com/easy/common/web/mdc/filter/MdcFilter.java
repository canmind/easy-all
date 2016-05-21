package com.easy.common.web.mdc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.easy.common.core.runtime.MdcHolder;
import com.easy.common.web.AbstractFilter;
import com.easy.common.web.WebProductContextHolder;

public class MdcFilter extends AbstractFilter implements Filter {
	protected void doFilterLogic(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		MdcHolder.init(WebProductContextHolder.getProductContext());
		try {
			chain.doFilter(request, response);
		} finally {
			MdcHolder.clean();
		}
	}

}