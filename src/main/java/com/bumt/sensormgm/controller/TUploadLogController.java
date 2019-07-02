package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUploadLog;
import com.bumt.sensormgm.service.TDeviceService;
import com.bumt.sensormgm.service.TEnterpriseService;
import com.bumt.sensormgm.service.TUploadLogService;
import com.bumt.sensormgm.util.CommonUtil;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/TUploadLog")
public class TUploadLogController  extends BaseController<TUploadLog>{

	@Autowired
	private TUploadLogService  service;

	@Override
	public BaseService getService() { return service; }

	@Autowired
	private TDeviceService tDeviceService;
	/**
	 * @Description:条件查询
	 * @Author:     zhangai
	 * @CreateDate: 2019/6/25 14:31
	 * @Version: 1.0
	 */
	@Override
	public Specification<TUploadLog> changeConditions(Map<String, Object> entity) {
		Specification<TUploadLog> querySpecification = (Root<TUploadLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			//根据企业id找到设备编码
			if(!StringUtils.isEmpty(entity.get("enterpriseId"))){
				TDevice tDevice =tDeviceService.getByEnterpriseId(Long.parseLong(entity.get("enterpriseId").toString()));
				if(!StringUtils.isEmpty(tDevice)){
					list.add(criteriaBuilder.equal(root.get("deviceCode").as(String.class), tDevice.getDeviceCode()));
				}
			}

			if(!StringUtils.isEmpty(entity.get("startTime"))&&(!StringUtils.isEmpty(entity.get("endTime")))){
				list.add(criteriaBuilder.between(root.get("uploadTime"), CommonUtil.getDayByString(entity.get("startTime").toString()),CommonUtil.getDayByString(entity.get("endTime").toString())));
			}

			return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
		};
		return querySpecification;
	}


	@ResponseBody
	@RequestMapping(value = "/getBeyondPageListByCondition", produces = {"application/json;charset=UTF-8"})
	public Object getBeyondPageListByCondition(@RequestBody Map<String,Object> entity,HttpSession httpSession){

		return new ResultUtil<>().setData(service.getBeyondPageListByCondition(entity,httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "/getWarningPageListByCondition", produces = {"application/json;charset=UTF-8"})
	public Object getWarningPageListByCondition(@RequestBody Map<String,Object> entity,HttpSession httpSession){

		return new ResultUtil<>().setData(service.getWarningPageListByCondition(entity,httpSession));
	}


	@ResponseBody
	@RequestMapping(value = "/getDataAnalysisByCondition", produces = {"application/json;charset=UTF-8"})
	public Object getDataAnalysisByCondition(@RequestBody Map<String,Object> entity){
		return new ResultUtil<>().setData(service.getDataAnalysisByCondition(entity));
	}

}
