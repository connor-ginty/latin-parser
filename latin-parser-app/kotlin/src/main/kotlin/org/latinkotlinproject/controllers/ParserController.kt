package org.latinkotlinproject.controllers

import org.latinkotlinproject.impl.Parser
import org.latinkotlinproject.data.ParsingData
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.latinkotlinproject.controllers.ServiceResponseBodies.*

@RestController
@RequestMapping("/latinParser/v1")
class ParserController {

  val parser = Parser()

  @GetMapping("/parseWordV1")
  fun getParsingData(@RequestParam latinWord: String): List<ParsingData> {
    return parser.parseWord(latinWord)
  }

  @GetMapping("/parseWordV2")
  fun getParsingDataV2(@RequestParam latinWord: String): ParserServiceResponse<List<ParsingData>> {
    val parsedData: List<ParsingData> = parser.parseWord(latinWord)

    val parsedDataResponse = ParserServiceResponse<List<ParsingData>>(
      status = HttpStatus.OK.value(),
      message = "Showing results for $latinWord",
      data = parser.parseWord(latinWord)
    )

    if (parsedData.isEmpty()) {
      parsedDataResponse.status = HttpStatus.NOT_FOUND.value()
      parsedDataResponse.message = "$latinWord is not a Latin word. Please try another word."
    }

    return parsedDataResponse
  }
}