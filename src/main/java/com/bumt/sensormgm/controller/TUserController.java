package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/TUser")
public class TUserController  extends BaseController<TUser>{

	@Autowired
	private TUserService  service;

	@Override
	public BaseService getService() { return service; }


	@ResponseBody
	@RequestMapping(value = "/getPageListByCondition2", produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	public Object getPageListByCondition2(@RequestBody Map<String,Object> entity, HttpSession session){

		return new ResultUtil<>().setData("");
	}

}
