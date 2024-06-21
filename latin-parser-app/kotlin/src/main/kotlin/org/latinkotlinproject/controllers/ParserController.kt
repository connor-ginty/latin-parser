package org.latinkotlinproject.controllers

import org.latinkotlinproject.impl.Parser
import org.latinkotlinproject.data.ParsingData
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.latinkotlinproject.controllers.ServiceResponseBodies.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/latinParser")
class ParserController {

  val parser = Parser()

  // Keeping to remember humble beginnings
  @GetMapping("/v1/parseWord")
  fun getParsingData(@RequestParam latinWord: String): List<ParsingData> {
    return parser.parseWord(latinWord)
  }

  @GetMapping("/v2/parseWord")
  fun getParsingDataV2(@RequestParam latinWord: String): ResponseEntity<Any> {
    val parsedData: List<ParsingData> = parser.parseWord(latinWord)

    val parsedDataResponse = ParserServiceResponse<List<ParsingData>>(
      success = true,
      message = "Showing results for $latinWord",
      parsedDataList = parsedData
    )

    return if (parsedData.isEmpty()) {
      parsedDataResponse.success = false
      parsedDataResponse.message = "$latinWord is not a Latin word. Please try another word."
      ResponseEntity.status(HttpStatus.NOT_FOUND).body(parsedDataResponse)
    } else {
      ResponseEntity.ok(parsedDataResponse)
    }
  }
}