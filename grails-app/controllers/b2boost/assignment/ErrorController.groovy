package b2boost.assignment


import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpStatus

import java.text.MessageFormat

class ErrorController {

    def index() {
        try {
            render view: "error",
                    status: HttpStatus.INTERNAL_SERVER_ERROR,
                    model: [code   : HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            exceptionMessage: unwrapErrorMessage(request.exception)]
        } catch (RuntimeException e) {
            // Last chance fallback
            log.error "Error: ${e.message}", e
            render view: "error",
                    status: HttpStatus.INTERNAL_SERVER_ERROR,
                    model: [code   : HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            exceptionMessage: "Unexpected Exception"]
        }
    }

    def validationException() {
        // This is a POC, that's not a good design !
        // Todo nice GSON template to make a nice hashmap with errors. vnd.error ?
        StringBuilder sb = new StringBuilder()
        for (DefaultMessageSourceResolvable resolvable : request.exception.cause.errors.allErrors) {
            sb.append(MessageFormat.format(resolvable.defaultMessage, resolvable.getArguments()))
            sb.append(" (" + resolvable.code + "). ")
        }
        render view: "error",
                status: HttpStatus.BAD_REQUEST,
                model: [code   : HttpStatus.BAD_REQUEST.value(),
                        exceptionMessage: sb.toString()]
    }

    def partnerNotFoundException() {
        render view: "error",
                status: HttpStatus.NOT_FOUND,
                model: [code   : HttpStatus.NOT_FOUND.value(),
                        exceptionMessage: unwrapErrorMessage(request.exception)]
    }

    /**
     * That's a really strange unwrap method but behaviour
     * between Functional Test and "grails run-app" is different
     * since the stack is not the same
     *
     * @param throwable
     * @return the first message in the stack or "Undefined Error" if no message in the stack
     */
    private static String unwrapErrorMessage(Throwable throwable) {
        while (throwable != null && throwable.getMessage() == null) {
            throwable = throwable.getCause();
        }
        throwable != null ? throwable.getMessage() : "Undefined Error"
    }

}