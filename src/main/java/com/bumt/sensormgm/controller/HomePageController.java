package com.bumt.sensormgm.controller;


import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.service.HomePageService;
import com.bumt.sensormgm.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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


}
