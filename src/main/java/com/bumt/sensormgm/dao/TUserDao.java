package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TUserDao extends BaseJpaDao<TUser> {

    TUser findByLoginNameAndPasswordAndDeleteFlag(String loginName, String password,int deleteFlag);


    List<TUser> findByLoginNameAndDeleteFlag(String loginName,int deleteFlag);

    List<TUser> findByMobileAndDeleteFlag(String mobile,int deleteFlag);

    List<TUser> findByEmailAndDeleteFlag(String email,int deleteFlag);

    List<TUser> findByEmailOrLoginNameOrMobileAndIdNotAndDeleteFlag(String email, String cname, String mobile, long id,int deleteFlag);

    List<TUser> findByMobileAndIdNotAndDeleteFlag(String mobile, long id, int i);

    List<TUser> findByEmailAndIdNotAndDeleteFlag(String email, long id, int i);

    List<TUser> findByLoginNameAndIdNotAndDeleteFlag(String cname, long id, int i);
}
