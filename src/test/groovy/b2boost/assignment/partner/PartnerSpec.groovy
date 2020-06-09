package b2boost.assignment.partner

import grails.test.hibernate.HibernateSpec
import spock.lang.Shared
import spock.lang.Subject

import java.time.ZonedDateTime

/**
 * The HibernateSpec super class will wrap each test method in a
 * transaction that is rolled back at the end of the test ensuring cleanup between tests.
 */
class PartnerSpec extends HibernateSpec {

    @Subject
    @Shared
    PartnerDataService service

    void setupSpec() {
        service = hibernateDatastore.getService(PartnerDataService)
    }

    void "test domain class validation"() {
        when: 'A domain class is saved with invalid data'
            Partner partner = new Partner(
                    companyName: '',
                    ref: 'refX',
                    locale:Locale.UK,
                    expires: ZonedDateTime.now())
            partner.save()

        then: 'There were errors and the data was not saved'
            partner.hasErrors()
            partner.errors.getFieldError('companyName')
            Partner.count() == 0
    }

}
