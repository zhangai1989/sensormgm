package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.service.TUploadLogService;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TUploadLogDao;

import javax.annotation.Resource;

@Service
public class TUploadLogServiceImpl extends BaseServiceImpl implements TUploadLogService  {

	@Resource
	private TUploadLogDao dao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }
}
