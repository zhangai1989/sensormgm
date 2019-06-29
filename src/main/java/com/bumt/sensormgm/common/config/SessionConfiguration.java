package com.bumt.sensormgm.common.config;


import com.bumt.sensormgm.util.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Description:
 * @Author:     zhangai
 * @CreateDate: 2019/6/26 9:08
 * @Version: 1.0
 */
@Configuration
public class SessionConfiguration implements WebMvcConfigurer  {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//拦截规则：除了login，其他都拦截判断

		registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**").
				excludePathPatterns(Constant.Strings.INDEX_HTML_STR).
				excludePathPatterns("/api/index/**").
				excludePathPatterns("/static/js/**");

	}

}
