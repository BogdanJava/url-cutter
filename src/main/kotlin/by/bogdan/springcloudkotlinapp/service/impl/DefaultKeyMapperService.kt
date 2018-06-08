package by.bogdan.springcloudkotlinapp.service.impl

import by.bogdan.springcloudkotlinapp.dao.LinkRepository
import by.bogdan.springcloudkotlinapp.model.Link
import by.bogdan.springcloudkotlinapp.service.KeyConverterService
import by.bogdan.springcloudkotlinapp.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    @Autowired
    lateinit var repository: LinkRepository

    override fun add(link: String)
            = converter.idToKey(repository.save(Link(link)).id)

    override fun getLink(key: String): KeyMapperService.Get {
        val link = repository.findOne(converter.keyToId(key))
        return if (link.isPresent) {
            KeyMapperService.Get.Link(link.get().text)
        } else {
            KeyMapperService.Get.NotFound(key)
        }
    }
}