package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TDevice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TDeviceDao extends BaseJpaDao<TDevice> {


    @Query(value = "select t1.* from t_device t1 join t_enterprise t2 on t1.enterprise_id=t2.id where t2.delete_flag=0 and t2.name like CONCAT('%',:enterprise,'%') and t1.`status` like CONCAT('%',:status,'%') AND t2.area_id in(:areaIds) AND t1.device_code like CONCAT('%',:deviceCode,'%') limit :pageNum, :pageSize",nativeQuery = true)
    List<TDevice> getPageListBySqlAndCondition(@Param("enterprise") String enterprise, @Param("status") String status, @Param("areaIds") List<Long> areaIds, @Param("deviceCode") String deviceCode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Query(value = "select count(0) from t_device t1 join t_enterprise t2 on t1.enterprise_id=t2.id where t2.delete_flag=0 and t2.name like CONCAT('%',:enterprise,'%') and t1.`status` like CONCAT('%',:status,'%') AND t2.area_id in(:areaIds)  AND t1.device_code like CONCAT('%',:deviceCode,'%')",nativeQuery = true)
    int getTotalBySqlAndCondition(@Param("enterprise") String enterprise, @Param("status") String status, @Param("areaIds") List<Long> areaIds, @Param("deviceCode") String deviceCode);

    TDevice getByEnterpriseId(Long enterpriseId);

    @Query(value = "select t3.name,sum(lampblack)/count(lampblack) per from t_device t1 JOIN t_enterprise t2 on t1.enterprise_id =t2.id JOIN t_area t3 on t2.area_id =t3.id and t2.area_id = ?1 GROUP BY t3.id order by per desc limit 7",nativeQuery = true)
    List<Map> getAreaRank(Long areaId);


    @Query(value = "select t2.`name`,sum(lampblack)/count(lampblack) per from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  where t2.area_id = ?1 GROUP BY t1.enterprise_id order by per desc limit 10",nativeQuery = true)
    List<Map> getDeviceDataByAreaId(String areaId);


    @Query(value = "select t1.last_upload_time,t2.`name`,lampblack,lampblack/lampblack_standard per  from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id LEFT JOIN t_area t3 on t2.area_id =t3.id  where t2.area_id = ?1 and lampblack > lampblack_warning",nativeQuery = true)
    List<Map> getEnterpriseBeyondByAreaId(String areaId);

    @Query(value = "select count(0) count from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id where t2.area_id = ?1 " +
            "UNION All\n" +
            "select count(0) from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id where t2.area_id = ?1 and `status`='ONLINE'\n" +
            "UNION All\n" +
            "select count(0) from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id where t2.area_id = ?1 and `status`='ONLINE' and t1.lampblack>lampblack_warning and t1.lampblack<=lampblack_standard\n" +
            "UNION All\n" +
            "select count(0) from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id where t2.area_id = ?1 and `status`='ONLINE' and t1.lampblack>lampblack_standard\n",nativeQuery = true)
    List<Map> getDeviceStatisticsByAreaId(String areaId);

    @Query(value = "select t2.`name`,t2.address,t2.contact,t2.contact_mobile as contactMobile,t2.env_contact as envContact,t2.env_contact_mobile as envContactMobile,t1.last_upload_time as lastUploadTime,t1.lampblack,t1.temp,t1.humidity,t1.fan_elec as fanElec,purifier_elec as purifierElec,case when `status` = 'OFFLINE' then 0 WHEN lampblack > lampblack_standard THEN 3 WHEN lampblack > lampblack_warning THEN 2 else 1 end as lampblackStatus,fan_status as fanStatus,purifier_status as purifierStatus,t2.latitude,t2.longitude from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id where t2.area_id = ?1 and t2.latitude>0 and t2.longitude>0 ",nativeQuery = true)
    List<Map> getDeviceListByAreaId(String areaId);

    @Query(value = "select t1.id,t2.area_id as areaId,t2.`name`,t2.address,t2.contact,t2.contact_mobile as contactMobile,t2.env_contact as envContact,t2.env_contact_mobile as envContactMobile,t1.status,DATE_FORMAT(t1.last_upload_time,'%Y-%m-%d %k:%i:%s') as lastUploadTime,t1.lampblack,t1.temp,t1.humidity,t1.fan_elec as fanElec,purifier_elec as purifierElec,case WHEN lampblack > lampblack_standard THEN 3 WHEN lampblack > lampblack_warning THEN 2 else 1 end as lampblackStatus,fan_status as fanStatus,purifier_status as purifierStatus,t2.latitude,t2.longitude from t_device t1 LEFT JOIN t_enterprise t2 on t1.enterprise_id =t2.id  LEFT JOIN t_area t3 on t2.area_id =t3.id where t2.area_id in (:areaIds) and t2.latitude>0 and t2.longitude>0 ",nativeQuery = true)
    List<Map> getDeviceListByAreas(@Param("areaIds") Set<Long> areaIds);
}
