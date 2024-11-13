package com.aw.food.aw_go_food.validator

import com.aw.food.aw_go_food.dto.AddFoodDTO
import java.time.LocalDate


fun AddFoodDTO.validate() {
    require(name.isNotBlank()) { "Name cannot be empty" }

    require(expirationDate.isAfter(LocalDate.now())) { "Expiration date must be in the future" }

    require(cost > 0) { "Cost must be greater than zero" }

    require(quantity > 0) { "Quantity must be greater than zero" }
}