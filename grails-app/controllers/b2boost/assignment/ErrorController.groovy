package b2boost.assignment
import org.springframework.http.HttpStatus

class ErrorController {
    static responseFormats = ['json']

    def index() {
        try {
            render view: "/error",
                    status: HttpStatus.INTERNAL_SERVER_ERROR,
                    model: [code   : HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            exceptionMessage: request.exception.getMessage()]
        } catch (RuntimeException e) {
            // Last chance fallback
            log.error "Error: ${e.message}", e
            render view: "/error",
                    status: HttpStatus.INTERNAL_SERVER_ERROR,
                    model: [code   : HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            exceptionMessage: "Unexpected Exception"]
        }
    }

}