package beakers.taglib

import grails.gsp.TagLib
import org.springframework.stereotype.Component

@TagLib
@Component
class MailTagLib {
    static namespace = 'mail'

    def subject = { attrs, body ->
        Binding binding = this.pageScope
        while (!binding.isRoot() && binding.getParent() != null) {
            binding = binding.getParent()
        }
        binding.setVariable("MAIL_SUBJECT", body())
    }
}
