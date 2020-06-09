package b2boost.assignment.partner.exception

class PartnerNotFoundException extends RuntimeException {

    Serializable id

    PartnerNotFoundException(Serializable id) {
        super("Partner with id $id not found.")
        this.id = id
    }

}
