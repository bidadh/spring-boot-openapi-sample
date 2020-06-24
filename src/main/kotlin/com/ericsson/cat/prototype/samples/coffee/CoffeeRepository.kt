package com.ericsson.cat.prototype.samples.coffee

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CoffeeRepository : ReactiveCrudRepository<Coffee, String>