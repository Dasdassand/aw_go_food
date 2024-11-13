package com.aw.food.aw_go_food.dto

import java.io.Serializable
import java.time.LocalDate

data class AddFoodDTO(
    val name: String,

    val expirationDate: LocalDate,

    val cost: Double,

    val quantity: Int
): Serializable
