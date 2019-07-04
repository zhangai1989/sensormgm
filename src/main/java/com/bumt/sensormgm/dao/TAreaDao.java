package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;

import java.util.List;

public interface TAreaDao extends BaseJpaDao<TArea> {
    List<TArea> findByLevel(String level);

    List<TArea> findByName(String cname);

    List<TUser> findByNameAndIdNot(String name, long id);
}
