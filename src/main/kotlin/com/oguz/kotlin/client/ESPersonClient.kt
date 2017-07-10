package com.oguz.kotlin.client

import com.oguz.kotlin.model.Person
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.common.xcontent.XContentFactory.*
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHits
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by oguzhankaracullu on 06/07/2017.
 */
@Service
class ESPersonClient(val transportClient: Client) : ESClient {

    val INDEX = "people_1_june"
    val TYPE = "people_type"


    override fun createIndex() {
        transportClient.admin().indices().prepareCreate("persons")
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", 1)
                        .put(",ndex.number_of_replicas",1))
                .addMapping("")


    }

    override fun createTemplate(){
        transportClient.admin().indices().preparePutTemplate("person_2017")
    }

    override fun putDocument(person: Person): IndexResponse? {
        val response: IndexResponse? = transportClient.prepareIndex(INDEX, TYPE)
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

    override fun search(person: String): SearchHits {

        val resp: SearchResponse = transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("name", person)))
                .setSize(1)
                .get()
        return resp.hits
    }

    fun searchAll(): SearchResponse? {

        return transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .get()
    }
}