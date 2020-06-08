package b2boost.assignment.converter

import grails.testing.spock.OnceBefore
import spock.lang.Shared
import spock.lang.Specification

class LocaleJsonConverterTest extends Specification {

    @Shared
    LocaleJsonConverter localeJsonConverter

    @OnceBefore
    void init() {
        localeJsonConverter = new LocaleJsonConverter()
    }

    void "test to convert a locale that contains language only"() {
        when: "calling GET /api/partners"
        String locale = localeJsonConverter.convert(new Locale("en", ""), null)

        then: "the format is correct"
        locale == "en"
    }

    void "test to convert a locale that contains language and country"() {
        when: "calling GET /api/partners"
        String locale = localeJsonConverter.convert(new Locale("en", "GB"), null)

        then: "the format is correct"
        locale == "en_GB"
    }

}
