package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.dao.TDeviceDao;
import com.bumt.sensormgm.dao.TEnterpriseDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import com.bumt.sensormgm.service.TDeviceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class TDeviceServiceImpl extends BaseServiceImpl implements TDeviceService  {

	@Resource
	private TDeviceDao dao;
	@Resource
	private TEnterpriseDao tEnterpriseDao;
	@Resource
	private TAreaService areaService;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }

	@Override
	public Object getPageListBySqlAndCondition(Map<String, Object> entity,HttpSession session) {


		String enterprise="";
		if(!StringUtils.isEmpty(entity.get("enterprise"))){
			enterprise =entity.get("enterprise").toString();
		}
		String status="";
		if(!StringUtils.isEmpty(entity.get("status"))){
			status =entity.get("status").toString();
		}
		String deviceCode = "";
		if(!StringUtils.isEmpty(entity.get("deviceCode"))){
			deviceCode =entity.get("deviceCode").toString();
		}

		String areaId="";
		if(!StringUtils.isEmpty(entity.get("areaId"))){
			areaId =entity.get("areaId").toString();
		}else{
			TUser tUser = (TUser) session.getAttribute("user");
			if(tUser!=null&&tUser.getLevel()==3){
				areaId = tUser.getAreaId();
			}
		}

		int pageNum =Integer.parseInt(entity.get("pageNum").toString());
		int pageSize =Integer.parseInt(entity.get("pageSize").toString());
		int start = (pageNum-1)*pageSize;
		List<TDevice> dataList =  dao.getPageListBySqlAndCondition(enterprise,status,areaId, deviceCode, start,pageSize );
//		for(TDevice tDevice:dataList){
//			if(tDevice.getEnterpriseId()!=null){
//				TEnterprise tEnterprise = tEnterpriseDao.findById(tDevice.getEnterpriseId()).get();
//				if(tEnterprise!=null){
//					tDevice.setEnterpriseName(tEnterprise.getName());
//				}
//			}
//		}
		int total =  dao.getTotalBySqlAndCondition(enterprise,status,areaId, deviceCode);
		Pageable pageable = PageRequest.of((pageNum-1), pageSize);
		Page<TDevice> page = new PageImpl(dataList,pageable,total);
		return page;
	}

	@Override
	public TDevice getByEnterpriseId(Long enterpriseId) {
		return dao.getByEnterpriseId(enterpriseId);
	}

	@Override
	public Object getDeviceList(Long areaId) {
		List<TArea> userAreas = areaService.getUserAreas(areaId, true);
		Set<Long> areaIds = new HashSet<>();
		for(TArea area : userAreas) {
			if(area.getLevel().equals("2")) {
				areaIds.add(area.getId());
			}
		}
		if(CollectionUtils.isEmpty(areaIds)) return new ArrayList<>();
		return dao.getDeviceListByAreas(areaIds);
	}
}
