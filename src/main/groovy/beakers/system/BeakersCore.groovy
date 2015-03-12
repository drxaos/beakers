package beakers.system

import beakers.system.config.ApplicationConfig
import beakers.system.config.GroovyConfigResource
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.web.BasicErrorController
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

import java.lang.reflect.Field
import java.nio.charset.Charset

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = [BasicErrorController, LiquibaseAutoConfiguration, ErrorMvcAutoConfiguration])
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BeakersCore {

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

    @Bean
    GroovyConfigResource beakersCoreConfig() {
        return new GroovyConfigResource(ApplicationConfig)
    }

    /**
     * @param args First argument is active profile and other are params used by configuration
     */
    public static void launch(List<Class> appClasses, String[] args) throws Exception {
        SpringApplication app = new SpringApplication((appClasses + [BeakersCore]) as Object[]);
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
