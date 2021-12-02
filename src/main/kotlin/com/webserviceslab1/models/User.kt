package com.webserviceslab1.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
        @Id
        @GeneratedValue
        var id: Long = 0,
        @Column
        var username: String = "",
        @Column
        var password: String = ""
)