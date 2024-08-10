package com.tanvx.cities.domain.city.repository;

import com.tanvx.cities.domain.city.dto.response.CityResponse;
import com.tanvx.cities.domain.city.entity.City;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

  Optional<City> findByName(String name);

  @Query("SELECT new com.tanvx.cities.domain.city.dto.response.CityResponse(c.id, c.name) FROM City c")
  Page<CityResponse> findAllCity(Pageable pageable);
}
