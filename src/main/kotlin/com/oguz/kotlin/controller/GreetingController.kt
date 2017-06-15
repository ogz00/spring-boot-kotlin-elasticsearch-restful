package com.oguz.kotlin.controller

import com.oguz.kotlin.model.Greeting
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */

@RestController
class GreetingController {
    val counter = AtomicLong()

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "Dummy") name: String): Greeting {
        return Greeting(counter.incrementAndGet(), "Hello, $name")
    }

}