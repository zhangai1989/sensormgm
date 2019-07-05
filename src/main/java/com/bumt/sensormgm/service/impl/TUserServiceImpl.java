package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.dao.TAreaDao;
import com.bumt.sensormgm.dao.TUserDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.CommonUtil;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TUserServiceImpl extends BaseServiceImpl implements TUserService  {

	@Resource
	private TUserDao dao;
	@Resource
	private TAreaDao areaDao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }

	/**
	 * @Description:登录校验
	 * @Author:     zhangai
	 * @CreateDate: 2019/6/26 11:02
	 * @Version: 1.0
	 */
	@Override
	public Object checkUserLoginNameAndPassword(String userName, String password, HttpSession session) {
		//获取登录密码的密文
		String enpassword="";
		try {
			enpassword = CommonUtil.encodeByMD5(password);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultUtil<>().setErrorMsg("获取密文出错！");
		}
		TUser user = dao.findByLoginNameAndPassword(userName,enpassword);
		if(user==null){
			return new ResultUtil<>().setErrorMsg("用户名或密码不正确！");
		}else{
			//user放入session用于数据权限判断
			session.setAttribute("user", user);
			return new ResultUtil<>().setData(user,"登录成功！");
		}
	}

	@Autowired
	private TAreaService tAreaService;

	@Override
	public Object getPageListByCondition2(Map<String, Object> entity, HttpSession httpSession) {
		TUser tUser = (TUser) httpSession.getAttribute("user");
		if (StringUtils.isEmpty(tUser)) {
			return new ResultUtil<>().setErrorMsg(4000,"未登录");
		}
		// 企业账号无权限
		if (4 == tUser.getLevel()) {
			return new PageImpl(new ArrayList(), null, 0);
		}
		Specification<TUser> specification = (root, criteriaQuery, cb) -> {
			List<Predicate> list = new ArrayList<>();
			// 前端传了企业ID
			if (!StringUtils.isEmpty(entity.get("enterpriseId"))) {
				list.add(cb.equal(root.<String>get("enterpriseId"), entity.get("enterpriseId")));
			} else {
				// 前端传了区域ID
				if (!StringUtils.isEmpty(entity.get("areaId"))) {
					list.add(cb.equal(root.<String>get("areaId"), entity.get("areaId")));
				} else {
					if(3 == tUser.getLevel()) {
						// 办事处级别只能查看同区域下企业用户
						list.add(cb.equal(root.<String>get("areaId"), tUser.getAreaId()));
						list.add(cb.isNotNull(root.<String>get("enterpriseId")));
					}else {
						CriteriaBuilder.In<Long> in = cb.in(root.<Long>get("areaId"));
						List<TArea> listArea = tAreaService.getUserAreas(Long.valueOf(tUser.getAreaId()), false);
						for (TArea area : listArea) {
							in.value(area.getId());
						}
						list.add(in);
					}
				}
			}
			if (!StringUtils.isEmpty(entity.get("name"))) {
				list.add(cb.like(root.get("cname"), "%" + entity.get("name") + "%"));
			}
			list.add(cb.equal(root.<Integer>get("deleteFlag"), 0));
			return cb.and(list.toArray(new Predicate[list.size()]));
		};

		int pageNum = Integer.parseInt(entity.get("pageNum").toString());
		int pageSize = Integer.parseInt(entity.get("pageSize").toString());
		Pageable pageable = PageRequest.of((pageNum - 1), pageSize, new Sort(Sort.Direction.DESC, "id"));

		return new ResultUtil<>().setData(getPageListByCondition(specification, pageable));
	}

	@Override
	public List<TUser> getByLoginName(String cname) {

		return dao.findByLoginName(cname);
	}

	@Override
	public List<TUser> getByMobile(String mobile) {
		return dao.findByMobile(mobile);
	}

	@Override
	public List<TUser> getByEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public List<TUser> checkUpdateStatus(String cname, String mobile, String email, long id) {

		return dao.findByEmailOrLoginNameOrMobileAndIdNot(email,cname,mobile,id);
	}

	@Override
	public Object insert(Object o, HttpSession session) {
		TUser user = (TUser)o;
		setLevel(user);
		return super.insert(o, session);
	}

	/**
	 * 设置用户等级
	 * @param user
	 */
	private void setLevel(TUser user) {
		if(!StringUtils.isEmpty(user.getEnterpriseId())) {
			user.setLevel(4);
		}else {
			TArea area = areaDao.findById(Long.valueOf(user.getAreaId())).get();
			user.setLevel(Integer.valueOf(area.getLevel()) + 1);
		}
	}

}
