package b2boost.assignment

class UrlMappings {

    static mappings = {
        /**
         * UrlMappings syntax '/api/partners'(resources: 'partner') automatically generates
         * the associated RESTful mappings (/!\ and only the mappings)
         */
        group "/api", {
            '/partners'(resources: 'partner', includes: ['index', 'show', 'save', 'update', 'delete'])
        }

        '500'(controller: 'error')
        '404'(view: '/notFound')
    }

}
