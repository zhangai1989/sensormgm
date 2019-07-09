package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TUserDao extends BaseJpaDao<TUser> {

    TUser findByLoginNameAndPasswordAndDeleteFlagEquals(String loginName, String password,int deleteFlag);


    List<TUser> findByLoginNameAndDeleteFlagNot(String loginName,int deleteFlag);

    List<TUser> findByMobileAndDeleteFlagNot(String mobile,int deleteFlag);

    List<TUser> findByEmailAndDeleteFlagNot(String email,int deleteFlag);

    List<TUser> findByEmailOrLoginNameOrMobileAndIdNotAndDeleteFlagNot(String email, String cname, String mobile, long id,int deleteFlag);
}
