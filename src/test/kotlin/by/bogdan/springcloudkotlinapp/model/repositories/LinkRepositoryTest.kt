package by.bogdan.springcloudkotlinapp.model.repositories

import by.bogdan.springcloudkotlinapp.dao.LinkRepository
import by.bogdan.springcloudkotlinapp.model.AbstractRepositoryTest
import by.bogdan.springcloudkotlinapp.model.Link
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@DatabaseSetup(LinkRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = [(LinkRepositoryTest.DATASET)])
class LinkRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    lateinit var repository: LinkRepository

    @Test
    fun findOneExisting() {
        val got: Optional<Link> = repository.findOne(LINK_1_ID)
        assertThat(got.isPresent, `is`(true))
        val link = got.get()
        assertThat(link, equalTo(Link(LINK_1_TEXT, LINK_1_ID)))
    }

    @Test
    fun findOneNotExisting() {
        val got: Optional<Link> = repository.findOne(LINK_NOT_FOUND)
        assertThat(got.isPresent, `is`(false))
    }

    @Test
    fun saveNew() {
        val toBeSaved = Link(LINK_TBS_TEXT)
        val got: Link = repository.save(toBeSaved)
        val list: List<Link> = repository.findAll()
        assertThat(list, hasSize(4))
        assertThat(got.text, equalTo(LINK_TBS_TEXT))
    }

    companion object {
        const val DATASET = "classpath:datasets/link-table.xml"
        private val LINK_TBS_TEXT: String = "http://www.ru"
        private val LINK_NOT_FOUND: Long = 1L
        private val LINK_1_ID: Long = 100500L
        private val LINK_1_TEXT: String = "http://www.eveonline.com"
    }
}