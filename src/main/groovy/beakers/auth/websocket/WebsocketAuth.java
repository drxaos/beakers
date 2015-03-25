package beakers.auth.websocket;

import java.lang.annotation.*;

/**
 * Annotation for specifying access-control expression which will be evaluated to decide whether a
 * websocket connection is allowed or not.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WebsocketAuth {
    /**
     * @return the Spring-EL expression to be evaluated before allowing connection
     */
    String value();
}
