package com.tanvx.measurements.batch.datasource;

import com.tanvx.measurements.entity.City;
import com.tanvx.measurements.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CityData {

    private final CityRepository cityRepository;

    @Bean
    public Map<String, City> mapCity() {
        return cityRepository.findAll()
                .stream()
                .collect(Collectors.toMap(City::getName, city -> city));
    }
}
