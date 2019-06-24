package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.service.TUserService;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TUserDao;

import javax.annotation.Resource;

@Service
public class TUserServiceImpl extends BaseServiceImpl implements TUserService  {

	@Resource
	private TUserDao dao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }
}
