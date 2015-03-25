package beakers.auth.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString(excludes = ["users"])
@EqualsAndHashCode(excludes = ["users"])
class RoleGroup {

    String name

    static hasMany = [users: User, roles: Role]

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}
