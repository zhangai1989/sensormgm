package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TEnterprise;

import java.util.List;

public interface TEnterpriseDao extends BaseJpaDao<TEnterprise> {

    List<TEnterprise> findByName(String name);

    List<TEnterprise> findByAreaId(long areaId);
}
