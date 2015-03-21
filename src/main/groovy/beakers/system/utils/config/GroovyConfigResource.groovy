package beakers.system.utils.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource

class GroovyConfigResource {

    @Autowired
    ApplicationContext applicationContext

    def resource

    GroovyConfigResource(Resource resource) {
        this.resource = resource
    }

    GroovyConfigResource(Class config) {
        this.resource = config
    }

    GroovyConfigResource(String resourcePath) {
        this.resource = applicationContext.getResource(resourcePath)
    }
}
