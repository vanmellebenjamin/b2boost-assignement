package b2boost.assignment.command

import grails.databinding.BindingFormat
import grails.validation.Validateable

class PartnerInfo implements Validateable {
    String name
    String reference
    Locale locale
    @BindingFormat("yyyy-MM-dd'T'HH:mm:ssX")
    Date expirationTime

    static constraints = {

        name            nullable: false, blank: false
        reference       nullable: false, blank: false
        locale          nullable: false, blank: false, match: "[a-z]{2}_[A-Z]{2}"
        expirationTime  nullable: false
    }

}