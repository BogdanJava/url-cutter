package by.bogdan.springcloudkotlinapp.model

import com.github.springtestdbunit.DbUnitTestExecutionListener
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests

@TestExecutionListeners(DbUnitTestExecutionListener::class)
@SpringBootTest
@DirtiesContext
@TestPropertySource(locations = ["classpath:repositories-test.properties"])
abstract class AbstractRepositoryTest : AbstractTransactionalJUnit4SpringContextTests()