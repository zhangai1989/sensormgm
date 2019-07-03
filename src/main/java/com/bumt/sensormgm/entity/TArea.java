package com.bumt.sensormgm.entity;

import com.bumt.sensormgm.common.pojo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 区域表 
 */
@Entity
@Table(name = "t_area")
public class TArea extends BaseEntity implements Serializable {

	/**
	 * 等级 1:大区(如成都市) 2:办事处
	 */
	@Column(name = "level", nullable = false)
	private String level;

	/**
	 * 父ID
	 */
	@Column(name = "parent_id", nullable = false)
	private Long parentId;

	/**
	 * 数据组名
	 */
	@Column(name = "name", nullable = false)
	private String name;

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

	public String getLevel() {
		return this.level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
