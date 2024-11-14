package com.aw.food.aw_go_food.service.impl

import com.aw.audit.service.AuditSender
import com.aw.food.aw_go_food.audit.AuditEvent
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
class FoodServiceImpl(
    val repository: FoodRepository,
    val auditSender: AuditSender
) : FoodService {


    override fun addFood(food: AddFoodDTO): GetFoodDTO {
        food.validate()
        val foodDto = repository.save(food.toEntity()).toDto()
        auditSender.sendMessage("${AuditEvent.ADD_FOOD.name} ${foodDto.id}")
        return foodDto
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
            Operations.MINUS -> {
                auditSender.sendMessage("${AuditEvent.MINUS_FOOD.name} $id")
                quantity - request.count
            }

            Operations.PLUS -> {
                auditSender.sendMessage("${AuditEvent.PLUS_FOOD.name} $id")
                quantity + request.count
            }
        }

        if (quantity == 0) {
            repository.delete(this)
            auditSender.sendMessage("${AuditEvent.DELETE_FOOD.name} $id")
        } else {
            repository.save(this)
        }

    }.toDto()

}