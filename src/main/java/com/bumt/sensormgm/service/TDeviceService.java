package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TDevice;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface TDeviceService extends BaseService {

    Object getPageListBySqlAndCondition(Map<String, Object> entity,HttpSession session);

    TDevice getByEnterpriseId(Long enterpriseId);

    Object getDeviceList(Long areaId);
}
