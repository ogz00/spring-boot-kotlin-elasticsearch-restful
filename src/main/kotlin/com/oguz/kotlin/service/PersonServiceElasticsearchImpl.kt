package com.oguz.kotlin.service

import com.oguz.kotlin.model.Person
import com.oguz.kotlin.repositories.PersonElasticRepository
import org.springframework.stereotype.Service

/**
 * Created by oguzhankaracullu on 12/06/2017.
 */
@Service
class PersonServiceElasticsearchImpl(val personElasticRepository: PersonElasticRepository) : PersonService {
    override fun getAllPeople(): MutableIterable<Person>? {
        return personElasticRepository.findAll()
    }

    override fun getCount(): Long {
        return personElasticRepository.count()
    }

    override fun createPerson(person: Person): Person? {
        return personElasticRepository.save(person)
    }

    override fun editPerson(person: Person): Person? {
        return personElasticRepository.save(person)
    }

    override fun createBulkPerson(personlist: List<Person>): MutableIterable<Person>? {
       return personElasticRepository.save(personlist)
    }

    override fun findByUsername(username: String): Person? {
       return personElasticRepository.findByUsername(username)
    }

    override fun findByName(name: String): MutableIterable<Person>? {
       return personElasticRepository.findByName(name)
    }
}
