package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.service.TEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/TEnterprise")
public class TEnterpriseController  extends BaseController<TEnterprise>{

	@Autowired
	private TEnterpriseService  service;

	@Override
	public BaseService getService() { return service; }

}
