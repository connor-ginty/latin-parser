//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.server.LocalServerPort
//import org.springframework.http.ResponseEntity
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//import org.springframework.web.client.RestTemplate
//import org.springframework.web.client.getForObject
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//class LatinKotlinProjectApplicationTests(restTemplate: Any) {
//
//	@LocalServerPort
//	private var port: Int = 8080
//	private var testWord = "examen"
//
//	@Autowired
//	private lateinit var mockMvc: MockMvc
//
//	@Test
//	fun testEndpoint(){
//		val url = "http://localhost:$port/parseWord?latinWord=$testWord"
//
//		mockMvc.perform(MockMvcRequestBuilders.get(url))
//			.andExpect(status().isOk)
//		}
//	}
