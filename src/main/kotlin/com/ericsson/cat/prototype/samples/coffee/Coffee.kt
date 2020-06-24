package com.ericsson.cat.prototype.samples.coffee

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Coffee(
    @Id
    var id: String? = null,
    var name: String? = null

)