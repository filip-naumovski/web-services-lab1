package com.webserviceslab1.repositories

import com.webserviceslab1.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.transaction.Transactional

@Transactional
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
}