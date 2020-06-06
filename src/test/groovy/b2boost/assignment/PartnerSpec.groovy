package b2boost.assignment

import grails.test.hibernate.HibernateSpec

import java.time.Instant


/**
 * The HibernateSpec super class will wrap each test method in a
 * transaction that is rolled back at the end of the test ensuring cleanup between tests.
 */
class PartnerSpec extends HibernateSpec {

    void "test domain class validation"() {
        when: 'A domain class is saved with invalid data'
        Partner partner = new Partner(id: 1, companyName: '', ref: 'refX', locale: Locale.FRENCH, expires: Date.from(Instant.now()))
        partner.save()

        then: 'There were errors and the data was not saved'
        partner.hasErrors()
        partner.errors.getFieldError('companyName')
        Partner.count() == 0
    }
}
