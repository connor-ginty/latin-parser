package org.latinkotlinproject.controllers

import com.google.gson.JsonObject
import org.latinkotlinproject.services.VocabService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/latinParser/v1")
class VocabController {

    val vocabService = VocabService()

    @GetMapping("/getDefinition", produces = ["application/json"])
    fun getVocabData(@RequestParam latinWord: String): JsonObject {
        return vocabService.getVocabInformation(latinWord)
    }

}