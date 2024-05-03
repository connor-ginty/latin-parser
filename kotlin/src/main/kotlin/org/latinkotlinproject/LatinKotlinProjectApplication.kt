package org.latinkotlinproject

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class LatinKotlinProjectApplication

fun main(args: Array<String>) {
	SpringApplication.run(LatinKotlinProjectApplication::class.java, *args)
}

@Configuration
class AppConfig : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8081")
			.allowedMethods("GET")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(3600)
	}
}
