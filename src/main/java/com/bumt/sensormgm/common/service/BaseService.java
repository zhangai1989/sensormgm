package com.bumt.sensormgm.common.service;


import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author : zhangai
 * @desaciption :
 * @date : Create in 14:27$ 2018/5/4$
 */
public interface BaseService<T> {
	/**
	 * 根据主键获取信息
	 *@author : zhangai
	 *@date : 17:46 2018/5/9
	 *@description：
	 * @param id
	 * @return T
	 */
	T getSingleById(String id);
	/**
	 *根据主键删除一条信息
	 *@author : zhangai
	 *@date : 17:47 2018/5/9
	 *@description：
	 * @param id
	 * @return int
	 */
	Object deleteById(String id);
	/**
	 *根据主键删除一条信息
	 *@author : zhangai
	 *@date : 17:47 2018/5/9
	 *@description：
	 * @param t 单条数据信息
	 * @return int
	 */
	Object insert(T t,HttpSession session);
	/**
	 *获取一页信息
	 *@author : zhangai
	 *@date : 17:48 2018/5/9
	 *@description：获取一页信息
	 * @param pageNum 当前的页数
	 * @param pageSize 该页的大小
	 * @return page信息
	 */
	Page getPageList(int pageNum, int pageSize);
	/**
	 * 获取所有信息
	 * @return T
	 */
	List<T> getAllList();

	/**
	 * 更新
	 * @param entity
	 * @return
	 */
//	Object updateByPrimaryKeySelective(Map<String,Object> entity);

	Object updateByPrimaryKeySelective(T entity,HttpSession session);

	/**
	 * 根据多个条件查询一页列表
	 * @param
	 * @return
	 */
	Page<T> getPageListByCondition(Specification<T> querySpecification, Pageable pageable);


    Object deleteByIdChangeStatus(T entity, HttpSession session);
}
