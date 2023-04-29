package com.lhd.tams.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lhd
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("TAMS API")
                        .description("Teaching Affairs Management System API")
                        .version("v1.0.0"));
    }
}
