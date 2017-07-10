package com.oguz.kotlin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.google.gson.annotations.SerializedName
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

/**
 * Created by oguzhankaracullu on 11/06/2017.
 */
//@Document(indexName = "people_1_june", type = "people_type", putTemplate = true )
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "people", uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("username"))))
data class Person(
        @JsonIgnore
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: UUID? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("surname") val surname: String? = null,
        @SerializedName("username") val username: String? = null,
        @SerializedName("age") val age: Int? = null)

