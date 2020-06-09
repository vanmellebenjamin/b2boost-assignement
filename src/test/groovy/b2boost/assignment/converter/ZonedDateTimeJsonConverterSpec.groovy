package b2boost.assignment.converter

import grails.testing.spock.OnceBefore
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeJsonConverterSpec extends Specification {

    @Shared
    ZonedDateTimeJsonConverter zonedDateTimeJsonConverter

    @OnceBefore
    void init() {
        zonedDateTimeJsonConverter = new ZonedDateTimeJsonConverter()
    }

    void "test to convert an UTC date"() {
        given: "the date is UTC"
            ZonedDateTime zdt = ZonedDateTime.of(
                    LocalDate.of (2020 , 07 , 15 ) ,
                    LocalTime.of ( 11 , 30 ) ,
                    ZoneId.of("UTC"))

        when: "convert the date using the converter"
            String date = zonedDateTimeJsonConverter.convert(zdt, null)

        then: "the format is correct"
            date == "2020-07-15T11:30:00+00:00"
    }

    void "test to convert a Brisbane zoned date"() {
        given: "the date is Brisbane time"
            ZonedDateTime zdt = ZonedDateTime.of(
                    LocalDate.of (2020 , 07 , 15 ) ,
                    LocalTime.of ( 21 , 30 ) ,
                    ZoneId.of("Australia/Brisbane"))

        when: "convert the date using the converter"
            String date = zonedDateTimeJsonConverter.convert(zdt, null)

        then: "the format is correct"
            date == "2020-07-15T11:30:00+00:00"
    }

}
