package com.bumt.sensormgm.common.controller;


import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : zhangai
 * @desaciption : 公共类，封装一些公共的方法，将最基本的增、删、查、改方法封装到common中
 * 子类只需要继承此类，就不用再写增删查改的方法了
 * @date : Create in 9:59$ 2018/5/4$
 */
public abstract class BaseController<T>{

	/**
	 *@author : zhangai
	 *@Date : 15:33 2018/5/4
	 * @Parm : id 主键
 	 *@描述：根据id获取单条数据的方法
	 */
	@ResponseBody
	@RequestMapping(value = "/getSingleById", produces = {"application/json;charset=UTF-8"})
	public Object getSingleById(@RequestParam("id") String id){
		return new ResultUtil<>().setData(getService().getSingleById(id));
	}

	/**
	 *@author : zhangai
	 *@Date : 11:31 2018/5/9
	 * @Parm: pageNum第几页  pageSize一页的数量
	 *@描述：获取单页信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageList", produces = {"application/json;charset=UTF-8"})
	public Object getPageList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
		return new ResultUtil<>().setData(getService().getPageList(pageNum,pageSize));
	}


	/**
	 *@author : zhangai
	 *@Date : 11:31 2019/6/24
	 * @Parm: pageNum第几页  pageSize一页的数量 因为存在分页信息的参数，无法使用对象接收
	 *@描述：获取单页信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageListByCondition", produces = {"application/json;charset=UTF-8"})
	public Object getPageListByCondition(@RequestBody Map<String,Object> entity){

		int pageNum = Integer.parseInt(entity.get("pageNum").toString());
		int pageSize = Integer.parseInt(entity.get("pageSize").toString());
		Pageable pageable= PageRequest.of((pageNum-1),pageSize,changeSort());
		Specification<T> querySpecification = changeConditions(entity);

		return new ResultUtil<>().setData(getService().getPageListByCondition(querySpecification,pageable));
	}

	public  Sort changeSort(){
		return new Sort(Sort.Direction.DESC,"id");
	}

	public Specification<T> changeConditions(Map<String, Object> entity) {
		Specification<T> querySpecification = (root, criteriaQuery, criteriaBuilder)-> {
			List<Predicate> list = new ArrayList<Predicate>();
			return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
		};
		return querySpecification;
	}


	/**
	 *@author : zhangai
	 *@Date : 11:31 2018/5/9
	 * @Parm:
	 *@描述：获取所有信息
	 */

	@ResponseBody
	@RequestMapping(value = "/getAllList", produces = {"application/json;charset=UTF-8"})
	public Object getAllList(){

		return new ResultUtil<>().setData(getService().getAllList());
	}

	/**
	 *@author : zhangai
	 *@Date : 11:35 2018/5/9
	 *@描述： 新增
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Object insert(@RequestBody T entity,HttpSession session){

		String errorMsg = checkInsertStatus(entity);
		if(!StringUtils.isEmpty(errorMsg)){
			return new ResultUtil<>().setErrorMsg(errorMsg);
		}

		return new ResultUtil<>().setData(getService().insert(entity,session));
	}
	public  String checkInsertStatus(T entity){
		return "";
	}


	/**
	 *@author : zhangai
	 *@Date : 11:35 2018/5/9
	 *@描述： 修改（支持部分字段传入的修改）
	 */
	@ResponseBody
	@RequestMapping(value = "/update",  method = RequestMethod.POST)
	public Object update(@RequestBody T entity,HttpSession session){
		String errorMsg = checkUpdateStatus(entity);
		if(!StringUtils.isEmpty(errorMsg)){
			return new ResultUtil<>().setErrorMsg(errorMsg);
		}
		changeUpdateEntity(entity);
		return new ResultUtil<>().setData(getService().updateByPrimaryKeySelective(entity,session));
	}

	public void changeUpdateEntity(T entity) {
	}

	public  String checkUpdateStatus(T entity){
		return "";
	}


	/**
	 *@author : zhangai
	 *@Date : 11:35 2018/5/9
	 *@描述： 删除
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteById", produces = {"application/json;charset=UTF-8"})
	public Object deleteById(@RequestBody Map<String,Object> entity){
		String id = entity.get("id").toString();
		return new ResultUtil<>().setData(getService().deleteById(id));
	}




	/**
	 * 获取子类注入的service
	 *@author : zhangai
	 *@Date : 11:33 2018/5/9
	 *@描述：获取子类注入的service
	 * @return BaseService
	 */
	public abstract BaseService getService();

}


