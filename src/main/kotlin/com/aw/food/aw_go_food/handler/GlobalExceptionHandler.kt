package com.aw.food.aw_go_food.handler

import com.aw.food.aw_go_food.controller.FoodController
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<String> {
        FoodController.log.error("Invalid argument: ${ex.message}", ex)
        return ResponseEntity("Invalid input: ${ex.message}", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException::class)
    fun handleNotFoundException(ex: ChangeSetPersister.NotFoundException, request: WebRequest): ResponseEntity<String> {
        FoodController.log.error("Other exception: ${ex.message}", ex)
        return ResponseEntity("Resource not found: ${ex.message}", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<String> {
        FoodController.log.error("Error occurred: ${ex.message}", ex)
        return ResponseEntity("An unexpected error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }


}