package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TEnterprise;

import java.util.List;

public interface TEnterpriseDao extends BaseJpaDao<TEnterprise> {

    List<TEnterprise> findByNameAndDeleteFlag(String name, int deleteFlag);

    List<TEnterprise> findByCodeAndDeleteFlag(String code, int deleteFlag);

    List<TEnterprise> findByAreaIdAndDeleteFlag(long areaId, int deleteFlag);

    List<TEnterprise> findByNameAndIdNotAndDeleteFlag(String name, long i, int i1);
}
