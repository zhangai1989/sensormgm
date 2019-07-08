package com.bumt.sensormgm.entity;

import com.bumt.sensormgm.common.pojo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 上传日志表 
 */
@Entity
@Table(name = "t_upload_log")
public class TUploadLog extends BaseEntity implements Serializable {

	/**
	 * 设备编码
	 */
	@Column(name = "device_code", nullable = false)
	private String deviceCode;

	/**
	 * 油烟浓度
	 */
	@Column(name = "lampblack", nullable = false)
	private Double lampblack;

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
	 * 上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "upload_time", nullable = true)
	private java.util.Date uploadTime;

	/**
	 * 删除标记 0:未删除 1:已删除
	 */
	@Column(name = "delete_flag", nullable = false)
	private int deleteFlag;

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeviceCode() {
		return this.deviceCode;
	}
	
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	public Double getLampblack() {
		return this.lampblack;
	}
	
	public void setLampblack(Double lampblack) {
		this.lampblack = lampblack;
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
	
	public java.util.Date getUploadTime() {
		return this.uploadTime;
	}
	
	public void setUploadTime(java.util.Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
