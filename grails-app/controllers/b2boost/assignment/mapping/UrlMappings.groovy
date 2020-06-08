package b2boost.assignment.mapping

class UrlMappings {

    static mappings = {

        /**
         * UrlMappings syntax '/api/partners'(resources: 'partner') automatically generates
         * the associated RESTful mappings (and only the mappings)
         */
        group "/api", {
            '/partners'(resources: 'partner', includes: ['index', 'show', 'save', 'update', 'delete'])
        }

        '500'(view: '/error')
        '404'(view: '/notFound')
    }

}
