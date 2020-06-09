package b2boost.assignment.partner

import grails.validation.Validateable

class Partner implements Validateable {

    String companyName
    String ref
    Locale locale
    Date expires

    static constraints = {
        companyName nullable: false, blank: false
        ref         nullable: false, blank: false
        locale      nullable: false
        expires     nullable: false
    }

}