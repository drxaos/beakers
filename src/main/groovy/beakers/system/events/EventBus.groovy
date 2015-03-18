package beakers.system.events

import groovy.util.logging.Log4j
import org.codehaus.groovy.grails.plugins.support.BeanPostProcessorAdapter
import org.springframework.aop.support.AopUtils
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils

import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

@Log4j
@Component
class EventBus extends BeanPostProcessorAdapter {
    @Autowired
    BusEndpoint busEndpoint

    private final Set<Class<?>> nonAnnotatedClasses =
            Collections.newSetFromMap(new ConcurrentHashMap<Class<?>, Boolean>(64));

    private Map<Class<? extends Event>, List<Closure>> listeners = [:]

    public <T extends Event> void subscribe(Class<T> type, Closure<T> listener) {
        def list = listeners.get(type)
        if (list == null) {
            listeners.put(type, [listener])
        } else {
            list.add(listener)
        }
    }

    public void publish(Event event) {
        for (Class c : listeners.keySet()) {
            if (c.isAssignableFrom(event.class)) {
                for (Closure cl : listeners.get(c)) {
                    cl.call(event)
                }
            }
        }
        busEndpoint.broadcast(event)
    }

    @Override
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
            final Set<Method> annotatedMethods = new LinkedHashSet<Method>(1);
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                @Override
                public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                    for (EventListener scheduled :
                            AnnotationUtils.getRepeatableAnnotation(method, EventListeners.class, EventListener.class)) {
                        processEventListener(scheduled, method, bean);
                        annotatedMethods.add(method);
                    }
                }
            });
            if (annotatedMethods.isEmpty()) {
                this.nonAnnotatedClasses.add(bean.getClass());
                if (log.isDebugEnabled()) {
                    log.debug("No @EventListener annotations found on bean class: " + bean.getClass());
                }
            } else {
                // Non-empty set of methods
                if (log.isDebugEnabled()) {
                    log.debug(annotatedMethods.size() + " @EventListener methods processed on bean '" + beanName +
                            "': " + annotatedMethods);
                }
            }
        }
        return bean;
    }

    def templateClosure = { Object bean, Method m, Event e -> m.invoke(bean, e) }

    protected void processEventListener(EventListener scheduled, Method method, Object bean) {
        if (method.parameterTypes.length != 1 || !Event.class.isAssignableFrom(method.parameterTypes[0])) {
            throw new IllegalArgumentException("EventListener must accept event as single argument")
        }
        Class<Event> c = method.parameterTypes[0]
        subscribe(c, templateClosure.curry(bean, method))
    }
}
