package b2boost.assignment

import b2boost.assignment.partner.Partner
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import java.time.Instant

@Integration
class PartnerFunctionalSpec extends Specification {

    @Shared
    @AutoCleanup
    HttpClient client

    @Shared
    String resourcePath = '/api/partners'

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        print baseUrl
        client  = HttpClient.create(new URL(baseUrl))
    }

    void setupData() {
        for (int i = 1; i < 20; i++) {
            new Partner(
                companyName:    'company ' + i,
                ref:            'xxxxx' + i,
                locale:         Locale.UK,
                expires:        Date.from(Instant.now())).save()
        }
    }

    Map getValidJson() {
        [
            "name"              : "Bells & Whistles",
            "reference"         : "xxxxxx",
            "locale"            : "en_ES",
            "expirationTime"    : "2017-10-03T12:18:46+00:00"
         ]
    }

    Map getInvalidWrongDateJson() {
        [
                "name"              : "Bells & Whistles",
                "reference"         : "xxxxxx",
                "locale"            : "en_ES",
                "expirationTime"    : "2017-10-03T12:18:46+0:00"
        ]
    }

    Map getInvalidWrongLocaleJson() {
        [
                "name"              : "Bells & Whistles",
                "reference"         : "xxxxxx",
                "locale"            : "en",
                "expirationTime"    : "2017-10-03T12:18:46+00:00"
        ]
    }

    void 'Test the index action'() {
        when: 'The index action is requested'
            HttpResponse<List<Map>> response = client.toBlocking()
                    .exchange(HttpRequest.GET(resourcePath), Argument.of(List, Map))

        then: 'The response is OK with an empty JSON array'
            response.status == HttpStatus.OK
            response.body() == []
            response.headers
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }
    // todo from&size tests

    @Rollback
    void 'Test that the show action correctly renders an instance'() {
        given: 'Database contains partners'
            setupData()

        when: 'The show action is called to retrieve a resource'
            String path = "${resourcePath}/1"
            response = client.toBlocking().exchange(HttpRequest.GET(path), Map)

        then: 'The response is correct and in JSON'
            response.status == HttpStatus.OK
            response.body().id == 1
            response.body().name == 'company1'
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test that the show action return not found if entity does not exist' () {
        when: 'The show action is called to retrieve a non existing resource'
            String path = "${resourcePath}/1"
            client.toBlocking().exchange(HttpRequest.GET(path), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.NOT_FOUND
            e.response.body().code == 404
            e.response.body().message == "Partner with id 1 not found."
    }

    void 'Test that the save action correctly validate a non empty entity'() {
        when: 'The save action is executed with no content'
            client.toBlocking().exchange(HttpRequest.POST(resourcePath, ''), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.BAD_REQUEST
            e.response.body().code == 400
            e.response.body().message ==
                    "Property [name] of class [class b2boost.assignment.partner.PartnerCommand] cannot be null (nullable). " +
                    "Property [reference] of class [class b2boost.assignment.partner.PartnerCommand] cannot be null (nullable). " +
                    "Property [locale] of class [class b2boost.assignment.partner.PartnerCommand] cannot be null (nullable). " +
                    "Property [expirationTime] of class [class b2boost.assignment.partner.PartnerCommand] cannot be null (nullable). "
            e.response.headers.get("Content-Type")
                .contains("application/json")
    }

    void 'Test that the save action correctly validate a wrong locale'() {
        when: 'The save action is executed with a wrong locale'
            client.toBlocking().exchange(HttpRequest.POST(resourcePath, invalidWrongLocaleJson), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.BAD_REQUEST
            e.response.body().code == 400
            e.response.body().message == "Property [locale] of class [class b2boost.assignment.partner.PartnerCommand] " +
                    "with value [en] does not pass custom validation (country is mandatory). "
            e.response.headers.get("Content-Type")
                .contains("application/json")
    }

    void 'Test that the save action correctly validate a wrong date'() {
        when: 'The save action is executed with a wrong date'
            client.toBlocking().exchange(HttpRequest.POST(resourcePath, invalidWrongDateJson), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.BAD_REQUEST
            e.response.body().code == 400
            e.response.body().message == "Unparseable date: \"2017-10-03T12:18:46+0:00\" (typeMismatch). "
            e.response.headers.get("Content-Type")
                .contains("application/json")
    }

    @Rollback
    void 'Test that the save action correctly persists an instance'() {
        when: 'The save action is executed with valid data'
            HttpResponse<Map> response = client.toBlocking().exchange(HttpRequest.POST(resourcePath, validJson), Map)

        then: 'The response is correct and in JSON'
            response.status == HttpStatus.CREATED
            response.body().id
            Partner.count() == 1
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

//    void 'Test the update action correctly updates an instance'() {
//        when: 'The save action is executed with valid data'
//        HttpResponse<Map> response = client.toBlocking().exchange(HttpRequest.POST(resourcePath, validJson), Map)
//
//        then: 'The response is correct'
//        response.status == HttpStatus.CREATED
//        response.body().id
//
//        when: 'The update action is called with invalid data'
//        String path = "${resourcePath}/${response.body().id}"
//        client.toBlocking().exchange(HttpRequest.PUT(path, invalidJson), Map)
//
//        then: 'The response is unprocessable entity'
//        path
//        def e = thrown(HttpClientResponseException)
//        e.response.status == HttpStatus.UNPROCESSABLE_ENTITY
//
//        when: 'The update action is called with valid data'
//        response = client.toBlocking().exchange(HttpRequest.PUT(path, validJson), Map)
//
//        then: 'The response is correct'
//        response.status == HttpStatus.OK
//        response.body()
//
//        cleanup:
//        response = client.toBlocking().exchange(HttpRequest.DELETE(path))
//        assert response.status() == HttpStatus.NO_CONTENT
//    }
//
//
//    @SuppressWarnings('MethodName')
//    @Rollback
//    void 'Test the delete action correctly deletes an instance'() {
//        when: 'The save action is executed with valid data'
//        HttpResponse<Map> response = client.toBlocking().exchange(HttpRequest.POST(resourcePath, validJson), Map)
//
//        then: 'The response is correct'
//        response.status == HttpStatus.CREATED
//        response.body().id
//
//        when: 'When the delete action is executed on an unknown instance'
//        def id = response.body().id
//        def path = "${resourcePath}/99999"
//        client.toBlocking().exchange(HttpRequest.DELETE(path))
//
//        then: 'The response is not found'
//        def e = thrown(HttpClientResponseException)
//        e.response.status == HttpStatus.NOT_FOUND
//
//        when: 'When the delete action is executed on an existing instance'
//        path = "${resourcePath}/${id}"
//        response = client.toBlocking().exchange(HttpRequest.DELETE(path))
//
//        then: 'The response is no content'
//        response.status == HttpStatus.NO_CONTENT
//        !Partner.get(id)
//    }
}
