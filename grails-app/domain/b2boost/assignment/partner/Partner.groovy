package b2boost.assignment.partner

import grails.validation.Validateable

import java.time.ZonedDateTime

class Partner implements Validateable {

    String companyName
    String ref
    Locale locale
    ZonedDateTime expires

    static constraints = {
        companyName nullable: false, blank: false
        ref         nullable: false, blank: false
        locale      nullable: false
        expires     nullable: false
    }

}