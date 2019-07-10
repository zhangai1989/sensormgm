package com.bumt.sensormgm.view;

import java.util.List;
import java.util.Map;

public class HomePageVo {



    private int allCount;

    private int todayCount;
    /**
     * @Description:区域排名
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:00
     * @Version: 1.0
     */
    private List<Map> areaRank;

    /**
     * @Description:企业排名
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:00
     * @Version: 1.0
     */
    private List<Map> enterpriseRank;

    /**
     * @Description:企业超标
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:27
     * @Version: 1.0
     */
    private List<String> enterpriseBeyond;

    /**
     * @Description:监测点位统计
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:27
     * @Version: 1.0
     */
    private Map deviceStatistics;


    /**
     * @Description:当前设备超标占比
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:00
     * @Version: 1.0
     */
    private Map deviceBeyondPer;


    private List<Map> deviceList;

    /**
     * @Description:企业超标
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:27
     * @Version: 1.0
     */
    private List<String> lastLogsData;


    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(int todayCount) {
        this.todayCount = todayCount;
    }

    public List<String> getLastLogsData() {
        return lastLogsData;
    }

    public void setLastLogsData(List<String> lastLogsData) {
        this.lastLogsData = lastLogsData;
    }

    public List<Map> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Map> deviceList) {
        this.deviceList = deviceList;
    }

    public HomePageVo() {
    }

    public List<Map> getAreaRank() {
        return areaRank;
    }

    public void setAreaRank(List<Map> areaRank) {
        this.areaRank = areaRank;
    }

    public List<Map> getEnterpriseRank() {
        return enterpriseRank;
    }

    public void setEnterpriseRank(List<Map> enterpriseRank) {
        this.enterpriseRank = enterpriseRank;
    }

    public List<String> getEnterpriseBeyond() {
        return enterpriseBeyond;
    }

    public void setEnterpriseBeyond(List<String> enterpriseBeyond) {
        this.enterpriseBeyond = enterpriseBeyond;
    }


    public Map getDeviceStatistics() {
        return deviceStatistics;
    }

    public void setDeviceStatistics(Map deviceStatistics) {
        this.deviceStatistics = deviceStatistics;
    }

    public Map getDeviceBeyondPer() {
        return deviceBeyondPer;
    }

    public void setDeviceBeyondPer(Map deviceBeyondPer) {
        this.deviceBeyondPer = deviceBeyondPer;
    }
}
