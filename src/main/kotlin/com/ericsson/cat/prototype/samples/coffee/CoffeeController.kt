package com.ericsson.cat.prototype.samples.coffee

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CoffeeController(private val coffeeService: CoffeeService) {
  @GetMapping("/coffees")
  fun getAll() = coffeeService.allCoffees

  @GetMapping("/coffees/{id}")
  fun getById(@PathVariable id: String) = coffeeService.getCoffeeById(id)

  @GetMapping("/coffees/{id}/orders")
  fun getOrders(@PathVariable id: String) = coffeeService.getOrdersForCoffeeById(id)
}