package net.blogjava.welldoer.druidbasic;

import static org.assertj.core.api.Assertions.*;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootTest
class DruidDataSourceTest {
	private static Logger logger = LoggerFactory.getLogger(DruidDataSourceTest.class);

	@Resource
	private DruidDataSource dataSource;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testDataSourceExists() {
		assertThat(dataSource).isNotNull();
		
		assertThat(dataSource.getUrl()).contains("jdbc:h2:mem:");
    }

}
