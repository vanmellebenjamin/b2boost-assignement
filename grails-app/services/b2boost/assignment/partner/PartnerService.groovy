package b2boost.assignment.partner

import b2boost.assignment.partner.exception.PartnerNotFoundException
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
class PartnerService {

    PartnerDataService partnerDataService

    @Transactional
    Partner save(PartnerCommand partnerCommand) {
        if(partnerCommand.hasErrors()) {
            throw new ValidationException("Validation Error", partnerCommand.getErrors())
        }
        partnerDataService.save(partnerCommand as Partner)
    }

    @Transactional
    delete(Serializable id) {
        Partner partner = partnerDataService.get(id)
        if (partner == null) {
            throw new PartnerNotFoundException(id);
        }
        partnerDataService.delete(id)
    }

    @Transactional
    Partner update(Serializable id, PartnerCommand partnerCommand) {
        partnerCommand.validate()
        Partner partner = partnerDataService.get(id)
        // todo copy fields
        partner.with {
            companyName: partnerCommand.name
            ref: partnerCommand.reference
            locale: partnerCommand.locale
            expires: partnerCommand.expirationTime
        }
        partnerDataService.save(partner)
    }

    @Transactional(readOnly = true)
    Partner find(Serializable id) {
        Partner partner = partnerDataService.get(id)
        if (partner == null) {
            throw new PartnerNotFoundException(id);
        }
        partner
    }

    @Transactional(readOnly = true)
    List<Partner> findAll(Map queryParams) {
        queryParams.max = queryParams.max ? queryParams.max : 10
        partnerDataService.list(queryParams)
    }

}

