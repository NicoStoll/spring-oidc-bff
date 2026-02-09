package de.deutscherv.gq0500.springbff.spa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This tells Spring: "If someone asks for /main.js, look in static/browser/main.js"
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/browser/");
    }
}
