package com.ericsson.cat.prototype.samples.employee

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 * @since 19/6/20 17:57
 */
@Tag(name = "Employees")
@Repository
class EmployeeRepository {
  companion object {
    var employeeData: MutableMap<String, Employee> = mutableMapOf()
    private var employeeAccessData: MutableMap<String, String> = mutableMapOf()

    init {
      employeeData["1"] = Employee("1", "Employee 1")
      employeeData["2"] = Employee("2", "Employee 2")
      employeeData["3"] = Employee("3", "Employee 3")
      employeeData["4"] = Employee("4", "Employee 4")
      employeeData["5"] = Employee("5", "Employee 5")
      employeeData["6"] = Employee("6", "Employee 6")
      employeeData["7"] = Employee("7", "Employee 7")
      employeeData["8"] = Employee("8", "Employee 8")
      employeeData["9"] = Employee("9", "Employee 9")
      employeeData["10"] = Employee("10", "Employee 10")
      employeeAccessData = HashMap()
      employeeAccessData["1"] = "Employee 1 Access Key"
      employeeAccessData["2"] = "Employee 2 Access Key"
      employeeAccessData["3"] = "Employee 3 Access Key"
      employeeAccessData["4"] = "Employee 4 Access Key"
      employeeAccessData["5"] = "Employee 5 Access Key"
      employeeAccessData["6"] = "Employee 6 Access Key"
      employeeAccessData["7"] = "Employee 7 Access Key"
      employeeAccessData["8"] = "Employee 8 Access Key"
      employeeAccessData["9"] = "Employee 9 Access Key"
      employeeAccessData["10"] = "Employee 10 Access Key"
    }
  }

  fun findEmployeeById(@Parameter(`in` = ParameterIn.PATH) id: String): Mono<Employee> {
    val employee = employeeData[id]?: Employee("NOT_FOUND", "NAME")
    return Mono.just(employee)
  }

  fun findAllEmployees(): Flux<Employee> {
    return Flux.fromIterable(employeeData.values)
  }

  fun updateEmployee(employee: Employee): Mono<Employee> {
    val existingEmployee = employeeData[employee.id]?: Employee("NOT_FOUND", "NAME")
    return Mono.just(existingEmployee)
  }
}