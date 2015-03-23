package beakers.system

import beakers.system.config.ApplicationConfig
import beakers.system.config.BasePackagesConfiguration
import beakers.system.types.BeakersModule
import beakers.system.utils.config.GroovyConfigResource
import groovy.util.logging.Log4j
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.web.BasicErrorController
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import

import java.lang.reflect.Field
import java.nio.charset.Charset

@Log4j
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = [
        BasicErrorController,
        LiquibaseAutoConfiguration,
        ErrorMvcAutoConfiguration,
])
@Import(BasePackagesConfiguration)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BeakersCore extends BeakersModule {

    static Collection<Class<? extends BeakersModule>> activeModules
    static applicationContext
    private static params = []

    static {
        // force default encoding
        if (System.getProperty('file.encoding')?.toUpperCase() != "UTF-8") {
            System.setProperty 'file.encoding', 'UTF-8'
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        }
    }

    @Override
    List<String> getMessagesPaths() {
        return ["classpath:i18n/messages"]
    }

    @Override
    GroovyConfigResource getConfigResource() {
        return new GroovyConfigResource(ApplicationConfig)
    }

    private static Collection<Class<? extends BeakersModule>> resolveDependencies(
            Collection<Class<? extends BeakersModule>> modules, LinkedHashSet<Class> result = null) {
        if (result == null) {
            result = new LinkedHashSet<>([BeakersCore])
        }
        def deps = modules.collect { it?.newInstance()?.dependencies ?: [] }.flatten().unique()
        if (!deps.isEmpty()) {
            resolveDependencies(deps, result)
        }
        result.addAll(modules)
        return result
    }

    /**
     * @param args First argument is active profile and other are params used by configuration
     */
    public static void launch(Collection<Class<? extends BeakersModule>> appClasses, String[] args) throws Exception {
        activeModules = resolveDependencies(appClasses)
        SpringApplication app = new SpringApplication(activeModules as Object[]) {
            @Override
            protected void load(ApplicationContext context, Object[] sources) {
                BeakersCore.log.info("Active modules: ${activeModules*.simpleName.join(", ")}")
                super.load(context, sources)
            }
        }
        if (args.length >= 1) {
            app.setAdditionalProfiles(args[0])
            for (int i = 1; i < args.length; i++) {
                params << args[i]
            }
        }
        app.run(args);
    }

    public static void launch(Class appClass, String[] args) throws Exception {
        launch([appClass], args)
    }

    public static void main(String[] args) throws Exception {
        launch(BeakersCore, args)
    }

    public static List getParams() {
        return new ArrayList(params)
    }

    /**
     * Value resolver
     * @param value value() of @Value annotation
     * @return resolved value
     */
    public static String resolveValue(String value) {
        ((ConfigurableBeanFactory) applicationContext.autowireCapableBeanFactory).resolveEmbeddedValue(value)
    }


}
