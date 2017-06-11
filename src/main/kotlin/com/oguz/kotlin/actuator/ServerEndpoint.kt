package com.oguz.kotlin.actuator

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */
import java.net.InetAddress
import java.util.ArrayList

import org.springframework.boot.actuate.endpoint.Endpoint
import org.springframework.stereotype.Component

@Component
class ServerEndpoint : Endpoint<MutableList<String>> {

    override fun getId(): String {
        return "server"
    }

    override fun invoke(): MutableList<String> {
        val serverDetails: MutableList<String> = ArrayList()
        try {
            serverDetails.add("Server IP Address : " + InetAddress.getLocalHost().hostAddress)
            serverDetails.add("Server OS : " + System.getProperty("os.name").toLowerCase())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return serverDetails
    }

    override fun isEnabled(): Boolean = true

    override fun isSensitive(): Boolean = false

}
