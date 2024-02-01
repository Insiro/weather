package com.insiro.weather.repository;


import com.insiro.weather.domain.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
