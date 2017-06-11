package com.oguz.kotlin.Repositories

import com.oguz.kotlin.Model.Person
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
interface PersonRepository : PagingAndSortingRepository<Person, Long> {

    fun findByUsername(username: String): Person?

    fun findByName(name: String): MutableIterable<Person>?
}