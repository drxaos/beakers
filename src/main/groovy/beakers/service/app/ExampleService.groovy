package beakers.service.app

import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
class ExampleService {

    @Transactional
    def executeJob() {

    }
}
