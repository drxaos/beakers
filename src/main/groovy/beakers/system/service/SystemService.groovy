package beakers.system.service

import beakers.system.domain.System
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service

@Log4j
@Service
class SystemService {

    String parameter(String name, String defaultValue = null) {
        return System.findByParameter(name)?.value ?: defaultValue
    }

}
