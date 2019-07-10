package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface TUserService extends BaseService {

    Object checkUserLoginNameAndPassword(String userName, String password, HttpSession session);

    Object getPageListByCondition2(Map<String, Object> entity, HttpSession httpSession);

    List<TUser> getByLoginName(String cname);

    List<TUser> getByMobile(String mobile);

    List<TUser> getByEmail(String email);

    String checkUpdateStatus(String cname, String mobile, String email, long id);
}
