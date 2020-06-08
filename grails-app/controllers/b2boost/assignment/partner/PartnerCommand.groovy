package b2boost.assignment.partner

import grails.compiler.GrailsCompileStatic
import grails.databinding.BindingFormat
import grails.validation.Validateable
import groovy.transform.CompileDynamic

@GrailsCompileStatic
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

}