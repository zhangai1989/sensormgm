package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TDeviceDao extends BaseJpaDao<TDevice> {


    @Query(value = "select t1.*,t2.`name` as enterprise_name  from t_device t1 join t_enterprise t2 on t1.enterprise_id=t2.id where t2.name like CONCAT('%',?1,'%') and t1.`status` like CONCAT('%',?2,'%') AND t2.area_id like CONCAT('%',?3,'%') ",nativeQuery = true)
    List<TDevice> getPageListBySqlAndCondition(String enterprise, String status, String areaId);



    TDevice getByEnterpriseId(Long enterpriseId);

    @Query(value = "select t1.*,t2.`name` as enterprise_name  from t_device t1 join t_enterprise t2 on t1.enterprise_id=t2.id where t2.name like CONCAT('%',?1,'%') and t1.`status` like CONCAT('%',?2,'%') AND t2.area_id like CONCAT('%',?3,'%') ",nativeQuery = true)
    Page<Map> getPageListBySqlAndCondition2(String enterprise, String status, String areaId, Pageable pageable);
}
