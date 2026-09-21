package pragma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * No necesitas tocar esta clase: es plomería de Spring necesaria para que
 * las clases anotadas con @ServerEndpoint (como NotificacionesEndpoint)
 * se registren en el Tomcat embebido.
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
