package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.service.BaseService;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface TUploadLogService extends BaseService {

    Object getBeyondPageListByCondition(Map<String, Object> entity, HttpSession httpSession);

    Object getWarningPageListByCondition(Map<String, Object> entity, HttpSession httpSession);

    Object getDataAnalysisByCondition(Map<String, Object> entity);
}
