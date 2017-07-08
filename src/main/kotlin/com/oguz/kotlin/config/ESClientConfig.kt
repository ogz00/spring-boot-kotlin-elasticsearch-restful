package com.oguz.kotlin.config;


import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.springframework.beans.factory.annotation.Value
import java.net.InetAddress
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import java.net.UnknownHostException


/**
 * Created by oguzhankaracullu on 16/06/2017.
 */
@EnableAutoConfiguration
@Configuration
class ESClientConfig {
    @Value("\${spring.data.elasticsearch.cluster-nodes}")
    private val EsClusterNodes: String? = null

    @Value("\${spring.data.elasticsearch.cluster-name}")
    private val EsClusterName: String? = null

    fun getClient(): TransportClient? {
        val server = EsClusterNodes?.split(":")?.get(0)
        val port = Integer.parseInt(EsClusterNodes?.split(":")?.get(1))
        try {

            var esSettings: Settings? = Settings.builder()
                    .put("cluster.name", EsClusterName)
                    .put("client.transport.sniff", false)
                    .put("xpack.security.user", "elastic:changeme")
                    .build()



            return PreBuiltXPackTransportClient(esSettings)
                    .addTransportAddress(
                            InetSocketTransportAddress(InetAddress.getByName(server), port)
                    )
        }catch (e:UnknownHostException){
            return null
        }

    }


  /*  @Bean
    fun elasticsearchTemplate(): ElasticsearchOperations {
        return ElasticsearchTemplate(client()) as ElasticsearchOperations
    }*/

}
