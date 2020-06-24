package com.ericsson.cat.prototype.samples.coffee

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant

@Service
class CoffeeService(private val repo: CoffeeRepository) {
  val allCoffees: Flux<Coffee>
    get() = repo.findAll()

  fun getCoffeeById(@Parameter(`in` = ParameterIn.PATH) id: String): Mono<Coffee> {
    return repo.findById(id)
  }

  fun getOrdersForCoffeeById(@Parameter(`in` = ParameterIn.PATH) coffeeId: String): Flux<CoffeeOrder> {
    return Flux.interval(Duration.ofSeconds(1))
        .onBackpressureDrop()
        .map { CoffeeOrder(coffeeId, Instant.now()) }
  }
}