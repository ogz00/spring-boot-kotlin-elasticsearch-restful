package com.oguz.kotlin.repositories

import com.oguz.kotlin.model.Person
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */
@Repository
interface PersonRepository : PagingAndSortingRepository<Person, Long> {

    fun findByUsername(username: String): Person?

    fun findByName(name: String): MutableIterable<Person>?
}