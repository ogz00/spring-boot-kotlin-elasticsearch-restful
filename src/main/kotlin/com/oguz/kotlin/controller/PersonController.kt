package com.oguz.kotlin.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oguz.kotlin.client.ESPersonClient
import com.oguz.kotlin.model.Person
import com.oguz.kotlin.service.PersonServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMapping


/**
 * Created by oguzhankaracullu on 11/06/2017.
 */
@RestController
@RequestMapping("/person")
class PersonController(val personServiceImpl: PersonServiceImpl, val esPersonClient: ESPersonClient) {


    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/all")
    fun person(): ResponseEntity<*> {

        val gson: Gson = GsonBuilder().create()
        var results = ArrayList<Person>()

        try {
            val hits = esPersonClient.searchAll()?.hits?.hits

            if (hits != null && hits.isNotEmpty()) {
                hits.mapTo(results) { gson.fromJson(it.sourceAsString, Person::class.java) }
                return ResponseEntity.ok(gson.toJson(results))
            }
            else
                return ResponseEntity.ok(personServiceImpl.getAllPeople())
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity<Any>(HttpStatus.BAD_REQUEST)
        }


    }

    @Throws(Exception::class)
    @RequestMapping(value = "", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody fun person(@RequestBody person: String, request: HttpServletRequest): ResponseEntity<*> {

        val created: Person = Gson().fromJson(person, Person::class.java)
        personServiceImpl.createPerson(created)
        //∂ esPersonClient.createPerson(created)
        return ResponseEntity.ok(created)


    }


    @Throws(Exception::class)
    @RequestMapping(value = "{username}", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody fun getPersonWithUsername(@PathVariable username: String): Person {
        return personServiceImpl.findByUsername(username)!!

    }

}

