package by.bogdan.springcloudkotlinapp.controller

import by.bogdan.springcloudkotlinapp.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
class RedirectController {

    @Autowired
    lateinit var keyMapperService: KeyMapperService

    @GetMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping("/{key}")
    fun redirect(@PathVariable key: String, response: HttpServletResponse) {

        val result = keyMapperService.getLink(key)
        when (result) {
            is KeyMapperService.Get.Link -> {
                response.setHeader(LocationHeader.HEADER_NAME, result.link)
                response.status = 302
            }
            is KeyMapperService.Get.NotFound -> {
                response.status = 404
            }
        }
    }

    companion object LocationHeader {
        private val HEADER_NAME: String = "Location";
    }
}