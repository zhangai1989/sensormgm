package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TUserDao extends BaseJpaDao<TUser> {

    TUser findByLoginNameAndPassword(String loginName, String password);
    @Query(value = "select t1.area_id as areaId,t1.enterprise_id as enterpriseId,t1.login_name as loginName,t1.cname,t1.email,t1.mobile,t1.create_time,t1.creator_name as creatorName,t1.modifier_name as modifierName,t1.modify_time ,t2.`name` as enterpriseName,t3.`name` as areaName from t_user t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id=t2.id LEFT JOIN t_area t3 on t1.area_id=t3.id where t1.level= ?1 and t1.area_id like CONCAT('%',?2,'%') limit ?3,?4",nativeQuery = true)
    List<Map> getPageListBySqlAndCondition(int levelCondition, String areaIdCondition, int start, int pageSize);

    List<TUser> findByLoginName(String loginName);

    List<TUser> findByMobile(String mobile);

    List<TUser> findByEmail(String email);

    List<TUser> findByEmailOrCnameOrMobileAndIdNot(String email, String cname, String mobile, long id);
}
