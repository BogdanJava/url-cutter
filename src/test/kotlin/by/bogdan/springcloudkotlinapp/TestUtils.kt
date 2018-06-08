package by.bogdan.springcloudkotlinapp

import org.mockito.Mockito

fun <T> whenever(call: T) = Mockito.`when`(call)!!