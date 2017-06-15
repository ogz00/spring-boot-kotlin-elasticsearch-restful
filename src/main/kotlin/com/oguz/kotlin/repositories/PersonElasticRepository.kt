package com.oguz.kotlin.repositories

import com.oguz.kotlin.model.Person
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

/**
 * Created by oguzhankaracullu on 12/06/2017.
 */
@Repository
interface PersonElasticRepository : ElasticsearchRepository<Person, Long> {

    fun findByUsername(username: String): Person?

    fun findByName(name:String): MutableList<Person>?

}
