package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.bumt.sensormgm.util.CommonUtil.getCodeByNowDateTime;

@RestController
@RequestMapping("/api/TUser")
public class TUserController  extends BaseController<TUser>{

	@Autowired
	private TUserService  service;

	@Override
	public BaseService getService() { return service; }


//	public void changeUpdateEntity(TUser entity) {
//		entity.setPassword("");
//	}

	@ResponseBody
	@RequestMapping(value = "/alertPwd", method = RequestMethod.POST)
	public Object alertPwd(@RequestBody TUser entity, HttpSession session){
		entity.setPassword("010a0c09090a090b0e060007020f080e");
		return new ResultUtil<>().setData(getService().updateByPrimaryKeySelective(entity,session));
	}

}
