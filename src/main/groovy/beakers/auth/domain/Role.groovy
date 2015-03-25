package beakers.auth.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString(excludes = ["groups"])
@EqualsAndHashCode(excludes = ["groups"])
class Role {

    String name

    static belongsTo = RoleGroup
    static hasMany = [groups: RoleGroup]

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}
