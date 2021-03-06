package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import com.bumt.sensormgm.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:     zhangai
 * @CreateDate: 2019/6/24 18:47
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/TArea")
@Api(value = "TAreaController|区域管理")
public class TAreaController  extends BaseController<TArea>{

	@Autowired
	private TAreaService  service;

	@Override
	public BaseService getService() { return service; }

	/**
	 * @Description:条件查询
	 * @Author:     zhangai
	 * @CreateDate: 2019/6/25 14:31
	 * @Version: 1.0
	 */
	@Override
	public Specification<TArea> changeConditions(Map<String, Object> entity) {
		Specification<TArea> querySpecification = (root, criteriaQuery,criteriaBuilder)-> {
			List<Predicate> list = new ArrayList<Predicate>();
			if(!StringUtils.isEmpty(entity.get("areaId"))){
				list.add(criteriaBuilder.like(root.get("areaId").as(String.class), "%" + entity.get("areaId") + "%"));
			}

			return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
		};
		return querySpecification;
	}

	@ResponseBody
	@RequestMapping(value = "/getAreaList", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
	@ApiOperation(value="获取区域下拉框列表", notes="")
	@ApiImplicitParam(paramType="query", name = "areaId", value = "区域id", required = true, dataType = "String")
	public Object getAreaList(){
		return new ResultUtil<>().setData(service.getAreaList());
	}

	@ResponseBody
	@RequestMapping(value = "/getAreaListBySession", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
	@ApiOperation(value="获取区域下拉框列表", notes="")
	@ApiImplicitParam(paramType="query", name = "areaId", value = "区域id", required = true, dataType = "String")
	public Object getAreaListBySession(HttpSession session){
		return new ResultUtil<>().setData(service.getAreaListBySession(session));
	}


	@ResponseBody
	@RequestMapping(value = "/getAreaTreeByAreaId", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
	@ApiOperation(value="获取区域下拉树", notes="")
	@ApiImplicitParam(paramType="query", name = "areaId", value = "区域id", required = true, dataType = "String")
	public Object getAreaTreeByAreaId(@RequestParam("areaId") String areaId){
		return new ResultUtil<>().setData(service.getAreaTreeByAreaId(areaId));
	}
}
