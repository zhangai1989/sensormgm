package com.bumt.sensormgm.common.service.impl;


import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.util.CommonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author : zhangai
 * @desaciption :
 * @date : Create in 14:44$ 2018/5/4$
 */
public abstract class BaseServiceImpl<T>  implements BaseService<T> {

    /**
     * 获取子类注入的Mapper
     * @return
     */
    public abstract BaseJpaDao getGenericMapper();

	/**
	 *@author : zhangai
	 *@Date : 9:53 2018/5/9
	 *@描述：获取单页信息
	 */
	@Override
	public Page getPageList(int pageNum, int pageSize){
//        Pageable pageable=new PageRequest(pageNum,pageSize,new Sort(Sort.Direction.DESC,"id"));
		Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<T> page =  getGenericMapper().findAll(pageable);
		return page;
	}

	/**
	 *@author : zhangai
	 *@Date : 9:53 2018/5/9
	 *@描述：获取单页信息
	 */
	@Override
	public Page<T> getPageListByCondition(Specification<T> querySpecification, Pageable pageable){
		return getGenericMapper().findAll(querySpecification,pageable);
	}


	/**
	 *@author : zhangai
	 *@Date : 9:53 2018/5/9
	 *@描述：获取单页信息
	 */
	@Override
	public List<T> getAllList(){
		return (List<T>) getGenericMapper().findAll();
	}

	/**
	 *@author : zhangai
	 *@Date : 15:01 2018/5/8
	 *@描述：查询单条信息
	 */
	@Override
	public T getSingleById(String id) {
		return (T) getGenericMapper().findById(Long.parseLong(id));
	}

	/**
	 *@author : zhangai
	 *@Date : 15:00 2018/5/8
	 *@描述：新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object insert(T t,HttpSession session) {
		//添加基础字段，id ,创建时间
		addBaseMetaInfo(t,session);
		return getGenericMapper().save(t);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object updateByPrimaryKeySelective(T entity,HttpSession session) {

		try {
			//实现部分字段更新
			String id = BeanUtils.getProperty(entity,"id");
			Optional<T> t = (Optional<T>)this.getSingleById(id);
			T saveEntity = t.get();
			Map<String, String> map = BeanUtils.describe(entity);
			for(String key:map.keySet()){
				if(map.get(key)!=null){
                    BeanUtils.setProperty(saveEntity,key,map.get(key));
                }
			}
			//添加修改时间
			addUpdateMetaInfo(saveEntity, session);
			return getGenericMapper().save(saveEntity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return "error";
	}



	/**
	 *@author : zhangai
	 *@Date : 15:00 2018/5/8
	 *@描述：删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object deleteById(String id) {
        getGenericMapper().deleteById(Long.parseLong(id));
		return 1;
	}


	@Override
	public Object deleteByIdChangeStatus(T entity,HttpSession session) {
		try {
			//实现部分字段更新
			BeanUtils.setProperty(entity, "deleteFlag",0);
			return updateByPrimaryKeySelective(entity,session);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 *@author : zhangai
	 *@Date : 15:00 2018/5/8
	 *@描述：添加基础字段，id ,创建时间
	 */
	public static void addBaseMetaInfo(Object entity,HttpSession session)
	{
		try{
//			BeanUtils.setProperty(entity, "id", CommonUtil.getUUID());
			BeanUtils.setProperty(entity, "createTime",CommonUtil.getNowDateTime());
			TUser tUser = (TUser) session.getAttribute("user");
			if(tUser!=null){
				BeanUtils.setProperty(entity, "creatorName",tUser.getCname());
			}

		} catch(Exception e){
			e.printStackTrace();
		};
	}

	/**
	 *@author : zhangai
	 *@Date : 11:42 2018/5/9
	 *@描述：
	 */
	public static void addUpdateMetaInfo(Object entity,HttpSession session)
	{
		try{
			TUser tUser = (TUser) session.getAttribute("user");
			if(tUser!=null){
				BeanUtils.setProperty(entity, "modifierName",tUser.getCname());
			}
			BeanUtils.setProperty(entity, "modifyTime",CommonUtil.getNowDateTime());

		} catch(Exception e){
			e.printStackTrace();
		};
	}




}
