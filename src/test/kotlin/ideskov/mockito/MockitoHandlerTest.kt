package ideskov.mockito

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when` as mockitoWhen
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.kotlin.core.publisher.toMono

@WebFluxTest(MockitoHandler::class, ApiRoutes::class)
internal class MockitoHandlerTest {

    @Autowired
    lateinit var client: WebTestClient

    @MockBean
    lateinit var veryComplexComponent: VeryComplexComponent

    @Test
    fun `test mockito argument matchers`() {
        mockitoWhen(veryComplexComponent.compute(any(Input::class.java))).thenReturn("Mocked done".toMono())

        client
                .get()
                .uri("/mockito?input=blah")
                .exchange()
                .expectStatus().is2xxSuccessful
    }
}
