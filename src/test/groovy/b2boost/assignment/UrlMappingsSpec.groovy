package b2boost.assignment

import b2boost.assignment.partner.PartnerController
import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class UrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(PartnerController)
    }

    void "test partner index REST mapping"() {
        when: "calling GET /api/partners"
        assertUrlMapping("/api/partners", controller: 'partner', action: 'index', method: 'GET')

        then: "no exception is thrown"
        noExceptionThrown()
    }

    void "test partner get REST mapping"() {
        when: "calling GET /api/partners/1"
        assertUrlMapping("/api/partners/1", controller: 'partner', action: 'show', method: 'GET') {
            id = '1'
        }

        then: "no exception is thrown"
        noExceptionThrown()
    }

    void "test partner save REST mapping"() {
        when: "calling POST /api/partners"
        assertReverseUrlMapping("/api/partners", controller: 'partner', action: 'save', method: 'POST')

        then: "no exception is thrown"
        noExceptionThrown()
    }

    void "test partner edit REST mapping"() {
        when: "calling PUT /api/partners/1"
        assertReverseUrlMapping("/api/partners/1", controller: 'partner', action: 'update', method: 'PUT') {
            id = '1'
        }

        then: "no exception is thrown"
        noExceptionThrown()
    }

    void "test partner delete REST mapping"() {
        when: "calling DELETE /api/partners/1"
        assertReverseUrlMapping("/api/partners/1", controller: 'partner', action: 'delete', method: 'DELETE') {
            id = '1'
        }

        then: "no exception is thrown"
        noExceptionThrown()
    }
    
}

