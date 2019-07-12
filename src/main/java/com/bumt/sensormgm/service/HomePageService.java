package com.bumt.sensormgm.service;

import javax.servlet.http.HttpSession;

public interface HomePageService  {

    Object getHomePageDataByAreaId(String areaId);

    Object getAllCountAndTodayCount(HttpSession httpSession);
}
