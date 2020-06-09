package b2boost.assignment

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Integration
class HealthCheckFunctionalSpec extends Specification {

    @Shared
    @AutoCleanup
    HttpClient client

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        client  = HttpClient.create(new URL(baseUrl))
    }

    void 'Test the health check endpoint'() {
        when: 'The health check is requested'
            String path = "/actuator/health"
            HttpResponse<List<Map>> response = client.toBlocking()
                    .exchange(HttpRequest.GET(path), Argument.of(List, Map))

        then: 'The response is OK with UP'
            response.status == HttpStatus.OK
            response.body().size() == 1
            response.body().get(0).status == "UP"
    }

}
