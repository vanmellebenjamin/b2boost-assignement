package b2boost.assignment.partner

import b2boost.assignment.partner.exception.PartnerNotFoundException
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class PartnerController implements PartnerExceptionHandler {
    static responseFormats = ['json']

    def partnerService

    def index(Integer from, Integer size) {
        respond partnerService.findAll([max: size, offset: from])
    }

    def show() {
        respond partnerService.find(params.id)
    }

    def save(PartnerCommand partnerCommand) {
        try {
            respond partnerService.save(partnerCommand), status: HttpStatus.CREATED
        } catch (ValidationException validationException) {
            handleValidationException(validationException)
        } catch (PartnerNotFoundException partnerNotFoundException) {
            handlePartnerNotFoundException(partnerNotFoundException)
        }
    }

    def update(PartnerCommand partnerCommand) {
        try {
            respond partnerService.update(params.id, partnerCommand), status: HttpStatus.OK
        } catch (ValidationException validationException) {
            handleValidationException(validationException)
        } catch (PartnerNotFoundException partnerNotFoundException) {
            handlePartnerNotFoundException(partnerNotFoundException)
        }
    }

    def delete() {
        try {
            partnerService.delete(params.id)
            respond status: HttpStatus.OK
        } catch (PartnerNotFoundException partnerNotFoundException) {
            handlePartnerNotFoundException(partnerNotFoundException)
        }
    }

}
