package com.easy.common.core.runtime;

import com.google.gson.annotations.Expose;

public class ProductEnvironment extends BaseModel {
	private static final long serialVersionUID = -4693571050861188847L;
	@Expose
	private String clientIp;
	@Expose
	private String serverIp;
	@Expose
	private String serverName;
	@Expose
	private String proxyIP;
	@Expose
	private String proxyPort;
	@Expose
	private String sessionId;
	@Expose
	private String edmId;
	@Expose
	private String edmType;
	@Expose
	private String partnerId;

	public ProductEnvironment() {
	}
	public ProductEnvironment(String clientIp, String serverIp,
			String serverName) {
		this.clientIp = clientIp;
		this.serverIp = serverIp;
		this.serverName = serverName;
	}
	public String getClientIp() {
		return this.clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getServerIp() {
		return this.serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerName() {
		return this.serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getProxyIP() {
		return this.proxyIP;
	}
	public void setProxyIP(String proxyIP) {
		this.proxyIP = proxyIP;
	}
	public String getProxyPort() {
		return this.proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public boolean useProxy() {
		return this.proxyIP != null;
	}
	public String getSessionId() {
		return this.sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEdmId() {
		return this.edmId;
	}
	public void setEdmId(String edmId) {
		this.edmId = edmId;
	}
	public String getEdmType() {
		return this.edmType;
	}
	public void setEdmType(String edmType) {
		this.edmType = edmType;
	}
	public String getPartnerId() {
		return this.partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
}
