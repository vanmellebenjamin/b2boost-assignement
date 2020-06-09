package b2boost.assignment.partner

import org.springframework.http.HttpStatus

class PartnerController {

    def partnerService

    def index(Integer from, Integer size) {
        respond partnerService.findAll([max: size, offset: from])
    }

    def show() {
        respond partnerService.find(params.id)
    }

    def save(PartnerCommand partnerCommand) {
        respond partnerService.save(partnerCommand), status: HttpStatus.CREATED
    }

    def update(PartnerCommand partnerCommand) {
        respond partnerService.update(params.id, partnerCommand), status: HttpStatus.OK
    }

    def delete() {
        partnerService.delete(params.id)
        respond status: HttpStatus.OK
    }

}
