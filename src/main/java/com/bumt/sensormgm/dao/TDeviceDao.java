package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TDeviceDao extends BaseJpaDao<TDevice> {


    @Query(value = "select t1.* from t_device t1 join t_enterprise t2 on t1.enterprise_id=t2.id where t2.name like CONCAT('%',?1,'%') and t1.`status` like CONCAT('%',?2,'%') AND t2.area_id like CONCAT('%',?3,'%') limit ?4,?5",nativeQuery = true)
    List<TDevice> getPageListBySqlAndCondition(String enterprise, String status, String areaId,int pageNum,int pageSize);

    @Query(value = "select count(0) from t_device t1 join t_enterprise t2 on t1.enterprise_id=t2.id where t2.name like CONCAT('%',?1,'%') and t1.`status` like CONCAT('%',?2,'%') AND t2.area_id like CONCAT('%',?3,'%') ",nativeQuery = true)
    int getTotalBySqlAndCondition(String enterprise, String status, String areaId);


    TDevice getByEnterpriseId(Long enterpriseId);



    @Query(value = "select t3.name,sum(lampblack)/count(lampblack) per from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id GROUP BY t3.id order by per desc limit 7",nativeQuery = true)
    List<Map> getAreaRank();


    @Query(value = "select t2.`name`,sum(lampblack)/count(lampblack) from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  where t2.area_id = ?1 GROUP BY enterprise_id  limit 10",nativeQuery = true)
    List<Map> getDeviceDataByAreaId(String areaId);


    @Query(value = "select t1.last_upload_time,t2.`name`,lampblack,lampblack/lampblack_standard per  from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id LEFT JOIN t_area t3 on t2.area_id =t3.id  where t2.area_id = ?1 and lampblack > lampblack_standard",nativeQuery = true)
    List<Map> getEnterpriseBeyondByAreaId(String areaId);
}
