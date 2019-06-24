package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.service.TDeviceService;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TDeviceDao;

import javax.annotation.Resource;

@Service
public class TDeviceServiceImpl extends BaseServiceImpl implements TDeviceService  {

	@Resource
	private TDeviceDao dao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }
}
