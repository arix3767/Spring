package com.kowalczyk.studentclasses.configuration;

import com.kowalczyk.studentclasses.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Profile(Profiles.DEV)
@Configuration
public class BasicCacheConfig {

    @Bean
    public Map<Integer, Student> studentCache() {
        return new HashMap<>();
    }
}
