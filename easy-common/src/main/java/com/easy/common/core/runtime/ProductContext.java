package com.easy.common.core.runtime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductContext {

	@Expose
	private String url;
	@Expose
	private String requestId;
	@Expose
	private String productCode;
	@Expose
	private String productVersion;
	@Expose
	private List<String[]> serviceCodeStack;
	@SerializedName("env")
	@Expose
	private ProductEnvironment environment;
	@SerializedName("user")
	@Expose
	private ProductUser user;
	@Expose
	private boolean authenticated = false;
	@Expose
	private int logonType = LogonType.ZHANNEI.value();

	@Expose
	private Date time;

	@Expose
	private String activityId;

	@Expose
	private String referer;
	private transient Map<String, Object> context = null;

	public ProductContext() {
		this(false);
	}

	public ProductContext(boolean authenticated) {
		this.authenticated = authenticated;
		this.environment = new ProductEnvironment();
		this.user = new ProductUser();
		this.time = new Date();
		this.requestId = UUID.randomUUID().toString();
	}

	public ProductEnvironment getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(ProductEnvironment environment) {
		this.environment = environment;
	}

	public ProductUser getUser() {
		return this.user;
	}

	public void setUser(ProductUser user) {
		this.user = user;
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductVersion() {
		return this.productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public List<String[]> getServiceCodeStack() {
		return this.serviceCodeStack;
	}

	public void setServiceCodeStack(List<String[]> serviceCodeStack) {
		this.serviceCodeStack = serviceCodeStack;
	}

	public void addServiceCodeAndVersion(String code, String version) {
		if (null == this.serviceCodeStack) {
			this.serviceCodeStack = new ArrayList<String[]>();
		}
		this.serviceCodeStack.add(new String[] { code, version });
	}

	public String[] lookCurrentServiceCodeAndVersion() {
		if (null == this.serviceCodeStack) {
			return new String[] { "", "" };
		}
		return (String[]) this.serviceCodeStack.get(this.serviceCodeStack
				.size() - 1);
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getReferer() {
		return this.referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public int getLogonType() {
		return this.logonType;
	}

	public void setLogonType(int logonType) {
		this.logonType = logonType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void put(String key, Object value) {
		if (null == this.context) {
			this.context = Collections.synchronizedMap(new HashMap<String, Object>());
		}
		this.context.put(key, value);
	}

	public Object find(String key) {
		if (null == this.context) {
			this.context = Collections.synchronizedMap(new HashMap<String, Object>());
		}
		return this.context.get(key);
	}

	public void clean() {
		if (null != this.context)
			this.context.clear();
	}
}