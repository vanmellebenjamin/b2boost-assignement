package b2boost.assignment

class Partner {
    Long id
    String companyName
    String ref
    Locale locale
    Date expires

    static constraints = {
        companyName blank:false
    }
}
