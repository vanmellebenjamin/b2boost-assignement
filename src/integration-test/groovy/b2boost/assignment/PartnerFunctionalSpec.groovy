package b2boost.assignment

import b2boost.assignment.partner.Partner
import b2boost.assignment.partner.PartnerDataService
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

import java.time.ZonedDateTime

@Integration
class PartnerFunctionalSpec extends Specification {

    @Shared
    @AutoCleanup
    HttpClient client

    @Shared
    String resourcePath = '/api/partners'

    @Shared
    PartnerDataService partnerDataService

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        print baseUrl
        client  = HttpClient.create(new URL(baseUrl))
        populateSampleData()
    }

    void populateSampleData() {
        for (int i = 1; i < 20; i++) {
            Partner partner = new Partner(
                companyName:    'company ' + i,
                ref:            'xxxxx' + i,
                locale:         Locale.UK,
                expires:        ZonedDateTime.now())
            partnerDataService.save(partner)
        }
    }

    Map getValidJson() {
        [
            "name"              : "Bells & Whistles",
            "reference"         : "xxxxxx",
            "locale"            : "en_ES",
            "expirationTime"    : "2017-10-03T12:18:46+03:00"
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

    /**
     * Index Tests
     */
    void 'Test the index action'() {
        when: 'The index action is requested'
            HttpResponse<List<Map>> response = client.toBlocking()
                    .exchange(HttpRequest.GET(resourcePath), Argument.of(List, Map))

        then: 'The response is OK with an empty JSON array'
            response.status == HttpStatus.OK
            response.body().size() == 10
            response.body().get(0).name == "company 1"
            response.body().get(9).name == "company 10"
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test the index action with from set'() {
        when: 'The index action is requested'
            String queryParams = "?from=5"
            String path = resourcePath + queryParams
            HttpResponse<List<Map>> response = client.toBlocking()
                    .exchange(HttpRequest.GET(path), Argument.of(List, Map))

        then: 'The response is OK with an empty JSON array'
            response.status == HttpStatus.OK
            response.body().size() == 10
            response.body().get(0).name == "company 6"
            response.body().get(9).name == "company 15"
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test the index action with size set'() {
        when: 'The index action is requested'
            String queryParams = "?size=5"
            String path = resourcePath + queryParams
            HttpResponse<List<Map>> response = client.toBlocking()
                    .exchange(HttpRequest.GET(path), Argument.of(List, Map))

        then: 'The response is OK with an empty JSON array'
            response.status == HttpStatus.OK
            response.body().size() == 5
            response.body().get(0).name == "company 1"
            response.body().get(4).name == "company 5"
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test the index action with size AND size set'() {
        when: 'The index action is requested'
            String queryParams = "?from=5&size=5"
            String path = resourcePath + queryParams
            HttpResponse<List<Map>> response = client.toBlocking()
                    .exchange(HttpRequest.GET(path), Argument.of(List, Map))

        then: 'The response is OK with an empty JSON array'
            response.status == HttpStatus.OK
            response.body().size() == 5
            response.body().get(0).name == "company 6"
            response.body().get(4).name == "company 10"
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

    /**
     * Show Tests
     */
    void 'Test that the show action correctly renders an instance'() {
        when: 'The show action is called to retrieve a resource'
            def id = 1
            String path = "${resourcePath}/$id"
            HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(path), Map)

        then: 'The response is correct and in JSON'
            response.status == HttpStatus.OK
            response.body().id == id
            response.body().name == 'company 1'
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test that the show action return not found if entity does not exist' () {
        when: 'The show action is called to retrieve a non existing resource'
            def id = 100
            String path = "${resourcePath}/$id"
            client.toBlocking().exchange(HttpRequest.GET(path), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.NOT_FOUND
            e.response.body().code == 404
            e.response.body().message == "Partner with id $id not found."
    }

    /**
     * Save Tests
     */
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
            e.response.body().message == "Text 2017-10-03T12:18:46+0:00 could not be parsed at index 19 (typeMismatch). "
            e.response.headers.get("Content-Type")
                .contains("application/json")
    }

    void 'Test that the save action correctly persists an instance'() {
        when: 'The save action is executed with valid data'
            HttpResponse<Map> response = client.toBlocking().exchange(HttpRequest.POST(resourcePath, validJson), Map)

        then: 'The response is correct and in JSON'
            response.status == HttpStatus.CREATED
            response.body().id
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
            response.body().name == "Bells & Whistles"
            response.body().reference == "xxxxxx"
            response.body().locale == "en_ES"
            response.body().expirationTime == "2017-10-03T09:18:46+00:00"
    }

    /**
     * Delete Tests
     */
    void 'Test the delete action with an existing entity'() {
        when: 'The delete action is call with an existing id'
            def id = 18
            partnerDataService.get(id) != null
            String path = "${resourcePath}/$id"
            HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.DELETE(path), Map)

        then: 'The response is OK, and partner has been removed'
            response.status == HttpStatus.OK
            partnerDataService.get(id) == null
    }

    void 'Test the delete action with non existing entity'() {
        when: 'The delete action is call with a non existing id'
            def id = 100
            String path = "${resourcePath}/$id"
            client.toBlocking().exchange(HttpRequest.DELETE(path), Map)

        then: 'The response is 404 with an error message'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.NOT_FOUND
            e.response.body().code == 404
            e.response.body().message == "Partner with id $id not found."
    }

    /**
     * Update Tests
     */
    void 'Test that the update returns a 404 if entity does not exist'() {
        when: 'The update action is executed with no content'
        def id = 100
            String path = "${resourcePath}/$id"
            client.toBlocking().exchange(HttpRequest.PUT(path, ''), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.NOT_FOUND
            e.response.body().code == 404
            e.response.body().message == "Partner with id $id not found."
            e.response.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test that the update correctly validate a non empty entity'() {
        when: 'The update action is executed with no content'
            def id = 16
            String path = "${resourcePath}/$id"
            client.toBlocking().exchange(HttpRequest.PUT(path, ''), Map)

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

    void 'Test that the update action correctly validate a wrong locale'() {
        when: 'The update action is executed with a wrong locale'
            def id = 16
            String path = "${resourcePath}/$id"
            client.toBlocking().exchange(HttpRequest.PUT(path, invalidWrongLocaleJson), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.BAD_REQUEST
            e.response.body().code == 400
            e.response.body().message == "Property [locale] of class [class b2boost.assignment.partner.PartnerCommand] " +
                    "with value [en] does not pass custom validation (country is mandatory). "
            e.response.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test that the update action correctly validate a wrong date'() {
        when: 'The update action is executed with a wrong date'
            def id = 16
            String path = "${resourcePath}/$id"
            client.toBlocking().exchange(HttpRequest.PUT(path, invalidWrongDateJson), Map)

        then: 'The response is correct'
            def e = thrown(HttpClientResponseException)
            e.response.status == HttpStatus.BAD_REQUEST
            e.response.body().code == 400
            e.response.body().message == "Text 2017-10-03T12:18:46+0:00 could not be parsed at index 19 (typeMismatch). "
            e.response.headers.get("Content-Type")
                    .contains("application/json")
    }

    void 'Test that the update action correctly edit an instance'() {
        given: "entity in db is a generic"
            def id = 16
            partnerDataService.get(id).companyName == "company 16"

        when: 'The update action is executed with valid data'
            String path = "${resourcePath}/$id"
            HttpResponse<Map> response = client.toBlocking().exchange(HttpRequest.PUT(path, validJson), Map)

        then: 'The response is correct and in JSON'
            response.status == HttpStatus.OK
            response.body().id
            response.headers.nettyHeaders.headers.get("Content-Type")
                    .contains("application/json")
            response.body().name == "Bells & Whistles"
            response.body().reference == "xxxxxx"
            response.body().locale == "en_ES"
            response.body().expirationTime == "2017-10-03T09:18:46+00:00"
            partnerDataService.get(id).companyName == "Bells & Whistles"
    }

}
