package b2boost.assignment.partner
import org.springframework.http.HttpStatus

class PartnerController {

    def partnerDataService

    def index(Integer from, Integer max) {
        log.info "index"
        // params.max = Math.min(max ?: 10, 100)
        respond partnerDataService.findAll([max: max, offset: from])
    }

    def show() {
        Partner partner = partnerDataService.find(params.id)
        if (partner == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        respond partner
    }

    def save(PartnerCO partnerInfo) {
        log.info "save"
        if (partnerInfo == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }

        if (partnerInfo.hasErrors()) {
            respond partnerInfo.errors
            return
        }
        Partner partner = partnerDataService.save(partnerInfo)
        respond partner, status: HttpStatus.CREATED
    }

    def update(PartnerCO partnerInfo) {
        log.info "update"
        if (partnerInfo == null) {
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
        Partner partner = partnerDataService.find(params.id)
        if (partner == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        respond partner.delete()
    }

}
