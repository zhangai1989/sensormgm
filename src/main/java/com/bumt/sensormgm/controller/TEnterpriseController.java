package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.common.controller.BaseController;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.service.TEnterpriseService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
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
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@Override
	public Object insert(@RequestBody TEnterprise entity){
		entity.setCode(getCodeByNowDateTime());
		return new ResultUtil<>().setData(getService().insert(entity));
	}

}
