package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.service.TDeviceService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/TDevice")
public class TDeviceController  extends BaseController<TDevice>{

	@Autowired
	private TDeviceService  service;

	@Override
	public BaseService getService() { return service; }




	/**
	 * @Description:覆盖父类的条件查询（涉及到多表查询JPA实现麻烦）
	 * @Author:     zhangai
	 * @CreateDate: 2019/6/26 17:02
	 * @Version: 1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageListByCondition", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	public Object getPageListByCondition(@RequestBody Map<String,Object> entity, HttpSession session){
		return new ResultUtil<>().setData(service.getPageListBySqlAndCondition(entity,session));
	}
}
