package b2boost.assignment

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic //TODO check why intelliJ complains
class Partner {

    String companyName
    String ref
    Locale locale
    Date expires

}