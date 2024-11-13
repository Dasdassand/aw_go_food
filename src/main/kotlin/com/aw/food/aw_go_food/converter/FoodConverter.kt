package com.aw.food.aw_go_food.converter

import com.aw.food.aw_go_food.dto.AddFoodDTO
import com.aw.food.aw_go_food.dto.GetFoodDTO
import com.aw.food.aw_go_food.entity.Food
import java.time.LocalDate

fun AddFoodDTO.toEntity(): Food {
    return Food(
        name = this.name,
        expirationDate = LocalDate.parse(this.expirationDate.toString()),  // Преобразование строки в дату
        cost = this.cost,
        quantity = this.quantity
    )
}

fun Food.toDto(): GetFoodDTO {
    return GetFoodDTO(
        id = this.id,
        name = this.name,
        expirationDate = this.expirationDate.toString(),  // Преобразование даты в строку
        cost = this.cost,
        quantity = this.quantity
    )
}

fun GetFoodDTO.toFood(): Food {
    return Food(
        id = this.id,
        name = this.name,
        expirationDate = LocalDate.parse(this.expirationDate),  // Преобразование даты в строку
        cost = this.cost,
        quantity = this.quantity
    )
}
