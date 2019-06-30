package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.service.BaseService;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface TUserService extends BaseService {

    Object checkUserLoginNameAndPassword(String userName, String password, HttpSession session);

    Object getPageListByCondition2(Map<String, Object> entity, HttpSession httpSession);
}
