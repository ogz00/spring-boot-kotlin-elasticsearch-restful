package com.oguz.kotlin.client

import com.oguz.kotlin.model.Person
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.search.SearchHits

/**
 * Created by oguzhankaracullu on 06/07/2017.
 */
interface ESClient {

    fun createIndex()
    fun putDocument(p:Person): IndexResponse?
    fun search(p:String): SearchHits
    fun createTemplate()
}