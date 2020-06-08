package b2boost.assignment.partner

import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class PartnerController {

    def partnerDataService

    def index(Integer from, Integer max) {
        log.info "index"
        // params.max = Math.min(max ?: 10, 100)
        respond partnerDataService.findAll([max: max, offset: from])
    }

    def show() {
        log.info "show"
        partner = partnerDataService.find(params.id)
        if (partner == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        respond partner
    }

    def save(PartnerCommand partnerCommand) {
        log.info "save"
        try {
            partner = partnerDataService.save(partnerCommand as Partner)
            respond partner, status: HttpStatus.CREATED
        } catch (ValidationException e) {
            respond e, status: HttpStatus.BAD_REQUEST
        }
    }

    def update(PartnerCommand partnerCommand) {
        log.info "update"
        if (partnerCommand == null) {
            render status: HttpStatus.BAD_REQUEST
            return
        }
        ​Partner partner = partnerDataService.find(params.id)
        if (partner == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        partner.properties = partnerEdit.properties
        respond partner.save
    }

    def delete() {
        log.info "delete"
        Partner partner = partnerDataService.find(params.id)
        if (partner == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        respond partner.delete()
    }

}
