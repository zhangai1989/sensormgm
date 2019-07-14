package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;

import java.util.List;

public interface TAreaDao extends BaseJpaDao<TArea> {
    List<TArea> findByLevelAndDeleteFlag(String level, int deleteFlag);

    List<TArea> findByParentIdAndDeleteFlag(Long parentId, int deleteFlag);

    List<TArea> findByNameAndDeleteFlag(String cname, int deleteFlag);

    List<TArea> findByNameAndIdNotAndDeleteFlag(String name, long id, int deleteFlag);
}
