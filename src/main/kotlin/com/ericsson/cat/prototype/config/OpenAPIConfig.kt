package com.ericsson.cat.prototype.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 19/6/20 17:55
 */
@Configuration
class OpenAPIConfig {
  @Bean
  fun customOpenAPI(): OpenAPI {
    return OpenAPI()
        .components(Components().addSecuritySchemes("basicScheme",
            SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
        .info(Info().title("Sample API").version("1")
            .license(License().name("Apache 2.0").url("http://springdoc.org")))
  }

  @Bean
  fun coffeeOpenApi(): GroupedOpenApi {
    val paths = arrayOf("/coffees/**")
    return GroupedOpenApi.builder().group("coffees").pathsToMatch(*paths)
        .build()
  }


  @Bean
  fun employeesOpenApi(): GroupedOpenApi? {
    val paths = arrayOf("/employees/**")
    return GroupedOpenApi.builder().group("employees").pathsToMatch(*paths)
        .build()
  }
}