package com.goku.gokubackend.application.config;

import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.infrastructure.hazelcast.OptionalSerializer;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@PropertySource("config.properties")
public class CacheConfig {

    @Autowired
    private Environment env;

//    @Bean
//    public ClientConfig hazelcastConfig() {
//        ClientConfig clientConfig = new ClientConfig();
//
//        SerializerConfig serializerConfig = new SerializerConfig()
//                .setImplementation(new OptionalSerializer())
//                .setTypeClass(Optional.class);
//
//        clientConfig.getSerializationConfig()
//                .setAllowOverrideDefaultSerializers(true)
//                .addSerializerConfig(serializerConfig);
//
//        clientConfig.getNetworkConfig().addAddress(env.getProperty("hazelcast.url"));
//        return clientConfig;
//    }
//
//    @Bean
//    public HazelcastInstance hazelcastInstance(ClientConfig config) {
//        return HazelcastClient.newHazelcastClient(config);
//    }
//
//    @Bean
//    public Map<String, City> cityCache(HazelcastInstance hazelcastInstance) {
//        return hazelcastInstance.getMap("cities");
//    }

    @Bean
    public Map<String, City> cityCache() {
        return new HashMap<>();
    }

}
