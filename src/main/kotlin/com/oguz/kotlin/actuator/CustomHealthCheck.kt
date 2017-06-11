package com.oguz.kotlin.actuator;

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class CustomHealthCheck : HealthIndicator {
    override fun health(): Health {
        val errorCode = 0
        if (errorCode != 1) {
            return Health.down().withDetail("Error Code", errorCode).build()
        } else {
            return Health.up().build()
        }

    }

}