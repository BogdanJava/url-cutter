package by.bogdan.springcloudkotlinapp.controller

import by.bogdan.springcloudkotlinapp.SpringCloudKotlinAppApplication
import by.bogdan.springcloudkotlinapp.service.KeyMapperService
import by.bogdan.springcloudkotlinapp.whenever
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@TestPropertySource(locations = ["classpath:repositories-test.properties"])
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SpringCloudKotlinAppApplication::class])
@WebAppConfiguration
class AddControllerTest {

    @Autowired
    lateinit var webAppContext: WebApplicationContext;

    lateinit var mockMvc: MockMvc;

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    private val KEY: String = "key"
    private val LINK: String = "link"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        whenever(service.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddsLinkReturnKey() {
        mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper()
                        .writeValueAsString(AddController.AddRequest(LINK))))
                .andExpect(jsonPath("$.key", equalTo(KEY)))
                .andExpect(jsonPath("$.link", equalTo(LINK)))
    }

    @Test
    fun whenUserAddLinkByFormReturnPage() {
        mockMvc.perform(post("/addhtml").param("link", LINK).contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString(KEY)))
                .andExpect(content().string(containsString(LINK)))
    }

}