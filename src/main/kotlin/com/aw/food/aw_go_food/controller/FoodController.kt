package com.aw.food.aw_go_food.controller

import com.aw.food.aw_go_food.dto.AddFoodDTO
import com.aw.food.aw_go_food.dto.GetFoodDTO
import com.aw.food.aw_go_food.dto.ReduceQuantityDTO
import com.aw.food.aw_go_food.service.FoodService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
class FoodController(private val service: FoodService) {

    @PostMapping
    fun addFood(@RequestBody food: AddFoodDTO): GetFoodDTO {
        log.info("Получен запрос на добавление $food")
        return service.addFood(food)
    }

    @GetMapping("/{id}")
    fun getFood(@PathVariable id: UUID): GetFoodDTO {
        log.info("Получен запрос на просмотр $id")
        return service.getFood(id)
    }


    @PatchMapping
    fun editQuantity(@RequestBody request: ReduceQuantityDTO): GetFoodDTO {
        log.info("Получен запрос на изменение $request")
        return service.editQuantity(request)
    }


    companion object {
        val log: Logger = LoggerFactory.getLogger("FoodController")
    }
}