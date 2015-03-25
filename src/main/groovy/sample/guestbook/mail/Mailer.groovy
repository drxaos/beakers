package sample.guestbook.mail

import beakers.system.events.EventListener
import beakers.auth.events.SignUpEvent
import beakers.mail.service.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Mailer {

    @Autowired
    MailService mailService

    void send(String to, String view, Map model) {
        mailService.send(to, view, model)
    }

    void send(String from, String to, String view, Map model) {
        mailService.send(from, to, view, model)
    }

    @EventListener
    void onSignUp(SignUpEvent e) {
        mailService.send(e.user.email, "/mail/auth/onSignUp", [
                username: e.user.username,
                password: e.user.password,
                fullName: e.user.fullName,
        ])

    }
}
