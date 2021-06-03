package com.goku.gokubackend.application.config;

import com.goku.gokubackend.domain.City;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Map;

import static com.goku.gokubackend.infrastructure.hazelcast.Serializers.citySerializer;

@Configuration
@PropertySource("config.properties")
public class CacheConfig {

    @Autowired
    private Environment env;

    @Bean
    public ClientConfig hazelcastConfig() {
        ClientConfig clientConfig = new ClientConfig();

        clientConfig
                .getSerializationConfig()
                .addSerializerConfig(buildSerializerConfig(citySerializer(), City.class));

        clientConfig.getNetworkConfig().addAddress(env.getProperty("hazelcast.url"));
        return clientConfig;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig config) {
        return HazelcastClient.newHazelcastClient(config);
    }

    @Bean
    public Map<String, City> cityCache(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("cities");
    }

    private SerializerConfig buildSerializerConfig(Serializer serializer, Class clazz) {
        SerializerConfig serializerConfig = new SerializerConfig()
                .setImplementation(serializer)
                .setTypeClass(clazz);
        return serializerConfig;
    }

}
