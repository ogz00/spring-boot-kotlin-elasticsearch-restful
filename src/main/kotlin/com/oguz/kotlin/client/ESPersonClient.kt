package com.oguz.kotlin.client

import com.oguz.kotlin.model.Person
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.common.xcontent.XContentFactory.*
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.xcontent.XContentBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHits
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import org.elasticsearch.cluster.health.ClusterHealthStatus


/**
 * Created by oguzhankaracullu on 06/07/2017.
 */
@Service
class ESPersonClient(val transportClient: Client) : ESClient {

    val INDEX = "user_"
    val TYPE = "customer"

    private fun getDate():String {
        return SimpleDateFormat("yyyy-MM").format(Date())
    }


    override fun putTemplate(): PutIndexTemplateResponse? {
        return transportClient.admin().indices().preparePutTemplate("template_user")
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", 1)
                        .put(",ndex.number_of_replicas", 1))
                .setOrder(0)
                .setTemplate("user_*")
                .addMapping("customer", getMapping())
                .execute().actionGet()


    }

    private fun getMapping(): XContentBuilder? {
        return jsonBuilder()
                .startObject()
                .startObject("customer")
                .startObject("properties")
                .startObject("name")
                .field("type", "text")
                .startObject("fields")
                .startObject("keyword")
                .field("ignore_above", 256)
                .field("type", "keyword")
                .endObject()
                .endObject()
                .endObject()


    }

    override fun getClusterHealth(): ClusterHealthResponse {
        return transportClient.admin().cluster().prepareHealth().get()
    }

    override fun getIndicesHealthMaps(): MutableMap<String, MutableList<String>> {
        val greenIndices = mutableListOf<String>()
        val redIndices = mutableListOf<String>()
        val yellowIndices = mutableListOf<String>()
        for (health in getClusterHealth().indices.values) {
            val index = health.index
            val status = health.status
            if (status == ClusterHealthStatus.GREEN) {
                greenIndices.add(index)
            } else if (status == ClusterHealthStatus.RED) {
                redIndices.add(index)
            } else if (status == ClusterHealthStatus.YELLOW) {
                yellowIndices.add(index)
            }else{
                throw RuntimeException("$index is in $status state")
            }
        }
        val indexMap: MutableMap<String, MutableList<String>> = hashMapOf("green" to greenIndices,
                "yellow" to yellowIndices, "red" to redIndices)
        return indexMap


    }

    override fun createTemplate() {
        transportClient.admin().indices().preparePutTemplate("person_2017")
    }

    override fun putDocument(person: Person): IndexResponse? {
        val response: IndexResponse? = transportClient.prepareIndex("$INDEX${getDate()}", TYPE)
                .setSource(jsonBuilder()
                        .startObject()
                        .field("name", person.name)
                        .field("surname", person.surname)
                        .field("username", person.username)
                        .field("age", person.age)
                        .field("created_at", SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Date()))
                        .endObject()).get()
        return response
    }

    override fun searchWithName(person: String, index:String): SearchResponse? {

        val resp: SearchResponse = transportClient.prepareSearch(index)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("name", person))
                .setPostFilter(QueryBuilders.rangeQuery("age").gte(14).lt(65))
                .setSize(50).setExplain(true)
                .get()
        return resp
    }

    fun searchAll(): SearchResponse? {

        return transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .get()
    }
}