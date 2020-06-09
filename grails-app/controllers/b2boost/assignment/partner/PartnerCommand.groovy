package b2boost.assignment.partner

import grails.databinding.BindingFormat
import grails.validation.Validateable
import groovy.transform.CompileDynamic

class PartnerCommand implements Validateable {
    String name
    String reference
    Locale locale
    @BindingFormat("yyyy-MM-dd'T'HH:mm:ssX")
    Date expirationTime


    Object asType(Class clazz) {
        if (clazz == Partner) {
            Partner partner = new Partner()
            copyProperties(this, partner)
            return partner
        }
        super.asType(clazz)
    }

    @SuppressWarnings('MethodParameterTypeRequired')
    @CompileDynamic
    def copyProperties(source, target) {
        target.companyName = source.name
        target.ref = source.reference
        target.locale = source.locale
        target.expires = source.expirationTime
    }


    static constraints = {
        // importFrom Partner, it seems to be a good idea, but fields does not match
        name            nullable: false, blank: false
        reference       nullable: false, blank: false
        locale          nullable: false
        expirationTime  nullable: false
        locale validator: { loc, pc ->
            if (loc.language == null || loc.language == "")  return ['language is mandatory']
            if (loc.country == null || loc.country == "")  return ['country is mandatory']
        }
    }

}