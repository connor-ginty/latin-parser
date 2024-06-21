package org.latinkotlinproject.controllers

class ServiceResponseBodies {

    data class ParserServiceResponse<T>(
        var success: Boolean,
        var message: String,
        val parsedDataList: T? = null
    )

    data class VocabServiceResponse<T>(
        var success: Boolean,
        var message: String,
        val parsedDataList: T? = null
    )
}