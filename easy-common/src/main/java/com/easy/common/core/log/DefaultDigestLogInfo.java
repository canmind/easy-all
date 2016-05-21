package com.easy.common.core.log;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class DefaultDigestLogInfo extends LogInfo {
	private static final long serialVersionUID = -6366610594393235582L;
	private String requestParams = "";
	private Object invokeResult;
	private boolean isInvokeSuccess;
	private String digestIdentificationCode;
	private Throwable exception;

	public String toLogString() {
		StringBuffer sb = new StringBuffer(getEnvironmentInfo());

		sb.append("[")
				.append(this.digestIdentificationCode)
				.append("(")
				.append(getInterceptorClass())
				.append(",")
				.append(getInterceptorMethod())
				.append(",")
				.append(this.isInvokeSuccess ? "Y" : "N")
				.append(",")
				.append(getStopWatch().getSplitTime())
				.append("ms)")
				.append("(")
				.append(StringUtils.isBlank(this.requestParams) ? "-"
						: this.requestParams)
				.append(")")
				.append("(")
				.append(this.invokeResult != null ? JSON.toJSONString(
						this.invokeResult, new SerializerFeature[] {
								SerializerFeature.WriteMapNullValue,
								SerializerFeature.WriteNullListAsEmpty }) : "-")
				.append(")")
				.append("(")
				.append(this.exception != null ? this.exception.getMessage()
						: "-").append(")").append("]");

		return sb.toString();
	}

	public String getRequestParams() {
		return this.requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	public Object getInvokeResult() {
		return this.invokeResult;
	}

	public void setInvokeResult(Object invokeResult) {
		this.invokeResult = invokeResult;
	}

	public boolean isInvokeSuccess() {
		return this.isInvokeSuccess;
	}

	public void setInvokeSuccess(boolean isInvokeSuccess) {
		this.isInvokeSuccess = isInvokeSuccess;
	}

	public String getDigestIdentificationCode() {
		return this.digestIdentificationCode;
	}

	public void setDigestIdentificationCode(String digestIdentificationCode) {
		this.digestIdentificationCode = digestIdentificationCode;
	}

	public Throwable getException() {
		return this.exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
