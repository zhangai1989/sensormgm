package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.service.BaseService;

import javax.servlet.http.HttpSession;

public interface TUserService extends BaseService {

    Object checkUserLoginNameAndPassword(String userName, String password, HttpSession session);
}
