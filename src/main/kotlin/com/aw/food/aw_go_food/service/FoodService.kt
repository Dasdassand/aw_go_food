package com.aw.food.aw_go_food.service

import com.aw.food.aw_go_food.dto.AddFoodDTO
import com.aw.food.aw_go_food.dto.GetFoodDTO
import com.aw.food.aw_go_food.dto.ReduceQuantityDTO
import java.util.*

interface FoodService {
    fun addFood(food: AddFoodDTO): GetFoodDTO
    fun getFood(id: UUID): GetFoodDTO
    fun editQuantity(request: ReduceQuantityDTO): GetFoodDTO
}