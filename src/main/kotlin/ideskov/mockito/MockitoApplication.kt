package ideskov.mockito

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@SpringBootApplication
class MockitoApplication

fun main(args: Array<String>) {
	runApplication<MockitoApplication>(*args)
}

@Configuration
class ApiRoutes {

	@Bean
	fun mockitoRouter(handler: MockitoHandler) = router {
		GET("/mockito", handler::sayHi)
	}
}

@Component
class MockitoHandler(val veryComplexComponent: VeryComplexComponent) {

	fun sayHi(request: ServerRequest) =
			veryComplexComponent
					.compute(Input(request.queryParam("input").orElse("")))
					.flatMap { ok().body(fromValue(it)) }
}

@Component
class VeryComplexComponent {

	fun compute(input: Input): Mono<String> {
		return "Done with $input".toMono()
	}
}

inline class Input(val input: String)
