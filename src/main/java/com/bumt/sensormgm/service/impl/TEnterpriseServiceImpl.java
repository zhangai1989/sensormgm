package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.dao.TEnterpriseDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import com.bumt.sensormgm.service.TDeviceService;
import com.bumt.sensormgm.service.TEnterpriseService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TEnterpriseServiceImpl extends BaseServiceImpl implements TEnterpriseService  {

	@Resource
	private TEnterpriseDao dao;
	@Resource
	private TDeviceService deviceService;
	@Resource
	private TAreaService areaService;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }

	@Override
	public List<TEnterprise> getAllListByName(String name) {
		return dao.findByNameAndDeleteFlag(name,0);
	}

	@Override
	public List<TEnterprise> getAllListByCode(String code) {
		return dao.findByCodeAndDeleteFlag(code,0);
	}

	@Override
	public Object getEnterpriseBySession(HttpSession session,String areaId) {
		TUser tUser = (TUser) session.getAttribute("user");
		if(StringUtils.isEmpty(areaId)){
			areaId = tUser.getAreaId();
		}
		return dao.findByAreaIdAndDeleteFlag(Long.parseLong(areaId),0);
	}

	@Override
	public Object save(TEnterprise enterprise, HttpSession session) {
		TEnterprise saveResult = (TEnterprise)insert(enterprise, session);
		return deviceService.insert(buildDevice(saveResult.getId(), saveResult.getCode(), saveResult.getName()), session);
	}

	@Override
	public List<TEnterprise> findByAreaId(String areaId) {
		return dao.findByAreaIdAndDeleteFlag(Long.parseLong(areaId),0);
	}

	@Override
	public List<TEnterprise> findByNameAndIdNot(String name, Long id) {

		return dao.findByNameAndIdNotAndDeleteFlag(name, 0L,0);
	}

	@Override
	public Object getPageListByCondition2(Map<String, Object> entity, HttpSession httpSession) {
		TUser tUser = (TUser) httpSession.getAttribute("user");
		if (StringUtils.isEmpty(tUser)) {
			return new ResultUtil<>().setErrorMsg(4000,"未登录");
		}
		Specification<TEnterprise> specification = (root, criteriaQuery, cb) -> {
			List<Predicate> list = new ArrayList<>();
			String areaId = tUser.getAreaId();
			if(!StringUtils.isEmpty(entity) && !StringUtils.isEmpty(entity.get("areaId"))) {
				areaId = entity.get("areaId").toString();
			}
			List<TArea> listArea = areaService.getUserAreas(Long.valueOf(areaId), true);
			CriteriaBuilder.In<Long> in = cb.in(root.<Long>get("areaId"));
			for (TArea area : listArea) {
				in.value(area.getId());
			}
			list.add(in);
			if (!StringUtils.isEmpty(entity.get("name"))) {
				list.add(cb.like(root.get("name"), "%" + entity.get("name") + "%"));
			}
			if (!StringUtils.isEmpty(entity.get("code"))) {
				list.add(cb.equal(root.<String>get("code"), entity.get("code").toString()));
			}
			list.add(cb.equal(root.<Integer>get("deleteFlag"), 0));
			return cb.and(list.toArray(new Predicate[list.size()]));
		};

		int pageNum = Integer.parseInt(entity.get("pageNum").toString());
		int pageSize = Integer.parseInt(entity.get("pageSize").toString());
		Pageable pageable = PageRequest.of((pageNum - 1), pageSize, new Sort(Sort.Direction.DESC, "id"));

		return new ResultUtil<>().setData(getPageListByCondition(specification, pageable));
	}

	private TDevice buildDevice(Long enterpriseId, String deviceCode, String deviceName) {
		TDevice device = new TDevice();
		device.setEnterpriseId(enterpriseId);
		device.setDeviceCode(deviceCode);
		device.setDeviceName(deviceName);
		device.setStatus("OFFLINE");
		device.setLampblack(0.00);
		device.setLampblackStandard(1.00);
		device.setLampblackWarning(1.00);
		device.setTemp(-10000d);
		device.setHumidity(-10000d);
		device.setFanStatus(false);
		device.setFanElec(-10000d);
		device.setPurifierStatus(false);
		device.setPurifierElec(-10000d);
		return device;
	}

}
