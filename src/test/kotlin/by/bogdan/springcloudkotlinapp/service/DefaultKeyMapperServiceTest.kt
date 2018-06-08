package by.bogdan.springcloudkotlinapp.service

import by.bogdan.springcloudkotlinapp.dao.LinkRepository
import by.bogdan.springcloudkotlinapp.model.Link
import by.bogdan.springcloudkotlinapp.service.impl.DefaultKeyMapperService
import by.bogdan.springcloudkotlinapp.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class DefaultKeyMapperServiceTest {

    @InjectMocks
    private val service: KeyMapperService = DefaultKeyMapperService()

    private val KEY: String = "aAbBcCdD"
    private val LINK_A: String = "http://www.google.com"
    private val LINK_B: String = "http://www.yahoo.com"

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repository: LinkRepository

    private val KEY_A: String = "abc"
    private val KEY_B: String = "cde"

    private val ID_A: Long = 1000000L

    private val ID_B: Long = 1000001L

    private val LINK_OBJ_A: Link = Link(LINK_A, ID_A)
    private val LINK_OBJ_B: Link = Link(LINK_B, ID_B)

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)
        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)

        whenever(repository.findOne(Mockito.anyLong())).thenReturn(Optional.empty())
        whenever(repository.save(Link(LINK_A))).thenReturn(LINK_OBJ_A)
        whenever(repository.save(Link(LINK_B))).thenReturn(LINK_OBJ_B)
        whenever(repository.findOne(ID_A)).thenReturn(Optional.of(LINK_OBJ_A))
        whenever(repository.findOne(ID_B)).thenReturn(Optional.of(LINK_OBJ_B))
    }

    @Test
    fun clientCanAddLink() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))
        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))
        assertNotEquals(keyA, keyB)
    }

    @Test
    fun clientCanNotTakeLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}