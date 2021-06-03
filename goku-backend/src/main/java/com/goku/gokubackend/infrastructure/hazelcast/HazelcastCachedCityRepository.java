package com.goku.gokubackend.infrastructure.hazelcast;

import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Primary
public class HazelcastCachedCityRepository implements CityRepository {

    private final CityRepository cityRepository;
    private final Map<String, City> cityCache;

    @Autowired
    public HazelcastCachedCityRepository(CityRepository cityRepository, Map<String, City> cityCache) {
        this.cityRepository = cityRepository;
        this.cityCache = cityCache;
    }

    @Override
    public City create(City newInstance) {
        final City newCity = cityRepository.create(newInstance);
        putInCache(newCity);
        return newCity;
    }

    @Override
    public City findById(String id) {
        if (cityCache.containsKey(id)) {
            return cityCache.get(id);
        }
        final City foundCity = cityRepository.findById(id);
        putInCache(foundCity);
        return foundCity;
    }

    @Override
    public City update(City city) {
        final City updated = cityRepository.update(city);
        putInCache(updated);
        return updated;
    }

    private void putInCache(City city) {
        cityCache.put(city.getId().get(), city);
    }


}
