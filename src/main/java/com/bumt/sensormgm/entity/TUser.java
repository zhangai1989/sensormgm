package com.bumt.sensormgm.entity;

import com.bumt.sensormgm.common.pojo.BaseEntity;
import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户表 
 */
@Entity
@Table(name = "t_user")
public class TUser extends BaseEntity implements Serializable {

	/**
	 * 用户等级 1:超级管理员 2:市级(如信阳市) 3:办事处 4:企业
	 */
	@Column(name = "level", nullable = false)
	private Boolean level;

	/**
	 * 登录名
	 */
	@Column(name = "login_name", nullable = false)
	private String loginName;

	/**
	 * 登录密码
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * 用户真实姓名
	 */
	@Column(name = "cname", nullable = false)
	private String cname;

	/**
	 * 手机
	 */
	@Column(name = "mobile", nullable = false)
	private String mobile;

	/**
	 * 邮箱
	 */
	@Column(name = "email", nullable = false)
	private String email;

	/**
	 * 区域id
	 */
	@Column(name = "area_id", nullable = false)
	private String areaId;

	/**
	 * 删除标记 0:未删除 1:已删除
	 */
	@Column(name = "delete_flag", nullable = false)
	private Boolean deleteFlag;

	/**
	 * 创建人ID
	 */
	@Column(name = "creator_id", nullable = true)
	private Long creatorId;

	/**
	 * 创建人姓名
	 */
	@Column(name = "creator_name", nullable = true)
	private String creatorName;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false)
	private java.util.Date createTime;

	/**
	 * 修改人id
	 */
	@Column(name = "modifier_id", nullable = true)
	private Long modifierId;

	/**
	 * null
	 */
	@Column(name = "modifier_name", nullable = true)
	private String modifierName;

	/**
	 * 修改时间
	 */
	@Column(name = "modify_time", nullable = true)
	private java.util.Date modifyTime;
	
	public Boolean getLevel() {
		return this.level;
	}
	
	public void setLevel(Boolean level) {
		this.level = level;
	}
	
	public String getLoginName() {
		return this.loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCname() {
		return this.cname;
	}
	
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAreaId() {
		return this.areaId;
	}
	
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public Boolean getDeleteFlag() {
		return this.deleteFlag;
	}
	
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getCreatorName() {
		return this.creatorName;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getModifierId() {
		return this.modifierId;
	}
	
	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}
	
	public String getModifierName() {
		return this.modifierName;
	}
	
	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
	
	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
