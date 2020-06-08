package b2boost.assignment.partner


import grails.gorm.transactions.Transactional

@Transactional
class PartnerDataService {

    Partner save(PartnerCO partnerInfo) {
        new Partner(
            companyName: partnerInfo.name,
            ref: partnerInfo.reference,
            locale: partnerInfo.locale,
            expires: partnerInfo.expirationTime).save()
    }

    @Transactional(readOnly = true)
    Partner find(Serializable id) {
        Partner.get(id)
    }

    @Transactional(readOnly = true)
    Partner[] findAll(Map queryParams) {
        Partner.findAll(queryParams)
    }

}

