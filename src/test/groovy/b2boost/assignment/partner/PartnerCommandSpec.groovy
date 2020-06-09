package b2boost.assignment.partner

import spock.lang.Specification

import java.time.Instant

class PartnerCommandSpec extends Specification {

    def "test to convert command to domain object"() {
        given: 'partnerCommand'
            PartnerCommand partnerCommand = new PartnerCommand(
                    name: 'Bells & Whistles',
                    reference: 'xxxxxx',
                    locale: Locale.UK,
                    expirationTime: Date.from(Instant.now()))

        when: 'partnerCommand is converted to partner'
            Partner partner = partnerCommand as Partner

        then: 'properties are well converted'
            partner.properties.size() == 4
            partnerCommand.properties.size() - 3 == partner.properties.size()
            partnerCommand.name == partner.companyName
            partnerCommand.reference == partner.ref
            partnerCommand.locale == partner.locale
            partnerCommand.expirationTime == partner.expires
    }

}
