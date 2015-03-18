package beakers.system.config

import beakers.system.BeakersCore
import grails.util.Holders
import groovy.util.logging.Log4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils
import org.springframework.util.ObjectUtils
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.socket.server.standard.ServerEndpointExporter

import javax.websocket.server.ServerEndpointConfig
import java.util.concurrent.ConcurrentHashMap

/**
 * Websocket configuration
 */
@Configuration
public class WebsocketConfiguration {


    @Bean
    public ServerEndpointExporter endpointExporter() {
        return new ServerEndpointExporter();
    }

    @Log4j
    public static class SpringBootConfigurator extends ServerEndpointConfig.Configurator {

        private static final String NO_VALUE = ObjectUtils.identityToString(new Object());

        private static final Map<String, Map<Class<?>, String>> cache =
                new ConcurrentHashMap<String, Map<Class<?>, String>>();

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
            def wac = BeakersCore.applicationContext
            if (wac == null) {
                String message = "Failed to find the root WebApplicationContext. Was ContextLoaderListener not used?";
                log.error(message);
                throw new IllegalStateException(message);
            }

            String beanName = ClassUtils.getShortNameAsProperty(endpointClass);
            if (wac.containsBean(beanName)) {
                T endpoint = wac.getBean(beanName, endpointClass);
                if (log.isTraceEnabled()) {
                    log.trace("Using @ServerEndpoint singleton " + endpoint);
                }
                return endpoint;
            }

            Component annot = AnnotationUtils.findAnnotation(endpointClass, Component.class);
            if ((annot != null) && wac.containsBean(annot.value())) {
                T endpoint = wac.getBean(annot.value(), endpointClass);
                if (log.isTraceEnabled()) {
                    log.trace("Using @ServerEndpoint singleton " + endpoint);
                }
                return endpoint;
            }

            beanName = getBeanNameByType(wac, endpointClass);
            if (beanName != null) {
                return (T) wac.getBean(beanName);
            }

            if (log.isTraceEnabled()) {
                log.trace("Creating new @ServerEndpoint instance of type " + endpointClass);
            }
            return wac.getAutowireCapableBeanFactory().createBean(endpointClass);
        }

        private String getBeanNameByType(def wac, Class<?> endpointClass) {
            String wacId = wac.getId();

            Map<Class<?>, String> beanNamesByType = cache.get(wacId);
            if (beanNamesByType == null) {
                beanNamesByType = new ConcurrentHashMap<Class<?>, String>();
                cache.put(wacId, beanNamesByType);
            }

            if (!beanNamesByType.containsKey(endpointClass)) {
                String[] names = wac.getBeanNamesForType(endpointClass);
                if (names.length == 1) {
                    beanNamesByType.put(endpointClass, names[0]);
                } else {
                    beanNamesByType.put(endpointClass, NO_VALUE);
                    if (names.length > 1) {
                        throw new IllegalStateException("Found multiple @ServerEndpoint's of type [" +
                                endpointClass.getName() + "]: bean names " + Arrays.asList(names));
                    }
                }
            }

            String beanName = beanNamesByType.get(endpointClass);
            return (NO_VALUE.equals(beanName) ? null : beanName);
        }
    }

}