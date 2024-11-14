package com.aw.food.aw_go_food.scheduler

import com.aw.audit.service.AuditSender
import com.aw.food.aw_go_food.audit.AuditEvent
import com.aw.food.aw_go_food.repository.FoodRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.context.ApplicationContext
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDate
import java.util.*

@Component
class ExpiredProductsScheduler(
    private val foodRepository: FoodRepository,
    private val audit: AuditSender,
    @Value("\${subsystem.code}")
    private val subSystemCode: String
) {
    @Autowired
    private lateinit var applicationContext: ApplicationContext
    private val log = LoggerFactory.getLogger(ExpiredProductsScheduler::class.java)

    @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Moscow")
    fun checkExpiredProducts() {
        log.info("Starting check for expired products...")
        val expiredProducts = foodRepository.findAll().filter {
            it.expirationDate.isBefore(LocalDate.now())
        }

        if (expiredProducts.isNotEmpty()) {
            val reportFile = File("logs/expired_products_${LocalDate.now()}.log")
            reportFile.bufferedWriter().use { writer ->
                expiredProducts.forEach { product ->
                    writer.write("ID: ${product.id}, Name: ${product.name}")
                    writer.newLine()
                }
            }
            log.info("Report of expired products written to ${reportFile.absolutePath}")

            expiredProducts.forEach { product ->
                foodRepository.delete(product)
                log.info("Deleted expired product ID: ${product.id}, Name: ${product.name}")
                audit.sendMessage("$subSystemCode ${AuditEvent.DELETE_FOOD.name} ${product.id}")
                val schedulerProxy = applicationContext.getBean(ExpiredProductsScheduler::class.java)
                schedulerProxy.evictCache(product.id)
            }
        } else {
            log.info("No expired products found.")
        }
    }

    @CacheEvict(value = ["foods"], key = "#id")
    fun evictCache(id: UUID) {
        log.info("Evicted cache for product ID: $id")
    }
}
