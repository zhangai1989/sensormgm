package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TEnterprise;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TEnterpriseService extends BaseService {

    List<TEnterprise> getAllListByName(String name);

    Object getEnterpriseBySession(HttpSession session,String areaId);

    void save(TEnterprise enterprise, HttpSession session);

}
