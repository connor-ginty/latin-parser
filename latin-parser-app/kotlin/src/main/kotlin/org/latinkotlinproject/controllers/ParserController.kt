package org.latinkotlinproject.controllers

import jakarta.validation.constraints.NotBlank
import org.latinkotlinproject.impl.Parser
import org.latinkotlinproject.data.WordInflection
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.latinkotlinproject.data.ServiceResponseBodies.*
import org.latinkotlinproject.services.PerseusServiceException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

@Validated
@RestController
@RequestMapping("/service/latin")
class ParserController(private val parser: Parser) {

    // Keeping to remember humble beginnings
    @GetMapping("/v1/parseWord")
    fun getParsingData(@NotBlank @RequestParam latinWord: String): List<WordInflection> {
        return parser.parseWord(latinWord)
    }

    @GetMapping("/parser/{word}")
    fun getInflections(@NotBlank @PathVariable word: String): ResponseEntity<Any> {
        return try {
            val parsedData: List<WordInflection> = parser.parseWord(word)

            if (parsedData.isEmpty()) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(ParserServiceResponse<List<WordInflection>>(
                    success = false,
                    message = "$word is not a Latin word. Please try again.",
                    inflections = parsedData
                ))
            } else {
                ResponseEntity.ok(ParserServiceResponse(
                    success = true,
                    message = "Showing results for $word",
                    inflections = parsedData
                ))
            }
        } catch (e: PerseusServiceException) {
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                ParserServiceResponse<List<WordInflection>>(
                    success = false,
                    message = "The Latin parser service is temporarily unavailable. Please try again later.",
                    inflections = emptyList()
                )
            )
        }
    }
}
