package com.oguz.kotlin.config;

import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.xpack.XPackClient
import org.elasticsearch.xpack.XPackPlugin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import java.net.InetAddress
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.auth.AuthScope
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.client.CredentialsProvider
import org.elasticsearch.client.RestClientBuilder
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.transport.NoNodeAvailableException
import org.elasticsearch.transport.client.PreBuiltTransportClient


/**
 * Created by oguzhankaracullu on 16/06/2017.
 */
@Configuration
class ElasticsearchConfig {
    @Value("\${spring.data.elasticsearch.cluster-nodes}")
    private val EsClusterNodes: String? = null

    @Value("\${spring.data.elasticsearch.cluster-name}")
    private val EsClusterName: String? = null

    @Bean
    fun client(): Client? {
        val server = EsClusterNodes?.split(":")?.get(0)
        val port = Integer.parseInt(EsClusterNodes?.split(":")?.get(1))

        var esSettings: Settings? = Settings.settingsBuilder()
                .put("cluster-name", EsClusterName)
                .put("client.transport.sniff", false)

                .build()


        return PreBuiltTransportClient.builder()
                .settings(esSettings)
                .build()
                .addTransportAddress(
                        InetSocketTransportAddress(InetAddress.getByName(server), port)
                )

    }

    @Bean
    fun elasticsearchTemplate(): ElasticsearchOperations {
        return ElasticsearchTemplate(client())
    }

}
