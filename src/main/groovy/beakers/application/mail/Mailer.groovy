package beakers.application.mail

import beakers.system.mail.MailService
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

    void onSignUp(String email, String username, String password, String fullName) {
        mailService.send(email, "/mail/login/onSignUp", [
                username: username,
                password: password,
                fullName: fullName,
        ])

    }
}
