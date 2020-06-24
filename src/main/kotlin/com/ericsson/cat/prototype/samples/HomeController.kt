package com.ericsson.cat.prototype.samples

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.kotlin.core.publisher.toMono

@RestController
class HomeController {
  @GetMapping("/hello")
  fun home() = mapOf("Hello" to "World").toMono()
}