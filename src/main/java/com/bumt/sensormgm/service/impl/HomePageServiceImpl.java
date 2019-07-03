package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.dao.TAreaDao;
import com.bumt.sensormgm.dao.TDeviceDao;
import com.bumt.sensormgm.dao.TEnterpriseDao;
import com.bumt.sensormgm.dao.TUploadLogDao;
import com.bumt.sensormgm.service.HomePageService;
import com.bumt.sensormgm.view.HomePageVo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class HomePageServiceImpl  implements HomePageService {

	@Resource
	private TAreaDao dao;

	@Resource
	private TEnterpriseDao tEnterpriseDao;

	@Resource
	private TDeviceDao tDeviceDao;

    @Resource
    private TUploadLogDao tUploadLogDao;


	@Override
	public Object getHomePageDataByAreaId(String areaId) {

		if(StringUtil.isEmpty(areaId)){
			return null;
		}
        HomePageVo homePageVo = new HomePageVo();

        //获取区域排名
        List<Map> areaRank =  tDeviceDao.getAreaRank();

        //获取企业排名
        List<Map> enterpriseRank =  tDeviceDao.getDeviceDataByAreaId(areaId);

        //获取企业超标信息
        List<Map> enterpriseBeyondList =  tDeviceDao.getEnterpriseBeyondByAreaId(areaId);

        List<String> enterpriseBeyond =  new ArrayList<>();
        if(enterpriseBeyondList!=null&&enterpriseBeyondList.size()>0){
            for(Map enterpriseBeyondMap:enterpriseBeyondList){
                String name = enterpriseBeyondMap.get("name").toString();

                String lastUploadTime="";
                if(enterpriseBeyondMap.get("last_upload_time")!=null){
                    lastUploadTime = enterpriseBeyondMap.get("last_upload_time").toString().substring(0,19);
                }

                String lampblack = enterpriseBeyondMap.get("lampblack").toString();
                String per =  enterpriseBeyondMap.get("per").toString();
                String enterpriseBeyondStr = "【"+lastUploadTime+"】"+name+
                        ",油烟浓度:"+lampblack+"mg/m3,超标倍数："+per;

                enterpriseBeyond.add(enterpriseBeyondStr);

            }
        }

        //监测点位统计
        List<Map> deviceStatisticsList =  tDeviceDao.getDeviceStatisticsByAreaId(areaId);
        Map deviceStatisticsResultMap = new HashMap();

        int allCount = Integer.parseInt(deviceStatisticsList.get(0).get("count").toString());
        int onlineCount = Integer.parseInt(deviceStatisticsList.get(1).get("count").toString());
        int earlyWarningCount = Integer.parseInt(deviceStatisticsList.get(2).get("count").toString());
        int beyondCount = Integer.parseInt(deviceStatisticsList.get(3).get("count").toString());

        deviceStatisticsResultMap.put("allCount",deviceStatisticsList.get(0).get("count"));
        deviceStatisticsResultMap.put("onlineCount",onlineCount);
        deviceStatisticsResultMap.put("offlineCount",allCount-onlineCount);
        int normalCount =onlineCount-earlyWarningCount-beyondCount;
        deviceStatisticsResultMap.put("normalCount",normalCount);
        deviceStatisticsResultMap.put("earlyWarningCount",earlyWarningCount);
        deviceStatisticsResultMap.put("beyondCount",beyondCount);

        //当前设备超标占比

        Map deviceBeyondPerMap = new HashMap();
        if(onlineCount==0){
            deviceBeyondPerMap.put("normal",0);
            deviceBeyondPerMap.put("earlyWarning",0);
            deviceBeyondPerMap.put("beyond",0);
        }else{
            deviceBeyondPerMap.put("normal",normalCount*1.0/onlineCount);
            deviceBeyondPerMap.put("earlyWarning",earlyWarningCount*1.0/onlineCount);
            deviceBeyondPerMap.put("beyond",beyondCount*1.0/onlineCount);

        }

        //最近100条数据显示

        List<Map> getLogList =  tUploadLogDao.getLogListByAreaId(areaId);

        List<String> lastLogsData =  new ArrayList<>();
        if(getLogList!=null&&getLogList.size()>0){
            for(Map log:getLogList){
                String name = log.get("name").toString();

                String uploadTime="";
                if(log.get("upload_time")!=null){
                    uploadTime = log.get("upload_time").toString().substring(0,19);
                }

                String lampblack = log.get("lampblack").toString();
                String logStr = "【"+uploadTime+"】,"+name+
                        "上传数据,油烟浓度:"+lampblack+"mg/m3.";
                lastLogsData.add(logStr);
            }
        }

        //获取改区域的设备信息
        List<Map> deviceList = tDeviceDao.getDeviceListByAreaId(areaId);


        homePageVo.setAreaRank(areaRank);
        homePageVo.setEnterpriseRank(enterpriseRank);
        homePageVo.setEnterpriseBeyond(enterpriseBeyond);
        homePageVo.setDeviceStatistics(deviceStatisticsResultMap);
        homePageVo.setDeviceBeyondPer(deviceBeyondPerMap);
        homePageVo.setDeviceList(deviceList);
        homePageVo.setLastLogsData(lastLogsData);
		return homePageVo;
	}

}
