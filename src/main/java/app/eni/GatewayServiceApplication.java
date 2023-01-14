package app.eni;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication


public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

@Bean
public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
            .route( r-> r.path("/api/encadrant/**")
                    .filters(f->f.rewritePath("/api/encadrant/(?<remains>.*)","/${remains}")
                            .preserveHostHeader()
                    )
                    .uri("lb://ENCADRANT-SERVICE")
            )
            .route(r -> r.path("/api/etudiant/**")
                    .filters(f -> f.rewritePath("/api/etudiant/(?<remains>.*)", "/${remains}")                                )
                    .uri("lb://ETUDIANT-SERVICE")
            )
            .route(r -> r.path("//**")
                    .filters(f -> f.rewritePath("/api/stage/(?<remains>.*)", "/${remains}")                                )
                    .uri("lb://STAGE-SERVICE")
            )
            .build();
}}