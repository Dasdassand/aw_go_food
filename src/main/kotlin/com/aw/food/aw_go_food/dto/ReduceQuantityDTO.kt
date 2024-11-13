package com.aw.food.aw_go_food.dto

import com.aw.food.aw_go_food.dto.enums.Operations
import java.io.Serializable
import java.util.*

data class ReduceQuantityDTO(
    val id: UUID,
    val count: Int,
    val operation: Operations
): Serializable