package com.ericsson.cat.prototype.samples.employee

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 19/6/20 17:56
 */
@Configuration
class EmployeeFunctionalConfig(private val employeeRepository: EmployeeRepository) {
  @Bean
  @RouterOperation(beanClass = EmployeeRepository::class, beanMethod = "findAllEmployees")
  fun getAllEmployeesRoute(): RouterFunction<ServerResponse> {
    return RouterFunctions.route(RequestPredicates.GET("/employees").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
        HandlerFunction {
          ServerResponse.ok().body(
              employeeRepository.findAllEmployees(), Employee::class.java)
        })
  }

  @Bean
  @RouterOperation(operation =
  Operation(
      operationId = "findEmployeeById",
      summary = "Find purchase order by ID",
      tags = ["MyEmployee"],
      parameters = [Parameter(`in` = ParameterIn.PATH, name = "id", description = "Employee Id")],
      responses = [ApiResponse(responseCode = "200", description = "successful operation", content = [Content(schema = Schema(implementation = Employee::class))]), ApiResponse(responseCode = "400", description = "Invalid Employee ID supplied"), ApiResponse(responseCode = "404", description = "Employee not found")]))
  fun getEmployeeByIdRoute(): RouterFunction<ServerResponse> {
    return RouterFunctions.route(RequestPredicates.GET("/employees/{id}"),
        HandlerFunction { req: ServerRequest ->
          ServerResponse.ok().body(
              employeeRepository.findEmployeeById(req.pathVariable("id")), Employee::class.java)
        })
  }

  @Bean
  @RouterOperation(beanClass = EmployeeRepository::class, beanMethod = "updateEmployee")
  fun updateEmployeeRoute(): RouterFunction<ServerResponse> {
    return RouterFunctions.route(RequestPredicates.POST("/employees/update").and(RequestPredicates.accept(MediaType.APPLICATION_XML)),
        HandlerFunction { req: ServerRequest ->
          req.body(BodyExtractors.toMono(Employee::class.java))
              .doOnNext { employeeRepository.updateEmployee(it)}
              .then(ServerResponse.ok().build())
        })
  }

  @Bean
  @RouterOperations(RouterOperation(path = "/employees-composed/update", beanClass = EmployeeRepository::class, beanMethod = "updateEmployee"), RouterOperation(path = "/employees-composed/{id}", beanClass = EmployeeRepository::class, beanMethod = "findEmployeeById"), RouterOperation(path = "/employees-composed", beanClass = EmployeeRepository::class, beanMethod = "findAllEmployees"))
  fun composedRoutes(): RouterFunction<ServerResponse> {
    return RouterFunctions.route(RequestPredicates.GET("/employees-composed"),
        HandlerFunction {
          ServerResponse.ok().body(
              employeeRepository.findAllEmployees(), Employee::class.java)
        })
        .and(RouterFunctions.route(RequestPredicates.GET("/employees-composed/{id}"),
            HandlerFunction { req: ServerRequest ->
              ServerResponse.ok().body(
                  employeeRepository.findEmployeeById(req.pathVariable("id")), Employee::class.java)
            }))
        .and(RouterFunctions.route(RequestPredicates.POST("/employees-composed/update"),
            HandlerFunction { req: ServerRequest ->
              req.body(BodyExtractors.toMono(Employee::class.java))
                  .doOnNext{employeeRepository.updateEmployee(it)}
                  .then(ServerResponse.ok().build())
            }))
  }
}