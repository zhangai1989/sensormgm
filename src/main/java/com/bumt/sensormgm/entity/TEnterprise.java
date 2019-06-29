package com.bumt.sensormgm.entity;

import com.bumt.sensormgm.common.pojo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import javax.persistence.*;

/**
 *  
 */
@Entity
@Table(name = "t_enterprise")
public class TEnterprise extends BaseEntity implements Serializable {

	/**
	 * 区域ID
	 */
	@Column(name = "area_id", nullable = false)
	private Long areaId;

	/**
	 * 企业编码
	 */
	@Column(name = "code", nullable = false)
	private String code;

	/**
	 * 数据组名
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * 经度
	 */
	@Column(name = "longitude", nullable = false)
	private Double longitude;

	/**
	 * 纬度
	 */
	@Column(name = "latitude", nullable = false)
	private Double latitude;

	/**
	 * 详细地址
	 */
	@Column(name = "address", nullable = false)
	private String address;

	/**
	 * 企业负责人
	 */
	@Column(name = "contact", nullable = false)
	private String contact;

	/**
	 * 企业负责人电话
	 */
	@Column(name = "contact_mobile", nullable = false)
	private String contactMobile;

	/**
	 * 环保负责人
	 */
	@Column(name = "env_contact", nullable = false)
	private String envContact;

	/**
	 * 环保负责人电话
	 */
	@Column(name = "env_contact_mobile", nullable = false)
	private String envContactMobile;

	/**
	 * 删除标记 0:未删除 1:已删除
	 */
	@Column(name = "delete_flag", nullable = false)
	private int deleteFlag;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "modify_time", nullable = true)
	private java.util.Date modifyTime;
	
	public Long getAreaId() {
		return this.areaId;
	}
	
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getContact() {
		return this.contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	

	public String getEnvContact() {
		return this.envContact;
	}
	
	public void setEnvContact(String envContact) {
		this.envContact = envContact;
	}


	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
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


	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getEnvContactMobile() {
		return envContactMobile;
	}

	public void setEnvContactMobile(String envContactMobile) {
		this.envContactMobile = envContactMobile;
	}
}
