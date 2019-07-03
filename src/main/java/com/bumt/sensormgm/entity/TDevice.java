package com.bumt.sensormgm.entity;

import com.bumt.sensormgm.common.pojo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 设备表 
 */
@Entity
@Table(name = "t_device")
public class TDevice extends BaseEntity implements Serializable {

	/**
	 * 企业ID
	 */

	@Column(name = "enterprise_id", nullable = false)
	private Long enterpriseId;

	/**
	 * 企业名称
	 */
	@Transient
//	@Column(name = "enterprise_name", nullable = false)
	private String enterpriseName;

	/**
	 * 设备编码
	 */
	@Column(name = "device_code", nullable = false)
	private String deviceCode;

	/**
	 * 设备名
	 */
	@Column(name = "device_name", nullable = false)
	private String deviceName;

	/**
	 * 手机号
	 */
	@Column(name = "mobile", nullable = true)
	private String mobile;

	/**
	 * 当前连接主机
	 */
	@Column(name = "host", nullable = true)
	private String host;

	/**
	 * 状态 ONLINE/OFFLINE
	 */
	@Column(name = "status", nullable = false)
	private String status;

	/**
	 * 油烟浓度
	 */
	@Column(name = "lampblack", nullable = false)
	private Double lampblack;

	/**
	 * 油烟浓度标准值
	 */
	@Column(name = "lampblack_standard", nullable = false)
	private Double lampblackStandard;

	/**
	 * 油烟浓度预警阀值
	 */
	@Column(name = "lampblack_warning", nullable = false)
	private Double lampblackWarning;

	/**
	 * 烟气温度
	 */
	@Column(name = "temp", nullable = false)
	private Double temp;

	/**
	 * 烟气温度
	 */
	@Column(name = "humidity", nullable = false)
	private Double humidity;

	/**
	 * 风机状态 0:关 1:开
	 */
	@Column(name = "fan_status", nullable = false)
	private Boolean fanStatus;

	/**
	 * 风机电流
	 */
	@Column(name = "fan_elec", nullable = false)
	private Double fanElec;

	/**
	 * 净化器状态 0:关 1:开
	 */
	@Column(name = "purifier_status", nullable = false)
	private Boolean purifierStatus;

	/**
	 * 净化器电流
	 */
	@Column(name = "purifier_elec", nullable = false)
	private Double purifierElec;

	/**
	 * 最后上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "last_upload_time", nullable = true)
	private java.util.Date lastUploadTime;

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
	@Column(name = "create_time", nullable = true)
	private java.util.Date createTime;

	/**
	 * 上线时间
	 */
	@Column(name = "online_time", nullable = true)
	private java.util.Date onlineTime;

	/**
	 * 离线时间
	 */
	@Column(name = "offline_time", nullable = true)
	private java.util.Date offlineTime;

	public Long getEnterpriseId() {
		return this.enterpriseId;
	}
	
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public String getDeviceCode() {
		return this.deviceCode;
	}
	
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	public String getDeviceName() {
		return this.deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Double getLampblack() {
		return this.lampblack;
	}
	
	public void setLampblack(Double lampblack) {
		this.lampblack = lampblack;
	}
	
	public Double getLampblackStandard() {
		return this.lampblackStandard;
	}
	
	public void setLampblackStandard(Double lampblackStandard) {
		this.lampblackStandard = lampblackStandard;
	}
	
	public Double getLampblackWarning() {
		return this.lampblackWarning;
	}
	
	public void setLampblackWarning(Double lampblackWarning) {
		this.lampblackWarning = lampblackWarning;
	}
	
	public Double getTemp() {
		return this.temp;
	}
	
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	
	public Double getHumidity() {
		return this.humidity;
	}
	
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	public Boolean getFanStatus() {
		return this.fanStatus;
	}
	
	public void setFanStatus(Boolean fanStatus) {
		this.fanStatus = fanStatus;
	}
	
	public Double getFanElec() {
		return this.fanElec;
	}
	
	public void setFanElec(Double fanElec) {
		this.fanElec = fanElec;
	}
	
	public Boolean getPurifierStatus() {
		return this.purifierStatus;
	}
	
	public void setPurifierStatus(Boolean purifierStatus) {
		this.purifierStatus = purifierStatus;
	}
	
	public Double getPurifierElec() {
		return this.purifierElec;
	}
	
	public void setPurifierElec(Double purifierElec) {
		this.purifierElec = purifierElec;
	}
	
	public java.util.Date getLastUploadTime() {
		return this.lastUploadTime;
	}
	
	public void setLastUploadTime(java.util.Date lastUploadTime) {
		this.lastUploadTime = lastUploadTime;
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
	
	public java.util.Date getOnlineTime() {
		return this.onlineTime;
	}
	
	public void setOnlineTime(java.util.Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public java.util.Date getOfflineTime() {
		return this.offlineTime;
	}
	
	public void setOfflineTime(java.util.Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
}
