package com.easy.common.core.runtime;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class ProductUser extends BaseModel {
	private static final long serialVersionUID = 490559999680222434L;
	@Expose
	private String logonId;
	@Expose
	private String platformId;
	@Expose
	private String userId;
	@Expose
	private String nickName;
	@Expose
	private String userEmail;
	@Expose
	private String lastLoginIP;
	@Expose
	private Date lastLoginDate;
	private String[] roles;
	public String getLogonId() {
		return this.logonId;
	}
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}
	public String getPlatformId() {
		return this.platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return this.userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLastLoginIP() {
		return this.lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String[] getRoles() {
		return this.roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
}
