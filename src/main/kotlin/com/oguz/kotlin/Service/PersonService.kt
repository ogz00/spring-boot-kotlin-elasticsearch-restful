package com.oguz.kotlin.Service

import com.oguz.kotlin.Model.Person

/**
 * Created by oguzhankaracullu on 12/06/2017.
 */
interface PersonService {

    fun getAllPeople(): MutableIterable<Person>?

    fun getCount(): Long

    fun createPerson(person: Person): Person?

    fun editPerson(person: Person): Person?

    fun createBulkPerson(personlist: List<Person>): MutableIterable<Person>?

    fun findByUsername(username: String): Person?

    fun findByName(name: String): MutableIterable<Person>?

}