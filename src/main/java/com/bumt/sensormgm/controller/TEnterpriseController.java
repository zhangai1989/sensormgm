package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.service.TEnterpriseService;
import com.bumt.sensormgm.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bumt.sensormgm.util.CommonUtil.getCodeByNowDateTime;

@RestController
@RequestMapping("/api/TEnterprise")
public class TEnterpriseController  extends BaseController<TEnterprise>{

	@Autowired
	private TEnterpriseService  service;

	@Override
	public BaseService getService() { return service; }



	/**
	 * @Description:条件查询
	 * @Author:     zhangai
	 * @CreateDate: 2019/6/25 14:31
	 * @Version: 1.0
	 */
	@Override
	public Specification<TEnterprise> changeConditions(Map<String, Object> entity) {
		Specification<TEnterprise> querySpecification = (root, criteriaQuery,criteriaBuilder)-> {
			List<Predicate> list = new ArrayList<Predicate>();
			if(!StringUtils.isEmpty(entity.get("areaId"))){
				list.add(criteriaBuilder.like(root.get("areaId").as(String.class), "%" + entity.get("areaId") + "%"));
			}
			if(!StringUtils.isEmpty(entity.get("name"))){
				list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + entity.get("name") + "%"));
			}
			return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
		};
		return querySpecification;
	}


	@ResponseBody
	@RequestMapping(value = "/getEnterpriseBySession", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
	@ApiOperation(value="获取企业下拉框列表", notes="")
	@ApiImplicitParam(paramType="query", name = "areaId", value = "区域id", required = true, dataType = "String")
	public Object getEnterpriseBySession(HttpSession session){
		return new ResultUtil<>().setData(service.getEnterpriseBySession(session));
	}


	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@Override
	public Object insert(@RequestBody TEnterprise entity,HttpSession session){
		entity.setCode(getCodeByNowDateTime());
		entity.setDeleteFlag(0);
		//企业名称不能重复
		String errorMsg = checkUpdateStatus2(entity);
		if(!StringUtils.isEmpty(errorMsg)){
			return new ResultUtil<>().setErrorMsg(errorMsg);
		}
		return new ResultUtil<>().setData(getService().insert(entity,session));
	}

	public  String checkUpdateStatus2(TEnterprise entity){
		String name = entity.getName();
		List<TEnterprise> tEnterprises =  service.getAllListByName(name);
		if(tEnterprises!=null||tEnterprises.size()>0){
			return "企业名称不能重复！";
		}
		return "";
	}
}
