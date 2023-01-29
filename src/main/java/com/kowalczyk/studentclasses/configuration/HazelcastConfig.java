package com.kowalczyk.studentclasses.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.kowalczyk.studentclasses.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

@Profile(Profiles.PROD)
@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return HazelcastClient.newHazelcastClient();
    }

    @Bean
    public Map<Integer, Student> studentCache(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("students");
    }
}
