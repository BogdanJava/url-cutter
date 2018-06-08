package by.bogdan.springcloudkotlinapp

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@TestPropertySource(locations = ["classpath:repositories-test.properties"])
@RunWith(SpringRunner::class)
@SpringBootTest
class SpringCloudKotlinAppApplicationTests {

	@Test
	fun contextLoads() {
	}

}
