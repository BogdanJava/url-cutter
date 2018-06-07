package by.bogdan.springcloudkotlinapp.service.impl

import by.bogdan.springcloudkotlinapp.service.KeyConverterService
import by.bogdan.springcloudkotlinapp.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    val sequence = AtomicLong(1000000L)

    private val map: MutableMap<Long, String> = ConcurrentHashMap()

    override fun add(link: String): String {
        val id = sequence.getAndIncrement()
        val key = converter.idToKey(id)
        map.put(id, link)
        return key
    }

    override fun getLink(key: String): KeyMapperService.Get {
        val id = converter.keyToId(key)
        val result = map[id]
        if (result == null) return KeyMapperService.Get.NotFound(key)
        else return KeyMapperService.Get.Link(result)
    }
}