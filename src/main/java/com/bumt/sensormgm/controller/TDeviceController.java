package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.service.TDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/TDevice")
public class TDeviceController  extends BaseController<TDevice>{

	@Autowired
	private TDeviceService  service;

	@Override
	public BaseService getService() { return service; }

}
