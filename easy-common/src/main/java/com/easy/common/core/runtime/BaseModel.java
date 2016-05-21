package com.easy.common.core.runtime;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = 8102332839758942806L;

	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
		}
		return super.toString();
	}
}
