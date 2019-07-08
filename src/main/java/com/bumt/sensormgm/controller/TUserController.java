package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.CommonUtil;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/TUser")
public class TUserController  extends BaseController<TUser>{

	@Autowired
	private TUserService  service;

	@Override
	public BaseService getService() { return service; }





	@Override
	public  void changeInsertEntity(TUser entity){
		String password = entity.getPassword();
		entity.setPassword(CommonUtil.encodeByMD5(password));
	}


	@Override
	public  String checkInsertStatus(TUser entity){
		String cname = entity.getLoginName();
		List<TUser> tUser =  service.getByLoginName(cname);
		if(!CollectionUtils.isEmpty(tUser)){
			return "登录名称不能重复！";
		}

		tUser =  service.getByMobile(entity.getMobile());
		if(!CollectionUtils.isEmpty(tUser)){
			return "手机不能重复！";
		}
		tUser =  service.getByEmail(entity.getEmail());
		if(!CollectionUtils.isEmpty(tUser)){
			return "邮箱不能重复！";
		}

		//登录名 手机 邮箱
		return "";
	}


	@Override
	public  String checkUpdateStatus(TUser entity){

		long id = entity.getId();
		String cname = entity.getLoginName();
		List<TUser> tUser =  service.checkUpdateStatus(cname,entity.getMobile(),entity.getEmail(),id);
		if(!CollectionUtils.isEmpty(tUser)){
			for(TUser u : tUser) {
				if(u.getLoginName().equals(entity.getLoginName())) {
					return "登录名称不能重复！";
				}
				if(u.getMobile().equals(entity.getMobile())) {
					return "手机号不能重复！";
				}
				if(u.getEmail().equals(entity.getEmail())) {
					return "邮箱不能重复！";
				}
			}
		}
		return "";
	}

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
