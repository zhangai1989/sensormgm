package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import com.bumt.sensormgm.service.TEnterpriseService;
import com.bumt.sensormgm.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
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

	@Autowired
	private TEnterpriseService tEnterpriseService;

	@Override
	public  String checkDeleteStatus(String id){

		List<TEnterprise> tEnterpriseList = tEnterpriseService.findByAreaId(id);
		if(!CollectionUtils.isEmpty(tEnterpriseList)){
			return "改区域下有企业，不能被删除！";
		}
		return "";
	}

	@Override
	public  String checkInsertStatus(TArea entity){


		String cname = entity.getName();
		List<TArea> tArea =  service.findByName(cname);
		if(!CollectionUtils.isEmpty(tArea)){
			return "区域名称不能重复！";
		}
		return "";
	}

	@Override
	public  String checkUpdateStatus(TArea entity){

		long id = entity.getId();
		String name = entity.getName();
		List<TArea> tUser =  service.findByNameAndIdNot(name,id);
		if(!CollectionUtils.isEmpty(tUser)){
			return "区域名称不能重复！";
		}
		return "";
	}
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

			if(!StringUtils.isEmpty(entity.get("level"))){
				list.add(criteriaBuilder.equal(root.get("level").as(String.class),2));
			}
			list.add(criteriaBuilder.equal(root.get("deleteFlag").as(String.class),0));
			return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
		};
		return querySpecification;
	}

	@ResponseBody
	@RequestMapping(value = "/getAreaList", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
	@ApiOperation(value="获取区域下拉框列表", notes="")
	@ApiImplicitParam(paramType="query", name = "areaId", value = "区域id", required = true, dataType = "String")
	public Object getAreaList(HttpSession session){
		return new ResultUtil<>().setData(service.getAreaList(session));
	}


	/**
	 *@author : zhangai
	 *@Date : 11:31 2019/6/24
	 * @Parm: pageNum第几页  pageSize一页的数量 因为存在分页信息的参数，无法使用对象接收
	 *@描述：获取单页信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getAreaList2", produces = {"application/json;charset=UTF-8"})
	public Object getAreaList2(@RequestBody Map<String,Object> entity,HttpSession session){

		int pageNum = Integer.parseInt(entity.get("pageNum").toString());
		int pageSize = Integer.parseInt(entity.get("pageSize").toString());
		Pageable pageable= PageRequest.of((pageNum-1),pageSize,changeSort());

		Specification<TArea> querySpecification = (root, criteriaQuery,criteriaBuilder)-> {
			List<Predicate> list = new ArrayList<Predicate>();
			TUser tUser = (TUser) session.getAttribute("user");
			if(tUser!=null){
				if(tUser.getLevel()==3){
					list.add(criteriaBuilder.equal(root.get("id").as(String.class),tUser.getAreaId()));
				}
			}
			list.add(criteriaBuilder.equal(root.get("level").as(String.class),2));
			list.add(criteriaBuilder.equal(root.get("deleteFlag").as(String.class),0));
			return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
		};

		return new ResultUtil<>().setData(getService().getPageListByCondition(querySpecification,pageable));
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
	public Object getAreaTreeByAreaId(@RequestParam("areaId") String areaId,
									  @RequestParam(required = false, defaultValue = "false") Boolean containEnterprise){
		return new ResultUtil<>().setData(service.getAreaTreeByAreaId(areaId, containEnterprise));
	}

}
