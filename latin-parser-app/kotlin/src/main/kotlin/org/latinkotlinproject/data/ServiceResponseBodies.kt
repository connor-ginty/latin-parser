package org.latinkotlinproject.data

class ServiceResponseBodies {

    data class ParserServiceResponse<T>(
        var success: Boolean,
        var message: String,
        val inflections: T? = null
    )

    data class VocabServiceResponse<T>(
        var success: Boolean,
        var message: String,
        val entries: T? = null
    )
}