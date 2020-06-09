package b2boost.assignment

import b2boost.assignment.partner.exception.PartnerNotFoundException
import grails.validation.ValidationException

class UrlMappings {

    static mappings = {
        /**
         * UrlMappings syntax '/api/partners'(resources: 'partner') automatically generates
         * the associated RESTful mappings (/!\ and only the mappings)
         */
        group "/api", {
            '/partners'(resources: 'partner', includes: ['index', 'show', 'save', 'update', 'delete'])
        }

        '500'(  controller: 'error',
                action: "validationException",
                exception: ValidationException)
        '500'(  controller: 'error',
                action: "partnerNotFoundException",
                exception: PartnerNotFoundException)
        '500'(controller: 'error')
        '404'(view: '/notFound')
    }

}
