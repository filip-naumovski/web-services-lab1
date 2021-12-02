package com.webserviceslab1.controllers

import com.webserviceslab1.helpers.UserRequest
import com.webserviceslab1.models.User
import com.webserviceslab1.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest


@RestController
@RequestMapping(value = ["/api/user/"])
class UserController(val userService: UserService) {

    @GetMapping(value = ["{id}"])
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> = ResponseEntity.ok(userService.getUserById(id))

    @DeleteMapping
    fun unregisterUser(@RequestBody userRequest: UserRequest): ResponseEntity<String> =
        ResponseEntity.ok(userService.unregisterUser(userRequest).message)

    @PostMapping(value = ["login"])
    fun loginUser(@RequestBody user: User): ResponseEntity<String> =
        ResponseEntity.ok(userService.loginUser(user).message)

    @PostMapping(value = ["register"])
    fun registerUser(@RequestBody userRequest: UserRequest): ResponseEntity<String> =
        ResponseEntity.ok(userService.registerUser(userRequest).message)

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun handleAllExceptions(ex: Exception, request: WebRequest?): ResponseEntity<Any?> {
        return ResponseEntity(ex.message!!, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}