package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TUploadLog;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface TUploadLogDao extends BaseJpaDao<TUploadLog> {

    @Query(value = "select * from t_upload_log t1 JOIN t_device t2 on t1.device_code=t2.device_code where t2.enterprise_id = ?3 and  upload_time >= ?1 and upload_time <= ?2 and t2.delete_flag=0 ",nativeQuery = true)
    List<TUploadLog> getDataAnalysisByCondition(String startTime, String endTime, String enterpriseId);

    @Query(value = "select t3.`name`,upload_time, t1.lampblack from t_upload_log t1 left join t_device t2 ON t1.device_code = t2.device_code LEFT JOIN t_enterprise t3 ON t2.enterprise_id = t3.id where area_id = ?1 ORDER BY upload_time desc limit 100 ",nativeQuery = true)
    List<Map> getLogListByAreaId(String areaId);
}
