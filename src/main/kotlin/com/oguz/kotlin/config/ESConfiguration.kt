package com.oguz.kotlin.config

import org.elasticsearch.client.transport.TransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by oguzhankaracullu on 09/07/2017.
 */

@Configuration
@EnableAutoConfiguration
class ESConfiguration {

    @Value("\${spring.data.elasticsearch.cluster-nodes}")
    private val EsClusterNodes: String? = null

    @Value("\${spring.data.elasticsearch.cluster-name}")
    private val EsClusterName: String? = null

    @Bean
    @ConditionalOnMissingBean
    fun elasticsearchClient(): TransportClientFactoryBean {

        var facoryBean:TransportClientFactoryBean = TransportClientFactoryBean()
        facoryBean.EsClusterName = this.EsClusterName!!
        facoryBean.EsClusterNodes = this.EsClusterNodes!!
        //facoryBean.afterPropertiesSet()
        //val client: TransportClient = facoryBean.`object`!!

        return facoryBean
    }
}