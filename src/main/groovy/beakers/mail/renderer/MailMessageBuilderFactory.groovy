/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package beakers.mail.renderer

import beakers.mail.service.MailLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailSender
import org.springframework.stereotype.Component

/**
 * Responsible for creating builder instances, which have dependencies and
 * are not threadsafe.
 */
@Component
class MailMessageBuilderFactory {

    @Autowired
    MailSender mailSender

    @Autowired
    MailMessageContentRenderer mailMessageContentRenderer

    @Autowired
    MailLogService mailLogService

    @Value('${mail.from:root@localhost}')
    String defaultFrom


    MailMessageBuilder createBuilder() {
        def b = new MailMessageBuilder(mailLogService, mailSender, mailMessageContentRenderer, [defaultFrom: defaultFrom])
        b.multipart(true)
        return b
    }
}
