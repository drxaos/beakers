package beakers.domain.system

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class RoleGroup {

    String name

    static hasMany = [users: User, roles: Role]

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}
