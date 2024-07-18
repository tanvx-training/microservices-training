package com.tanvx.measurements.app.batch.datasource;

import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.domain.city.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class CityData {

    private final CityRepository cityRepository;

    @Bean
    @Lazy
    public Map<String, City> mapCity() {
        return cityRepository.findAll()
                .stream()
                .collect(Collectors.toMap(City::getName, city -> city));
    }
}
