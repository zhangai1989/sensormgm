package com.bumt.sensormgm;

import com.bumt.sensormgm.common.dao.RedisDao;
import com.bumt.sensormgm.dao.TUploadLogDao;
import com.bumt.sensormgm.entity.TUploadLog;
import com.bumt.sensormgm.service.TUploadLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensormgmApplicationTests {

	@Resource
	private TUploadLogDao uploadLogDao;

	@Test
	public void contextLoads() {
		long ms = System.currentTimeMillis() - 1000 * 1000 * 60 * 10;
		Random random = new Random(2);
		for(int i = 0; i < 1000; i ++) {
			TUploadLog log = new TUploadLog();
			log.setDeviceCode("20190630162821");
			log.setLampblack(random.nextDouble());
			log.setTemp(20 + random.nextDouble() * 10);
			log.setHumidity(20 + random.nextDouble() * 10);
			log.setFanStatus(random.nextDouble() > 1);
			log.setFanElec(random.nextDouble());
			log.setPurifierStatus(random.nextDouble() > 1);
			log.setPurifierElec(random.nextDouble());
			log.setUploadTime(new Date(ms + i * 1000 * 60 * 10));
			uploadLogDao.save(log);
		}
	}

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Resource
	private RedisDao redisDao;

	@Test
	public void testRedis() {
		Object allCount2 = redisDao.get("upload.total");
		System.out.println(allCount2);
	}

}
