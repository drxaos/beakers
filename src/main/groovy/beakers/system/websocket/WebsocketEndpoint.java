package beakers.system.websocket;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import java.lang.annotation.*;

/**
 * Annotation for specifying websocket endpoint
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WebsocketEndpoint {
    /**
     * URI or URI-template that the annotated class should be mapped to.
     *
     * @return The URI or URI-template that the annotated class should be mapped
     * to.
     */
    String value();

    String[] subprotocols() default {};

    Class<? extends Decoder>[] decoders() default {};

    Class<? extends Encoder>[] encoders() default {};
}
