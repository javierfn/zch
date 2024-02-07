package com.inditex.zarachallenge.config;

import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TimeZoneConfiguration {

    @Bean
    public TimeZone setTimeZone() {
        log.info("Set the default application time zone to UTC");

        var timeZone = TimeZone.getTimeZone("UTC");

        TimeZone.setDefault(timeZone);

        return timeZone;
    }
    
}
