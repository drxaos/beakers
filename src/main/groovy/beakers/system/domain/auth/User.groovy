package beakers.system.domain.auth

import beakers.system.utils.DateUtils
import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class User {

    Date created = DateUtils.now()

    String username
    String password

    String fullName
    String email

    RoleGroup roleGroup

    static constraints = {
        username nullable: false, blank: false, unique: true
        password nullable: false, blank: false

        fullName nullable: false, blank: false
        email nullable: false, blank: false, unique: true
        roleGroup nullable: false
    }
}