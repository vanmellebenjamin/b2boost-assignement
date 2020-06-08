package b2boost.assignment.partner

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
class PartnerService {

    PartnerDataService partnerDataService

    @Transactional
    Partner save(Partner partner) {
        partnerDataService.save(partner)
    }

    @Transactional
    delete(Serializable id) {
        partnerDataService.delete(id)
    }

    @Transactional
    Partner update(Partner partnerInstance) {
        Partner partner = partnerDataService.get(partnerInstance.id)
        partner.with {
            companyName: partnerInstance.companyName
            ref: partnerInstance.ref
            locale: partnerInstance.locale
            expires: partnerInstance.expires
        }
        partnerDataService.save(partner)
    }

    @Transactional(readOnly = true)
    Partner find(Serializable id) {
        partnerDataService.get(id)
    }

    @Transactional(readOnly = true)
    List<Partner> findAll(Map queryParams) {
        partnerDataService.list(queryParams)
    }

}

