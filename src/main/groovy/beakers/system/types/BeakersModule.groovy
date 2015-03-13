package beakers.system.types

import beakers.system.config.GroovyConfigResource

abstract class BeakersModule {

    GroovyConfigResource getConfigResource() {
        return null
    }

    Class<? extends BeakersModule>[] getDependencies() {
        return []
    }

}
