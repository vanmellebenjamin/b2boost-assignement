package b2boost.assignment

import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class UrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(PartnerController)
    }

    void "test partner mappings"() {
        expect: "calls to GET /api/partners to succeed"
        verifyUrlMapping("/api/partners", controller: 'partner', action: 'index', method: 'GET')
        verifyUrlMapping("/api/partners/1", controller: 'partner', action: 'show', method: 'GET') {
            id = '1'
        }
    }

}

