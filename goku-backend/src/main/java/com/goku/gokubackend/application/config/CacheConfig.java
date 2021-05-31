package com.goku.gokubackend.application.config;

import com.goku.gokubackend.domain.City;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

//    @Bean
//    public ClientConfig hazelcastConfig() {
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.getNetworkConfig().addAddress("localhost:5701"); //add to env
//        return clientConfig;
//    }

    //TODO: fix hazelcast configuration
//    @Bean
//    public HazelcastInstance hazelcastInstance(ClientConfig config) {
//        return HazelcastClient.newHazelcastClient(config);
//    }

//    @Bean
//    public Map<String, City> cityCache(HazelcastInstance hazelcastInstance) {
//        return hazelcastInstance.getMap("cities");
//    }

    @Bean
    public Map<String, City> cityCache() {
        return new HashMap<>();
    }
}
