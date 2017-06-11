package com.oguz.kotlin.actuator

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */

import org.springframework.boot.actuate.endpoint.AbstractEndpoint
import org.springframework.boot.actuate.endpoint.Endpoint
import org.springframework.stereotype.Component

@Component
class ShowEndpoints(private val endpoints: List<Endpoint<*>>) : AbstractEndpoint<List<Endpoint<*>>>("showEndpoints") {

    override fun invoke(): List<Endpoint<*>> {
        return this.endpoints
    }
}
