package b2boost.assignment

import javax.transaction.Transactional

@Transactional
class PartnerDataService {

    Partner save(PartnerInfo partnerInfo) {
        return new Partner(
                companyName: partnerInfo.name,
                ref: partnerInfo.reference,
                locale: partnerInfo.locale,
                expires: partnerInfo.expirationTime).save()
    }

    Partner get(Serializable id) {
        return Partner.get(id)
    }

}

