package b2boost.assignment.partner

import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import org.springframework.validation.Errors
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import static javax.servlet.http.HttpServletResponse.SC_CREATED

class PartnerControllerSpec extends Specification implements ControllerUnitTest<PartnerController> {

    def "test BookingController.save accepts POST requests"() {
        given: 'partner service works fine'
        controller.partnerDataService = Mock(PartnerDataService)

        when: 'save is called'
        request.method = 'POST'
        controller.save(new PartnerCommand())

        then: 'response status is created'
        response.status == SC_CREATED
    }

    def "test BookingController.save return bad request if Validation exception occurs"() {
        given: 'partner service will throw an exception'
        controller.partnerDataService = Mock(PartnerDataService)
        controller.partnerDataService.save(_ as Partner) >> {
            Errors errors = Mock(Errors)
            errors.getAllErrors() >> []
            throw new ValidationException("error", errors)
        }

        when: 'save is called'
        request.method = 'POST'
        controller.save(new PartnerCommand())

        then: 'response status is BAD request'
        response.status == SC_BAD_REQUEST
    }

    // Todo implement tests for each action

}
