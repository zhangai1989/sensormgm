package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
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

}
