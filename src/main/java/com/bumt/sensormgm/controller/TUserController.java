package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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


	/**
	 *@author : zhangai
	 *@Date : 11:31 2019/6/24
	 * @Parm: pageNum第几页  pageSize一页的数量 因为存在分页信息的参数，无法使用对象接收
	 *@描述：获取单页信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageListByCondition2", produces = {"application/json;charset=UTF-8"})
	public Object getPageListByCondition2(@RequestBody Map<String,Object> entity,HttpSession httpSession){

		return service.getPageListByCondition2(entity,httpSession);
	}

	@ResponseBody
	@RequestMapping(value = "/alertPwd", method = RequestMethod.POST)
	public Object alertPwd(@RequestBody TUser entity, HttpSession session){
		entity.setPassword("010a0c09090a090b0e060007020f080e");
		return new ResultUtil<>().setData(getService().updateByPrimaryKeySelective(entity,session));
	}

}
