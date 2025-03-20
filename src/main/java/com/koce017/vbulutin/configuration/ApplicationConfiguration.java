package com.koce017.vbulutin.configuration;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Slugify slugify() {
        return Slugify.builder()
                .lowerCase(true)
                .build();
    }

}
