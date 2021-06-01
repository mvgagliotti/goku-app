package com.goku.gokubackend.infrastructure;

import com.goku.gokubackend.domain.City;
import com.goku.gokubackend.domain.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Primary
public class CachedCityRepository implements CityRepository {

    private final CityRepository cityRepository;
    private final Map<String, City> cityCache;

    @Autowired
    public CachedCityRepository(CityRepository cityRepository, Map<String, City> cityCache) {
        this.cityRepository = cityRepository;
        this.cityCache = cityCache;
    }

    @Override
    public City create(City newInstance) {
        final City newCity = cityRepository.create(newInstance);
        cityCache.put(newCity.getId().get(), newCity);
        return newCity;
    }

    @Override
    public City findById(String id) {
        if (cityCache.containsKey(id)) {
            return cityCache.get(id);
        }
        final City foundCity = cityRepository.findById(id);
        cityCache.put(foundCity.getId().get(), foundCity);
        return foundCity;
    }

    @Override
    public City update(City city) {
        final City updated = cityRepository.update(city);
        cityCache.put(updated.getId().get(), updated);
        return updated;
    }
}
