package com.insiro.weather.repository;


import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findByCity(City city);
}
