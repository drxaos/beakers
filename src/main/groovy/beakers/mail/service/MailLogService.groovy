package beakers.mail.service

import beakers.mail.domain.MailLog
import beakers.system.errors.ServiceException
import beakers.system.aop.OnUpdateDatabase
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
class MailLogService {

    @Transactional
    @OnUpdateDatabase
    public save(Date date, String toEmail, String subject, String text, String sender, String view) {
        MailLog ml = new MailLog(
                date: date,
                toEmail: toEmail,
                subject: subject,
                text: text,
                sender: sender,
                view: view
        )

        if (!ml.validate() || !ml.save(flush: true)) {
            throw new ServiceException("can't create mail log: ${ml}")
        }
    }

}
