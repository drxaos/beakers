package beakers.system.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class System {

    String parameter
    String value

    static constraints = {
        parameter nullable: false, blank: false
        value nullable: true
    }
}
