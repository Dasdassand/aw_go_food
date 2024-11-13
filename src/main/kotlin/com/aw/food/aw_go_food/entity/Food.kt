package com.aw.food.aw_go_food.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "food")
data class Food(
    @Id
    @Column(nullable = false, unique = true)
    var id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var name: String,

    @Column(name = "expiration_date")
    var expirationDate: LocalDate,

    @Column(nullable = false)
    var cost: Double,

    @Column(nullable = false)
    var quantity: Int
)