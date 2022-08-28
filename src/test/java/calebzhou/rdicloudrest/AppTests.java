package calebzhou.rdicloudrest;

import calebzhou.rdicloudrest.config.MyBatisConfig;
import calebzhou.rdicloudrest.dao.Island2Mapper;
import calebzhou.rdicloudrest.model.Island2;
import calebzhou.rdicloudrest.utils.RandomUtils;
import calebzhou.rdicloudrest.utils.RdiSerializer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyBatisConfig.class)
class AppTests {
	@Autowired
	Island2Mapper mapper;
	@Test
	void contextLoads() {
		Integer playerOwnIslandId = mapper.getPlayerOwnIslandId("s6400b138-3da9-4780-8540-bb212f487aa2");
		Island2 article = mapper.getIslandById( playerOwnIslandId);
		System.out.println(RdiSerializer.GSON.toJson(article));
	}

	@Test
	void randomTesT(){
		long t1 = System.nanoTime();
		String randomString = RandomUtils.getRandomString(192);
		long t2 = System.nanoTime();
		System.out.println(randomString);
		System.out.println(t2-t1);
	}
}
