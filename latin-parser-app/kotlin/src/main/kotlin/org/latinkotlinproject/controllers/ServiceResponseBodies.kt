package org.latinkotlinproject.controllers

class ServiceResponseBodies {

    data class ParserServiceResponse<T>(
        var status: Int,
        var message: String,
        val data: T? = null
    )
}