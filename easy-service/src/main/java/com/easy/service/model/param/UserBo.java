package com.easy.service.model.param;

import java.io.Serializable;
import java.util.Date;

public class UserBo extends BaseBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 990230998206104922L;
	

    private Integer id;

    private String username;

    private String phone;

    private String sex;

    private String address;

    private Integer level;

    private Integer scores;

    private String location;

    private String ota;

    private Date gmtCreate;

    private Date gmtModify;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getScores() {
		return scores;
	}

	public void setScores(Integer scores) {
		this.scores = scores;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOta() {
		return ota;
	}

	public void setOta(String ota) {
		this.ota = ota;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
    
}
