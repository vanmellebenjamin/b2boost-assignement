package b2boost.assignment.partner

import grails.gorm.services.Service;
import groovy.transform.CompileStatic;

@CompileStatic
@Service(Partner)
interface PartnerDataService {

    Partner get(Serializable id)

    List<Partner> list(Map args)

    void delete(Serializable id)

    Partner save(Partner booking)

}
