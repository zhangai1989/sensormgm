package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TArea;

import java.util.List;

public interface TAreaDao extends BaseJpaDao<TArea> {
    List<TArea> findByLevel(String level);
}
