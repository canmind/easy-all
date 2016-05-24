package com.easy.common.web;

import com.o2o.framework.web.CodeType;

public enum CodeType {
	AES("AES", "AES加密类型"), MD5("MD5", "MD5加密类型");

	private String code;
	private String description;

	private CodeType(String code, String description) {
		this.code = code;
		this.description = description;
		com.o2o.framework.core.util.JavaEnumUtils.put(getClass().getName()
				+ code, this);
	}

	public static CodeType valueByCode(String code) {
		Object obj = com.o2o.framework.core.util.JavaEnumUtils
				.get(CodeType.class.getName() + code);
		if (null != obj) {
			return (CodeType) obj;
		}
		return AES;
	}

	public String getCode() {
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

