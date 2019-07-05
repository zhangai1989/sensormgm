package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TUserDao extends BaseJpaDao<TUser> {

    TUser findByLoginNameAndPassword(String loginName, String password);


    List<TUser> findByLoginName(String loginName);

    List<TUser> findByMobile(String mobile);

    List<TUser> findByEmail(String email);

    List<TUser> findByEmailOrLoginNameOrMobileAndIdNot(String email, String cname, String mobile, long id);
}
