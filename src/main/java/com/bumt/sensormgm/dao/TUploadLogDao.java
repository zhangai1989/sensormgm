package com.bumt.sensormgm.dao;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.entity.TDevice;
import com.bumt.sensormgm.entity.TUploadLog;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TUploadLogDao extends BaseJpaDao<TUploadLog> {

    @Query(value = "select * from t_upload_log t1 JOIN t_device t2 on t1.device_code=t2.device_code where t2.enterprise_id = ?3 and  upload_time >= ?1 and upload_time <= ?2 ",nativeQuery = true)
    List<TUploadLog> getDataAnalysisByCondition(String startTime, String endTime, String enterpriseId);

}
