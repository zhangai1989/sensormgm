package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TUploadLog;
import com.bumt.sensormgm.service.TUploadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/TUploadLog")
public class TUploadLogController  extends BaseController<TUploadLog>{

	@Autowired
	private TUploadLogService  service;

	@Override
	public BaseService getService() { return service; }

}
