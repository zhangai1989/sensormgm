package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;

import com.bumt.sensormgm.entity.TUploadLog;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TUploadLogService;
import com.bumt.sensormgm.view.WarningBeyondVo;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TUploadLogDao;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TUploadLogServiceImpl extends BaseServiceImpl implements TUploadLogService  {

	@Resource
	private TUploadLogDao dao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }

	@PersistenceContext
	private EntityManager em;

	@Override
	public Object getBeyondPageListByCondition(Map<String, Object> entity, HttpSession httpSession) {
		String startTime="";
		if(!StringUtils.isEmpty(entity.get("startTime"))){
			startTime =entity.get("startTime").toString();
		}

		String endTime="";
		if(!StringUtils.isEmpty(entity.get("endTime"))){
			endTime =entity.get("endTime").toString();
		}

		int pageNum =Integer.parseInt(entity.get("pageNum").toString());
		int pageSize =Integer.parseInt(entity.get("pageSize").toString());
		int start = (pageNum-1)*pageSize;

		String sql =  "select t3.`name` as enterpriseName ,t1.upload_time,t2.lampblack_standard as lampblackStandard ,t1.lampblack,t1.lampblack/t2.lampblack_standard as multiple from t_upload_log t1 JOIN t_device t2 on t1.device_code=t2.device_code LEFT JOIN t_enterprise t3 on t2.enterprise_id = t3.id\n" +
				"where  upload_time >= '" + startTime +"' and upload_time <= '"+endTime+"' and  t2.lampblack_standard < t1.lampblack";

		String countSql =  "select count(0) from t_upload_log t1 JOIN t_device t2 on t1.device_code=t2.device_code LEFT JOIN t_enterprise t3 on t2.enterprise_id = t3.id\n" +
				"where  upload_time >= '" + startTime +"' and upload_time <= '"+endTime+"' and  t2.lampblack_standard < t1.lampblack ";


		TUser tUser = (TUser) httpSession.getAttribute("user");
		//level==3的时候查询改区域的数据，level为4的时候查询该企业的数据
		if(tUser!=null){
			if(tUser.getLevel()==3){
				sql = sql + " and t3.area_id = " +tUser.getAreaId();
				countSql = countSql + " and t3.area_id = " +tUser.getAreaId();
			}else if(tUser.getLevel()==4){
				sql = sql + " and t2.enterprise_id = " +tUser.getEnterpriseId();
				countSql = countSql + " and t2.enterprise_id = " +tUser.getEnterpriseId();
			}
		}

		sql= sql+" limit "+start+","+pageSize;
		System.out.println(sql);
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> dataList = query.getResultList();

		for(Map map:dataList){
			if(map.get("upload_time")!=null&&map.get("upload_time").toString().length()>19){
				map.put("uploadTime",map.get("upload_time").toString().substring(0,19));
			}
		}

		Query queryTotal = em.createNativeQuery(countSql);
		List<BigInteger> total = queryTotal.getResultList();

		Pageable pageable = PageRequest.of((pageNum-1), pageSize);
		Page<Map> page = new PageImpl(dataList,pageable,total.get(0).intValue());
		return page;
	}

	@Override
	public Object getWarningPageListByCondition(Map<String, Object> entity, HttpSession httpSession) {
		String startTime="";
		if(!StringUtils.isEmpty(entity.get("startTime"))){
			startTime =entity.get("startTime").toString();
		}

		String endTime="";
		if(!StringUtils.isEmpty(entity.get("endTime"))){
			endTime =entity.get("endTime").toString();
		}

		int pageNum =Integer.parseInt(entity.get("pageNum").toString());
		int pageSize =Integer.parseInt(entity.get("pageSize").toString());
		int start = (pageNum-1)*pageSize;

		String sql =  "select t3.`name` as enterpriseName ,t1.upload_time,t2.lampblack_standard as lampblackStandard ,t1.lampblack,t1.lampblack/t2.lampblack_standard as multiple from t_upload_log t1 JOIN t_device t2 on t1.device_code=t2.device_code LEFT JOIN t_enterprise t3 on t2.enterprise_id = t3.id\n" +
				"where  upload_time >= '" + startTime +"' and upload_time <= '"+endTime+"' and t2.lampblack_standard > t1.lampblack and t1.lampblack > t2.lampblack_warning";

		String countSql =  "select count(0) from t_upload_log t1 JOIN t_device t2 on t1.device_code=t2.device_code LEFT JOIN t_enterprise t3 on t2.enterprise_id = t3.id\n" +
				"where  upload_time >= '" + startTime +"' and upload_time <= '"+endTime+"' and t2.lampblack_standard > t1.lampblack and t1.lampblack > t2.lampblack_warning ";


		TUser tUser = (TUser) httpSession.getAttribute("user");
		//level==3的时候查询改区域的数据，level为4的时候查询该企业的数据
		if(tUser!=null){
			if(tUser.getLevel()==3){
				sql = sql + " and t3.area_id = " +tUser.getAreaId();
				countSql = countSql + " and t3.area_id = " +tUser.getAreaId();
			}else if(tUser.getLevel()==4){
				sql = sql + " and t2.enterprise_id = " +tUser.getEnterpriseId();
				countSql = countSql + " and t2.enterprise_id = " +tUser.getEnterpriseId();
			}
		}

		sql= sql+" limit "+start+","+pageSize;
		System.out.println(sql);
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> dataList = query.getResultList();

		for(Map map:dataList){
			if(map.get("upload_time")!=null&&map.get("upload_time").toString().length()>19){
				map.put("uploadTime",map.get("upload_time").toString().substring(0,19));
			}
		}

		Query queryTotal = em.createNativeQuery(countSql);
		List<BigInteger> total = queryTotal.getResultList();

		Pageable pageable = PageRequest.of((pageNum-1), pageSize);
		Page<Map> page = new PageImpl(dataList,pageable,total.get(0).intValue());
		return page;
	}

	@Override
	public Object getDataAnalysisByCondition(Map<String, Object> entity) {
		String startTime="";
		if(!StringUtils.isEmpty(entity.get("startTime"))){
			startTime =entity.get("startTime").toString();
		}

		String endTime="";
		if(!StringUtils.isEmpty(entity.get("endTime"))){
			endTime =entity.get("endTime").toString();
		}
//		List<TUploadLog> tUploadLogList = dao.getDataAnalysisByCondition(startTime,endTime, entity.get("enterpriseId").toString());
		return dao.getDataAnalysisByCondition(startTime,endTime, entity.get("enterpriseId").toString());
	}

	@Override
	public Object getRankingByCondition(Map<String, Object> entity,HttpSession session) {

		String startTime="";
		if(!StringUtils.isEmpty(entity.get("startTime"))){
			startTime =entity.get("startTime").toString();
		}

		String endTime="";
		if(!StringUtils.isEmpty(entity.get("endTime"))){
			endTime =entity.get("endTime").toString();
		}


		String sql =  "select t1.*,t3.`name` from (select device_code as deviceCode,sum(lampblack)/COUNT(0) as lampblack,SUM(temp)/count(0) as temp,SUM(humidity)/count(0) as humidity from t_upload_log where upload_time >='"+startTime+"' and upload_time <='"+endTime+"'  GROUP BY device_code) t1 JOIN t_device t2 ON t1.deviceCode = t2.device_code LEFT JOIN t_enterprise t3 ON t2.enterprise_id = t3.id " ;


		TUser tUser = (TUser) session.getAttribute("user");
		//level==3的时候查询改区域的数据，level为4的时候查询该企业的数据
		if(tUser!=null){
			if(tUser.getLevel()==3){
				sql = sql + " and t3.area_id = " +tUser.getAreaId();
			}else if(tUser.getLevel()==4){
				sql = sql + " and t2.enterprise_id = " +tUser.getEnterpriseId();
			}
		}

		sql=sql+" ORDER BY "+entity.get("field").toString()+" "+entity.get("sort").toString();

		System.out.println(sql);
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> dataList = query.getResultList();

//		for(Map map:dataList){
//			if(map.get("upload_time")!=null&&map.get("upload_time").toString().length()>19){
//				map.put("uploadTime",map.get("upload_time").toString().substring(0,19));
//			}
//		}
		return dataList;
	}
}
