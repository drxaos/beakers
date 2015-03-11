package beakers.system.utils.mail

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.subethamail.smtp.TooMuchDataException
import org.subethamail.wiser.Wiser

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Local stub for smtp server
 */
class SmtpStubServer extends Wiser {

    Wiser wiser

    List<MailMessageContent> mailMessages = []

    SmtpStubServer(int port) {
        wiser = new Wiser(port) {
            private final static Logger log = LoggerFactory.getLogger(SmtpStubServer.class);

            @Override
            void deliver(String from, String recipient, InputStream data) throws TooMuchDataException, IOException {
                super.deliver(from, recipient, data)
                messages.each {
                    def decoded = MailUtil.decodeMessage(it)
                    mailMessages << decoded
                    log.info("Mail delivered: ${decoded}")
                }
            }
        }
    }

    @PostConstruct
    def initialize() {
        wiser.start()
    }

    @PreDestroy
    def dispose() {
        wiser.stop()
    }

    def reset() {
        wiser.messages.clear()
        mailMessages.clear()
    }

    List<MailMessageContent> getAllMessages() {
        return mailMessages
    }

    List<MailMessageContent> from(String from) {
        return mailMessages.findAll { it.from == from }
    }

    List<MailMessageContent> to(String to) {
        return mailMessages.findAll { it.to == to }
    }

    List<MailMessageContent> fromTo(String from, String to) {
        return mailMessages.findAll { it.to == to && it.from == from }
    }

    List<MailMessageContent> subject(String subject) {
        return mailMessages.findAll { it.subject == subject }
    }

}
