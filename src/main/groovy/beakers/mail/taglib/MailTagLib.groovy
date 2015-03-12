package beakers.mail.taglib

import grails.gsp.TagLib
import org.codehaus.groovy.grails.web.pages.TagLibraryLookup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TagLib
@Component
class MailTagLib {
    static namespace = 'mail'

    @Autowired
    TagLibraryLookup tagLibraryLookup

    def subject = { attrs, body ->
        Binding binding = this.pageScope
        while (!binding.isRoot() && binding.getParent() != null) {
            binding = binding.getParent()
        }
        binding.setVariable("MAIL_SUBJECT", body())
    }

    def mail = { attrs, body ->

        // subject
        Binding binding = this.pageScope
        while (!binding.isRoot() && binding.getParent() != null) {
            binding = binding.getParent()
        }
        binding.setVariable("MAIL_SUBJECT", attrs.remove("subject"))

        // layout
        //instanceTagLibraryApi.tagLibraryLookup = tagLibraryLookup
        attrs.put("name", "../mail/layouts/" + attrs.remove("layout"))
        g.applyLayout(attrs, body)
    }
}
