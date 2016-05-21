package com.easy.common.core.runtime;

public final class ProductContextHolder {
	private static ThreadLocal<ProductContext> context = new ThreadLocal<ProductContext>() {
		public ProductContext initialValue() {
			return new ProductContext();
		}
	};

	public static void clean() {
		if (null != context.get()) {
			((ProductContext) context.get()).clean();
			context.set(null);
		}
	}

	public static ProductContext getProductContext() {
		if (null == context.get()) {
			context.set(new ProductContext());
		}
		return (ProductContext) context.get();
	}

	public static void setProductContext(ProductContext productContext) {
		context.set(productContext);
	}
}
