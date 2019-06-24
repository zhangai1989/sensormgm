package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.service.TAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/TArea")
public class TAreaController  extends BaseController<TArea>{

	@Autowired
	private TAreaService  service;

	@Override
	public BaseService getService() { return service; }

}
