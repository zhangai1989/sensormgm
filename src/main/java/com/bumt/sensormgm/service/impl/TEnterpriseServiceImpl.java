package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.service.TEnterpriseService;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TEnterpriseDao;

import javax.annotation.Resource;

@Service
public class TEnterpriseServiceImpl extends BaseServiceImpl implements TEnterpriseService  {

	@Resource
	private TEnterpriseDao dao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }
}
