package b2boost.assignment.partner

import org.springframework.http.HttpStatus

import java.time.Instant

class PartnerController {

    def partnerService

    def index(Integer from, Integer size) {
        for (int i = 1; i < 2; i++) {
            Date date = Date.from(Instant.now())
            new Partner(
                    companyName:    'company X',
                    ref:            'xxxxx',
                    locale:         Locale.UK,
                    expires:        date).save()
        }
        respond partnerService.findAll([max: size, offset: from])
    }

    def show() {
        respond partnerService.find(params.id)
    }

    def save(PartnerCommand partnerCommand) {
        respond partnerService.save(partnerCommand), status: HttpStatus.CREATED
    }

    // todo
    def update(PartnerCommand partnerCommand) {
        ​Partner partner = partnerService.find(params.id)
        if (partner == null) {
            render status: HttpStatus.NOT_FOUND
            return
        }
        partner.properties = partnerEdit.properties
        respond partner.save
    }

    // todo
    def delete() {
        respond partnerService.delete(params.id)
    }

}
