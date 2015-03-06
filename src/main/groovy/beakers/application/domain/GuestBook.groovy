package beakers.application.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

//@Entity
@ToString
@EqualsAndHashCode
class GuestBook {

    String guestName
    String message

    static constraints = {
        guestName nullable: false, blank: false, maxSize: 50
        message nullable: false, blank: false, maxSize: 1024
    }
}
