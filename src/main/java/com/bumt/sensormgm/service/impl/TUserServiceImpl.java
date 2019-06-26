package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.CommonUtil;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TUserDao;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class TUserServiceImpl extends BaseServiceImpl implements TUserService  {

	@Resource
	private TUserDao dao;

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
}
