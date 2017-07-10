package com.oguz.kotlin.config;


import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import java.net.InetAddress
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean


/**
 * Created by oguzhankaracullu on 16/06/2017.
 */
class TransportClientFactoryBean : FactoryBean<TransportClient>, InitializingBean, DisposableBean {
    override fun getObjectType(): Class<*> {
        return TransportClient::class.java
    }

    override fun isSingleton(): Boolean {
        return true
    }

    override fun getObject(): TransportClient? {
        return this.client
    }

    override fun afterPropertiesSet() {
        createTransportClient()
    }

    override fun destroy() {
        if (this.client != null) {
            this.client?.close()
        }

    }

    private var client: TransportClient? = null
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var EsClusterNodes = ""


    var EsClusterName = ""

    fun createTransportClient() {
        val server = EsClusterNodes?.split(":")?.get(0)
        val port = Integer.parseInt(EsClusterNodes?.split(":")?.get(1))
        try {

            var esSettings: Settings? = Settings.builder()
                    .put("cluster.name", EsClusterName)
                    .put("client.transport.sniff", false)
                    .put("xpack.security.user", "elastic:changeme")
                    .build()



            client = PreBuiltXPackTransportClient(esSettings)
                    .addTransportAddress(
                            InetSocketTransportAddress(InetAddress.getByName(server), port)
                    )
        } catch (e: Exception) {
            throw Exception(e)
        }

    }


    /*  @Bean
      fun elasticsearchTemplate(): ElasticsearchOperations {
          return ElasticsearchTemplate(client()) as ElasticsearchOperations
      }*/

}
