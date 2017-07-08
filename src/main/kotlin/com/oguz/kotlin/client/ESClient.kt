package com.oguz.kotlin.client

import com.oguz.kotlin.model.Person
import org.elasticsearch.search.SearchHits

/**
 * Created by oguzhankaracullu on 06/07/2017.
 */
interface ESClient {
    fun connectionES()
    fun createIndex()
    fun putDocument(p:Person)
    fun search(p:String): SearchHits
}