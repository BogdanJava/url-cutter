package by.bogdan.springcloudkotlinapp.service

import by.bogdan.springcloudkotlinapp.service.impl.DefaultKeyConverterService
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DefaultKeyConverterServiceTest {

    val service: KeyConverterService = DefaultKeyConverterService()

    @Test
    fun givenIdMustBeConvertibleBothWays() {
        val rand = Random()
        for (i in 0..1000) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)
            assertEquals(initialId, id)
        }
    }
}