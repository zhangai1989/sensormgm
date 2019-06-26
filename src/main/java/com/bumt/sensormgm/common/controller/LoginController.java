package com.bumt.sensormgm.common.controller;

import com.bumt.sensormgm.service.TUserService;
import com.bumt.sensormgm.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/index")
public class LoginController {


    @Autowired
    private TUserService tUserService;

    @ResponseBody
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public Object login(HttpSession session, @RequestParam("userName") String userName, @RequestParam("password") String password){
        return tUserService.checkUserLoginNameAndPassword(userName,password,session);
    }


    @ResponseBody
    @RequestMapping(value = "/loginOut", produces = {"application/json;charset=UTF-8"})
    public Object loginOut(HttpSession session){
        session.removeAttribute("user");
        return new ResultUtil<>().setData("退出成功");
    }



    @ResponseBody
    @RequestMapping(value = "/noTokenLoginOut", produces = {"application/json;charset=UTF-8"},method ={RequestMethod.GET,RequestMethod.POST} )
    public Object noTokenLoginOut(){
        return new ResultUtil<>().setErrorMsg(3000,"后端未登录临时拦截，必须登录后才能访问！");
    }


}
