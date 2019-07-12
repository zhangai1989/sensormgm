package com.bumt.sensormgm.controller;


import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.service.HomePageService;
import com.bumt.sensormgm.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Description:首页相关请求接口
 * @Author:     zhangai
 * @CreateDate: 2019/7/1 10:11
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/homePage")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @ResponseBody
    @RequestMapping(value = "/getHomePageDataByAreaId", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public Object getHomePageDataByAreaId(@RequestParam("areaId") String areaId){

        return new ResultUtil<>().setData(homePageService.getHomePageDataByAreaId(areaId));
    }

    @ResponseBody
    @RequestMapping(value = "/getAllCountAndTodayCount", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public Object getAllCountAndTodayCount(){
        return new ResultUtil<>().setData(homePageService.getAllCountAndTodayCount());
    }
}
