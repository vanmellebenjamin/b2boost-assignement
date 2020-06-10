package b2boost.assignment.partner

import b2boost.assignment.partner.exception.PartnerNotFoundException
import grails.validation.ValidationException
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpStatus

import java.text.MessageFormat

trait PartnerExceptionHandler {

    def handleValidationException(ValidationException validationException) {
        // This is a POC, that's not a good design !
        // Todo nice GSON template to make a nice hashmap with errors. vnd.error ?
        StringBuilder sb = new StringBuilder()
        for (DefaultMessageSourceResolvable resolvable : validationException?.errors?.allErrors) {
            sb.append(MessageFormat.format(resolvable.defaultMessage, resolvable.getArguments()))
            sb.append(" (" + resolvable.code + "). ")
        }
        render view: "/error",
                status: HttpStatus.BAD_REQUEST,
                model: [code   : HttpStatus.BAD_REQUEST.value(),
                        exceptionMessage: sb.toString()]
    }

    def handlePartnerNotFoundException(PartnerNotFoundException partnerNotFoundException) {
        render view: "/error",
                status: HttpStatus.NOT_FOUND,
                model: [code   : HttpStatus.NOT_FOUND.value(),
                        exceptionMessage: partnerNotFoundException.getMessage()]
    }

}
