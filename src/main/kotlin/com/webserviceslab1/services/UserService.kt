package com.webserviceslab1.services

import com.webserviceslab1.helpers.UserRequest
import com.webserviceslab1.helpers.UserResponse
import com.webserviceslab1.models.User
import com.webserviceslab1.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getUserById(id: Long): User = userRepository.findById(id).orElseThrow()
    fun getUserByUsername(username: String): User = userRepository.findByUsername(username).orElseThrow()

    private fun removeUser(user: User): User {
        val userResponse = getUserByUsername(user.username)
        userRepository.deleteById(userResponse.id)
        return userResponse
    }

    fun loginUser(user: User): UserResponse {
        val userResponse = getUserByUsername(user.username)
        return if (user.password == userResponse.password) {
            UserResponse(user, "User with username ${user.username} successfully logged in!")
        } else {
            throw Exception("Incorrect password!")
        }
    }

    fun registerUser(user: UserRequest): UserResponse {
        return if (user.password != user.confirmPassword) {
            throw Exception("Passwords do not match!")
        } else if (userRepository.findByUsername(user.username).isPresent) {
            throw Exception("User with username ${user.username} already exists!")
        } else {
            UserResponse(
                userRepository.save(User(username = user.username, password = user.password)),
                "User with username ${user.username} successfully registered!"
            )
        }
    }

    fun unregisterUser(user: UserRequest): UserResponse {
        val userResponse = getUserByUsername(user.username)
        return if (user.password != user.confirmPassword) {
            throw Exception("Passwords do not match!")
        } else if (user.password != userResponse.password) {
            throw Exception("Incorrect password!")
        } else {
            UserResponse(removeUser(userResponse), "Deleted user with username ${user.username}.")
        }
    }
}