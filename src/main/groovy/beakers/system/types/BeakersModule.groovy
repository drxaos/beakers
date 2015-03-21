package beakers.system.types

import beakers.system.utils.config.GroovyConfigResource

abstract class BeakersModule {

    GroovyConfigResource getConfigResource() {
        return null
    }

    List<String> getMessagesPaths() {
        return null
    }

    Class<? extends BeakersModule>[] getDependencies() {
        return []
    }

}
