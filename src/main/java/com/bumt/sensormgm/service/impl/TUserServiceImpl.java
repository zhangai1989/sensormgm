package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.dao.TAreaDao;
import com.bumt.sensormgm.dao.TUserDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.CommonUtil;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
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

	@Override
	public Object getPageListByCondition2(Map<String, Object> entity, HttpSession httpSession) {
		TUser tUser = (TUser) httpSession.getAttribute("user");
		if(tUser!=null){
			int level = tUser.getLevel();

			int levelCondition = 2;
			String areaIdCondition ="";
			if(level==1||level==2){
				//查询level==3的所有数据
				levelCondition =3 ;
			}else if(level==3){

				//查询level==4的数据 areaId==
				levelCondition = 4;
				areaIdCondition=tUser.getAreaId();
			}

			int pageNum =Integer.parseInt(entity.get("pageNum").toString());
			int pageSize =Integer.parseInt(entity.get("pageSize").toString());
			int start = (pageNum-1)*pageSize;
			List<Map> dataList =  dao.getPageListBySqlAndCondition(levelCondition,areaIdCondition, start,pageSize );
			List<Map> dataResultList = new ArrayList<>();
			for(Map map:dataList){
				Map map2 = new HashMap();
				map2.putAll(map);
				if(map.get("modify_time")!=null&&map.get("modify_time").toString().length()>19){
					map2.put("modifyTime",map.get("modify_time").toString().substring(0,19));
				}
				if(map.get("create_time")!=null&&map.get("create_time").toString().length()>19){
					map2.put("createTime",map.get("create_time").toString().substring(0,19));
				}

				dataResultList.add(map2);
			}
			return new ResultUtil<>().setData(dataResultList);
		}
		return new ResultUtil<>().setErrorMsg(4000,"未登录");
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
	public static void main(String[] args) {
		String aa = "2019-06-30 13:48:38.0";
		System.out.println(aa.substring(0,19));


	}
}
