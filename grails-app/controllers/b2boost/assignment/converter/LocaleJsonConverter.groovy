package b2boost.assignment.converter

import grails.plugin.json.builder.JsonGenerator;

class LocaleJsonConverter implements JsonGenerator.Converter {

    @Override
    boolean handles(Class<?> type) {
        type == Locale.class
    }

    @Override
    String convert(Object value, String key) {
        ((Locale) value).toString()
    }
}
