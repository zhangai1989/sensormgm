package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.service.TAreaService;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TAreaDao;

import javax.annotation.Resource;

@Service
public class TAreaServiceImpl extends BaseServiceImpl implements TAreaService  {

	@Resource
	private TAreaDao dao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }
}
