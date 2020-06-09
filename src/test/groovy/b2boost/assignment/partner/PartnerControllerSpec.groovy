package b2boost.assignment.partner

import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import org.springframework.validation.Errors
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import static javax.servlet.http.HttpServletResponse.SC_CREATED

class PartnerControllerSpec extends Specification implements ControllerUnitTest<PartnerController> {

    def "test PartnerController.save accepts POST requests"() {
        given: 'partner service works fine'
            controller.partnerService = Mock(PartnerService)

        when: 'save is called'
            request.method = 'POST'
            controller.save(new PartnerCommand())

        then: 'response status is created'
            response.status == SC_CREATED
    }

    // Todo implement tests for each action

}
