package com.oguz.kotlin.Controller

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.sql.SQLException


/**
 * Created by oguzhankaracullu on 12/06/2017.
 */

data class ErrorResponse(val statusCode: Int,  val statusMessage: String, val message: String)

@ControllerAdvice
@RestController
class GenericRepositoryRestExceptionHandler {

    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun onException(ex: Throwable): ResponseEntity<ErrorResponse>
    {
        val httpError = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message ?: "Internal Server Error")
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseBody
    internal fun handleConflict(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        val httpError = HttpStatus.CONFLICT
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message ?: "Operation cannot be performed. Integrity Constraint violated")
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(NullPointerException::class)
    @ResponseBody
    internal fun handleNull(ex: NullPointerException): ResponseEntity<*> {
        val httpError = HttpStatus.NOT_FOUND
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message ?: "Requested Data Not Found")
        return ResponseEntity(errorResponse, httpError)
    }

    @ExceptionHandler(SQLException::class)
    @ResponseBody
    internal fun handleSqlException(ex: SQLException): ResponseEntity<*> {
        val httpError = HttpStatus.BAD_REQUEST
        val errorResponse = ErrorResponse(httpError.value(), httpError.reasonPhrase, ex.message ?: "Bad Request")
        return ResponseEntity(errorResponse, httpError)
    }
}