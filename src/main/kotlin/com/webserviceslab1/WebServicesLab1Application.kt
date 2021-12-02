package com.webserviceslab1

import com.webserviceslab1.helpers.UserRequest
import com.webserviceslab1.models.User
import com.webserviceslab1.services.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class WebServicesLab1Application

fun main(args: Array<String>) {
    runApplication<WebServicesLab1Application>(*args)
}

@Component
class Runner(private val userService: UserService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        try {
            println(
                userService.registerUser(
                    UserRequest(
                        username = "admin",
                        password = "admin",
                        confirmPassword = "admin"
                    )
                ).message
            )
            println(
                userService.registerUser(
                    UserRequest(
                        username = "admin",
                        password = "admin",
                        confirmPassword = "admin"
                    )
                ).message
            )
        } catch (e: Exception) {
            println(e.message)
        }
        try {
            println(
                userService.loginUser(
                    User(username = "admin", password = "admin")
                ).message
            )
            println(
                userService.loginUser(
                    User(username = "admin", password = "wrongPassword")
                ).message
            )
        } catch (e: Exception) {
            println(e.message)
        }
    }
}