package com.oguz.kotlin.Controller

import com.google.gson.Gson
import com.oguz.kotlin.Model.Person
import com.oguz.kotlin.Service.PersonServiceImpl
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
class PersonController(val personServiceImpl: PersonServiceImpl) {


    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/all")
    fun person(): MutableIterable<Person>? {
        return personServiceImpl.getAllPeople()
    }

    @Throws(Exception::class)
    @RequestMapping(value = "", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody fun person(@RequestBody person: String, request: HttpServletRequest): ResponseEntity<*> {

        val created: Person = Gson().fromJson(person, Person::class.java)
        personServiceImpl.createPerson(created)
        return ResponseEntity.ok(created)


    }


    @Throws(Exception::class)
    @RequestMapping(value = "{username}", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    @ResponseBody fun getPersonWithUsername(@PathVariable username: String): Person{
        return personServiceImpl.findByUsername(username)!!

    }

}

