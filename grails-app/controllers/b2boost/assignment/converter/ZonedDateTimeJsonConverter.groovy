package b2boost.assignment.converter

import grails.plugin.json.builder.JsonGenerator

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter;

class ZonedDateTimeJsonConverter implements JsonGenerator.Converter {

    // Since Java8 that's a thread safe object
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    @Override
    boolean handles(Class<?> type) {
        type == ZonedDateTime.class
    }

    @Override
    String convert(Object value, String key) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(((ZonedDateTime) value).toInstant(), ZoneId.of("UTC"));
        // todo Not able to get +00:00, Z etc... give Z or +0000
        zdt.format(dateFormat) + "+00:00"
    }
}
