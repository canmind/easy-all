package com.easy.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.easy.common.core.runtime.ProductContext;
import com.easy.common.core.runtime.ProductContextHolder;

public final class WebProductContextHolder {
	private static final String HTTP_SESSION_CONSTANT = "HTTP_SESSION";
	public static final String ENCODE_RETURN_RESULT_CONSTANT = "$$ENCODE_RETURN_RESULT_TAG";
	public static final String WEB_CNTXT_RESULT_CONSTANT = "$$WEB_CNTXT_RESULT_CONSTANT";
	private static ThreadLocal<ProductContextWrapper> context = new ThreadLocal<ProductContextWrapper>() {
		public WebProductContextHolder.ProductContextWrapper initialValue() {
			return new WebProductContextHolder.ProductContextWrapper();
		}
	};

	public static void clean() {
		if (null != context.get()) {
			((ProductContextWrapper) context.get()).clean();
			context.set(null);
		}
	}

	public static ProductContext getProductContext() {
		ProductContext prdctx = getProductContextWrapper().getProductContext();
		prdctx.put(WEB_CNTXT_RESULT_CONSTANT, Boolean.valueOf(true));
		return prdctx;
	}

	public static void setProductContext(ProductContext productContext) {
		if (null == context.get()) {
			context.set(new ProductContextWrapper());
			productContext
					.put(WEB_CNTXT_RESULT_CONSTANT, Boolean.valueOf(true));
			((ProductContextWrapper) context.get())
					.setProductContext(productContext);
		} else {
			productContext
					.put(WEB_CNTXT_RESULT_CONSTANT, Boolean.valueOf(true));
			((ProductContextWrapper) context.get())
					.setProductContext(productContext);
		}
	}

	private static ProductContextWrapper getProductContextWrapper() {
		if (null == context.get()) {
			context.set(new ProductContextWrapper());
		}
		return (ProductContextWrapper) context.get();
	}

	public static boolean isWebInitContext() {
		boolean isEncodeResponse = null == getProductContextWrapper()
				.getProductContext().find(WEB_CNTXT_RESULT_CONSTANT) ? false
				: ((Boolean) getProductContextWrapper().getProductContext()
						.find(WEB_CNTXT_RESULT_CONSTANT)).booleanValue();
		return isEncodeResponse;
	}

	public static void setEncodeResponse(CodeType codeType, String key) {
		Object[] value = { codeType, key };
		getProductContextWrapper().transitContainer.put(
				ENCODE_RETURN_RESULT_CONSTANT, value);
	}

	public static void setEncodeResponse(CodeType codeType) {
		setEncodeResponse(codeType, "");
	}

	public static CodeType encodeResponse() {
		Object[] value = (Object[]) getProductContextWrapper().transitContainer
				.get(ENCODE_RETURN_RESULT_CONSTANT);
		CodeType codeType = null == value ? null : (CodeType) value[0];
		return codeType;
	}

	public static String encodeResponseKey() {
		Object[] value = (Object[]) getProductContextWrapper().transitContainer
				.get(ENCODE_RETURN_RESULT_CONSTANT);
		String key = null == value ? null : (String) value[1];
		return key;
	}

	public static void setHttpSession(HttpSession session) {
		getProductContextWrapper().transitContainer.put(HTTP_SESSION_CONSTANT,
				session);
	}

	public static HttpSession getHttpSession() {
		return (HttpSession) getProductContextWrapper().transitContainer
				.get(HTTP_SESSION_CONSTANT);
	}

	static class ProductContextWrapper {
		private Map<String, Object> transitContainer;

		public ProductContextWrapper() {
			this.transitContainer = new HashMap<String, Object>();
		}

		public ProductContext getProductContext() {
			return ProductContextHolder.getProductContext();
		}

		public void setProductContext(ProductContext productContext) {
			ProductContextHolder.setProductContext(productContext);
		}

		public Map<String, Object> getTransitContainer() {
			return this.transitContainer;
		}

		public void clean() {
			ProductContextHolder.clean();
			this.transitContainer.clear();
		}
	}
}