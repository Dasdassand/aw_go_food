package com.aw.food.aw_go_food.repository

import com.aw.food.aw_go_food.entity.Food
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FoodRepository : CrudRepository<Food, UUID> {
}