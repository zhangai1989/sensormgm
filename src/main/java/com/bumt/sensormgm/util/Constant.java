package com.bumt.sensormgm.util;

/**
 *
 * @author : zhangai
 * @desaciption :常量工具类 消除魔法值
 * @date : Create in 17:12$ 2018/5/9$
 */
public interface Constant {

	/**
	 * 0：首页的bannner 1：研究院，2：学术委员会，3：研发中心，4培训中心，
	 * 5：智库风采 6：新闻资讯 7：合作伙伴 8：研究领域
	 *@author : zhangai
	 *@date : 16:05 2018/5/28
	 *@description：
	  * @param null
	 */
	interface BannerType{
		String BANNERTYPE_INDEX = "0,2,3,4";
		String BANNERTYPE_INSOUTLINE = "1";
		String BANNERTYPE_THINKTANK = "5";
		String BANNERTYPE_NEWS = "6";
		String BANNERTYPE_PARTNER = "7";
		String BANNERTYPE_RESEARCH = "8";
	}

	/**
	 * @Description: 2000:返回成功   4000:返回失败
	 * @Author:     zhangai
	 * @CreateDate: 2019/3/18 16:50
	 * @Version: 1.0
	 */
	interface ResultCode{
		int SUCCESS=2000;
		int ERROR=4000;
	}


	interface User{
		String REGISTER="register_";
	}
}
