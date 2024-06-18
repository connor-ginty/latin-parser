package org.latinkotlinproject.controllers

import org.latinkotlinproject.Parser
import org.latinkotlinproject.data.ParsingData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/latinParser/v1")
class ParserController {

  val parser = Parser()

  @GetMapping("/parseWord")
  fun getParsingData(@RequestParam latinWord: String): List<ParsingData> {
    return parser.parseWord(latinWord)
  }
}