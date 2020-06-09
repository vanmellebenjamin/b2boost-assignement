package b2boost.assignment.partner

import grails.gorm.services.Service;
import groovy.transform.CompileStatic;

@CompileStatic
@Service(Partner)
interface PartnerDataService {

    Partner get(Serializable id)

    /**
     * Todo Follow up open issue https://github.com/grails/gorm-hibernate5/issues/55
     * HHH90000022: Hibernate's legacy org.hibernate.Criteria API is deprecated;
     * use the JPA javax.persistence.criteria.CriteriaQuery instead
     */
    List<Partner> list(Map args)



    void delete(Serializable id)

    Partner save(Partner partner)

}
