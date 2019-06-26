package com.bumt.sensormgm.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TDeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TDeviceDao;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class TDeviceServiceImpl extends BaseServiceImpl implements TDeviceService  {

	@Resource
	private TDeviceDao dao;

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

		String areaId="";
		if(!StringUtils.isEmpty(entity.get("areaId"))){
			areaId =entity.get("areaId").toString();
		}else{
			TUser tUser = (TUser) session.getAttribute("user");
			if(tUser!=null&&tUser.getLevel()==3){
				areaId = tUser.getAreaId();
			}
		}


		PageHelper.startPage(Integer.parseInt(entity.get("pageNum").toString()), Integer.parseInt(entity.get("pageSize").toString()));
		List<TDevice> dataList =  dao.getPageListBySqlAndCondition(enterprise,status,areaId);
		PageInfo<TDevice> appsPageInfo = new PageInfo<>(dataList);

		Pageable pageable = PageRequest.of(Integer.parseInt(entity.get("pageNum").toString()), Integer.parseInt(entity.get("pageSize").toString()));
		Page<Map> page =  dao.getPageListBySqlAndCondition2(enterprise,status,areaId,pageable);


		return appsPageInfo;
	}

	@Override
	public TDevice getByEnterpriseId(Long enterpriseId) {
		return dao.getByEnterpriseId(enterpriseId);
	}
}
