package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.pojo.TreeUtil;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.dao.TAreaDao;
import com.bumt.sensormgm.dao.TDeviceDao;
import com.bumt.sensormgm.dao.TEnterpriseDao;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.service.HomePageService;
import com.bumt.sensormgm.view.HomePageVo;
import com.bumt.sensormgm.view.OnePerNode;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class HomePageServiceImpl  implements HomePageService {

	@Resource
	private TAreaDao dao;

	@Resource
	private TEnterpriseDao tEnterpriseDao;

	@Resource
	private TDeviceDao tDeviceDao;
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
                    lastUploadTime = enterpriseBeyondMap.get("last_upload_time").toString();
                }

                String lampblack = enterpriseBeyondMap.get("lampblack").toString();
                String per =  enterpriseBeyondMap.get("per").toString();
                String enterpriseBeyondStr = "【"+lastUploadTime+"】"+name+
                        ",油烟浓度:"+lampblack+"mg/m3,超标倍数："+per;

                enterpriseBeyond.add(enterpriseBeyondStr);

            }
        }

        homePageVo.setAreaRank(areaRank);
        homePageVo.setEnterpriseRank(enterpriseRank);
        homePageVo.setEnterpriseBeyond(enterpriseBeyond);

		return homePageVo;
	}
}
