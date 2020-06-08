package b2boost.assignment.partner

import grails.test.hibernate.HibernateSpec

import java.time.Instant

/**
 * The HibernateSpec super class will wrap each test method in a
 * transaction that is rolled back at the end of the test ensuring cleanup between tests.
 */
class PartnerSpec extends HibernateSpec {

    void "test domain class validation"() {
        when: 'A domain class is saved with invalid data'
        Partner partner = new Partner(
                companyName: '',
                ref: 'refX',
                locale:Locale.UK,
                expires: Date.from(Instant.now()))
        partner.save()

        then: 'There were errors and the data was not saved'
        partner.hasErrors()
        partner.errors.getFieldError('companyName')
        Partner.count() == 0
    }

    void "test domain class insertion"() {
        when: 'A domain class is saved with valid data'
        Partner partner = new Partner(
                companyName: 'Bells & Whistles',
                ref: 'xxxxxx',
                locale: Locale.UK,
                expires: Date.from(Instant.now()))
        partner.save()

        then: 'There was no error and the data was saved'
        !partner.hasErrors()
        Partner.count() == 1
    }

}
