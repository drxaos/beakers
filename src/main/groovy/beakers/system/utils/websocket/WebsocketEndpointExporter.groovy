package beakers.system.utils.websocket

import beakers.auth.websocket.Endpoint
import beakers.system.config.WebsocketConfiguration
import beakers.system.websocket.WebsocketEndpoint
import org.apache.tomcat.websocket.pojo.PojoEndpointServer
import org.apache.tomcat.websocket.pojo.PojoMethodMapping
import org.springframework.context.ApplicationContext
import org.springframework.web.socket.server.standard.ServerEndpointExporter

import javax.websocket.DeploymentException
import javax.websocket.server.ServerEndpointConfig

class WebsocketEndpointExporter extends ServerEndpointExporter {
    private List<Class<?>> annotatedEndpointClasses;

    public void setAnnotatedEndpointClasses(Class<?>... annotatedEndpointClasses) {
        this.annotatedEndpointClasses = Arrays.asList(annotatedEndpointClasses);
    }

    @Override
    protected void registerEndpoints() {
        Set<Class<?>> endpointClasses = new LinkedHashSet<Class<?>>();
        if (this.annotatedEndpointClasses != null) {
            endpointClasses.addAll(this.annotatedEndpointClasses);
        }

        ApplicationContext context = getApplicationContext();
        if (context != null) {
            String[] endpointBeanNames = context.getBeanNamesForAnnotation(WebsocketEndpoint.class);
            for (String beanName : endpointBeanNames) {
                endpointClasses.add(context.getType(beanName));
            }
        }

        for (Class<?> endpointClass : endpointClasses) {

            WebsocketEndpoint annotation = endpointClass.getAnnotation(WebsocketEndpoint.class);
            if (annotation == null) {
                throw new DeploymentException("serverContainer.missingAnnotation");
            }
            String path = annotation.value();

            // Method mapping
            PojoMethodMapping methodMapping = new PojoMethodMapping(Endpoint, annotation.decoders(), path);

            // ServerEndpointConfig
            ServerEndpointConfig sec;
            Class<? extends ServerEndpointConfig.Configurator> configuratorClazz = WebsocketConfiguration.SpringBootConfigurator;
            ServerEndpointConfig.Configurator configurator = null;
            if (!configuratorClazz.equals(ServerEndpointConfig.Configurator.class)) {
                try {
                    configurator = configuratorClazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new DeploymentException("serverContainer.configuratorFail", e);
                }
            }
            sec = ServerEndpointConfig.Builder.create(endpointClass, path).
                    decoders(Arrays.asList(annotation.decoders())).
                    encoders(Arrays.asList(annotation.encoders())).
                    subprotocols(Arrays.asList(annotation.subprotocols())).
                    configurator(configurator).
                    build();
            sec.getUserProperties().put(
                    PojoEndpointServer.POJO_METHOD_MAPPING_KEY,
                    methodMapping);

            registerEndpoint(sec);
        }

        if (context != null) {
            Map<String, ServerEndpointConfig> endpointConfigMap = context.getBeansOfType(ServerEndpointConfig.class);
            for (ServerEndpointConfig endpointConfig : endpointConfigMap.values()) {
                registerEndpoint(endpointConfig);
            }
        }
    }

    private void registerEndpoint(Class<?> endpointClass) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("Registering @ServerEndpoint class: " + endpointClass);
            }
            getServerContainer().addEndpoint(endpointClass);
        }
        catch (DeploymentException ex) {
            throw new IllegalStateException("Failed to register @ServerEndpoint class: " + endpointClass, ex);
        }
    }

    private void registerEndpoint(ServerEndpointConfig endpointConfig) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("Registering ServerEndpointConfig: " + endpointConfig);
            }
            getServerContainer().addEndpoint(endpointConfig);
        }
        catch (DeploymentException ex) {
            throw new IllegalStateException("Failed to register ServerEndpointConfig: " + endpointConfig, ex);
        }
    }

}
