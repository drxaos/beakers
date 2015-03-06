package beakers.domain.system

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class Role {

    String name

    static belongsTo = RoleGroup
    static hasMany = [groups: RoleGroup]

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}
