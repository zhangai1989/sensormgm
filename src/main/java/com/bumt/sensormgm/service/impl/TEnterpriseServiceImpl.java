package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TDeviceService;
import com.bumt.sensormgm.service.TEnterpriseService;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TEnterpriseDao;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class TEnterpriseServiceImpl extends BaseServiceImpl implements TEnterpriseService  {

	@Resource
	private TEnterpriseDao dao;
	@Resource
	private TDeviceService deviceService;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }

	@Override
	public List<TEnterprise> getAllListByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public Object getEnterpriseBySession(HttpSession session) {
		TUser tUser = (TUser) session.getAttribute("user");
		return dao.findByAreaId(Long.parseLong(tUser.getAreaId()));
	}

	@Override
	public void save(TEnterprise enterprise, HttpSession session) {
		TEnterprise saveResult = (TEnterprise)insert(enterprise, session);
		deviceService.insert(buildDevice(saveResult.getId(), saveResult.getCode(), saveResult.getName()), session);
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
