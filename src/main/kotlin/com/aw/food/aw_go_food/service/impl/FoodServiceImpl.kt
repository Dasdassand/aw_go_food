package com.aw.food.aw_go_food.service.impl

import com.aw.food.aw_go_food.controller.FoodController
import com.aw.food.aw_go_food.converter.toDto
import com.aw.food.aw_go_food.converter.toEntity
import com.aw.food.aw_go_food.converter.toFood
import com.aw.food.aw_go_food.dto.AddFoodDTO
import com.aw.food.aw_go_food.dto.GetFoodDTO
import com.aw.food.aw_go_food.dto.ReduceQuantityDTO
import com.aw.food.aw_go_food.dto.enums.Operations
import com.aw.food.aw_go_food.repository.FoodRepository
import com.aw.food.aw_go_food.service.FoodService
import com.aw.food.aw_go_food.validator.validate
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import java.util.*

@Service
class FoodServiceImpl(val repository: FoodRepository) : FoodService {


    override fun addFood(food: AddFoodDTO): GetFoodDTO {
        food.validate()
        return repository.save(food.toEntity()).toDto()
    }

    @Cacheable("foods")
    override fun getFood(id: UUID) = repository.findById(id).orElse(null)?.toDto()
        ?: run {
            FoodController.log.error("Food with $id not found")
            throw ChangeSetPersister.NotFoundException()
        }

    @CachePut(value = ["foods"], key = "#result.id")
    override fun editQuantity(request: ReduceQuantityDTO) = getFood(request.id).toFood().apply {
        quantity = when (request.operation) {
            Operations.MINUS -> quantity - request.count
            Operations.PLUS -> quantity + request.count
        }

        if (quantity == 0) {
            repository.delete(this)
        } else {
            repository.save(this)
        }

    }.toDto()

}