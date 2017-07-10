package com.oguz.kotlin.client

import com.oguz.kotlin.model.Person
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.search.SearchHits

/**
 * Created by oguzhankaracullu on 06/07/2017.
 */
interface ESClient {

    fun putTemplate(): PutIndexTemplateResponse?
    fun putDocument(p:Person): IndexResponse?
    fun createTemplate()
    fun getIndicesHealthMaps(): MutableMap<String, MutableList<String>>
    fun getClusterHealth(): ClusterHealthResponse
    fun searchWithName(person: String, index: String): SearchResponse?
}