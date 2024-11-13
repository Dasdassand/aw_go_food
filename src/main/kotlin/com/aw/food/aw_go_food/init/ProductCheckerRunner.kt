package com.aw.food.aw_go_food.init

import com.aw.food.aw_go_food.scheduler.ExpiredProductsScheduler
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class ProductCheckerRunner(
    private val expiredProductsScheduler: ExpiredProductsScheduler
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        expiredProductsScheduler.checkExpiredProducts()
    }
}
