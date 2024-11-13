package com.aw.food.aw_go_food.dto

import java.io.Serializable
import java.util.UUID

data class GetFoodDTO(
    val id: UUID,
    val name: String,

    val expirationDate: String,

    val cost: Double,

    val quantity: Int
): Serializable
