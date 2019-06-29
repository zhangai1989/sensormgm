package com.bumt.sensormgm.common.config;
 
import com.bumt.sensormgm.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author:     zhangai
 * @CreateDate: 2019/6/26 9:08
 * @Version: 1.0
 */
public class SessionInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		// 登录不做拦截
		// TODO Auto-generated method stub
		logger.info("------preHandle------");
		//获取session
		HttpSession session = request.getSession(true);
		//	设置session失效时间 ：session.setMaxInactiveInterval (30 * 60);
		if (!(request.getRequestURI().indexOf(Constant.Strings.STATIC_STR)>=0)||request.getRequestURI().indexOf(Constant.Strings.USER_LOGIN_STR)>0||request.getRequestURI().indexOf(Constant.Strings.INDEX_HTML_STR)>0) {
			//判断用户是否存在，不存在就跳转到登录界面
			if(session.getAttribute(Constant.Strings.USER_STR) == null){
				request.getRequestDispatcher("/api/index/noTokenLoginOut").forward(request, response);
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
