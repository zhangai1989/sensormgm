package com.bumt.sensormgm.view;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Entity
public class WarningBeyondVo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double multiple;

    private double lampblackStandard;
    private double lampblack;

    private String uploadTime;

    private String enterpriseName;

    public double getMultiple() {
        return multiple;
    }

    public void setMultiple(double multiple) {
        this.multiple = multiple;
    }

    public double getLampblackStandard() {
        return lampblackStandard;
    }

    public void setLampblackStandard(double lampblackStandard) {
        this.lampblackStandard = lampblackStandard;
    }

    public double getLampblack() {
        return lampblack;
    }

    public void setLampblack(double lampblack) {
        this.lampblack = lampblack;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
