package by.bogdan.springcloudkotlinapp.controller

import by.bogdan.springcloudkotlinapp.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class AddController {

    @Value("\${smlr.prefix}")
    private lateinit var prefix: String

    @Autowired
    lateinit var service: KeyMapperService

    @ResponseBody
    @PostMapping("/add")
    fun addRest(@RequestBody request: AddRequest) =
            ResponseEntity.ok(AddResponse(request.link, service.add(request.link)))!!

    @PostMapping("/addhtml")
    fun addhtml(model: Model, @RequestParam(required = true) link: String): String {
        var result = add(link)
        model.addAttribute("link", result.link)
        model.addAttribute("compressed", prefix + result.key)
        return "result"
    }

    data class AddRequest(val link: String)
    data class AddResponse(val link: String, val key: String)

    private fun add(link: String) = AddResponse(link, service.add(link))
}