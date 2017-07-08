package com.oguz.kotlin.client

import com.oguz.kotlin.config.ESClientConfig
import com.oguz.kotlin.model.Person
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHits
import org.springframework.stereotype.Service

/**
 * Created by oguzhankaracullu on 06/07/2017.
 */
@Service
class ESPersonClient(val esClientConfig: ESClientConfig) : ESClient {

    val INDEX = "people_1_june"
    val TYPE = "people_type"
    override fun connectionES() {
        val client: TransportClient? = esClientConfig.getClient()
        println(client!!.connectedNodes().size)
    }

    override fun createIndex() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putDocument(person: Person) {
        val client: TransportClient? = esClientConfig.getClient()
        val bulkRequest: BulkRequestBuilder = client!!.prepareBulk()
        bulkRequest.add(client.prepareIndex(INDEX, TYPE)
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id", person.id)
                        .field("name", person.name)
                        .field("surname", person.surname)
                        .field("username", person.username)
                        .field("age", person.age)
                        .endObject()))
        val bulkResponse: BulkResponse = bulkRequest.get()
        if (bulkResponse.hasFailures()) {
            println(bulkResponse.buildFailureMessage())
        }
    }

    override fun search(person: String): SearchHits {
        val client: TransportClient? = esClientConfig.getClient()
        println(client!!.nodeName())

        val resp: SearchResponse = client!!.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("name", person)))
                .setSize(1)
                .get()
        return resp.hits
    }

    fun searchAll(): SearchResponse? {
        val client: TransportClient? = esClientConfig.getClient()
        println(client!!.nodeName())

        return client!!.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .get()
    }
}