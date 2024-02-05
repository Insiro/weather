package com.insiro.weather.controller;

import com.insiro.weather.domain.dto.NewWeatherDTO;
import com.insiro.weather.domain.dto.WeatherDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.exception.CityNotFoundException;
import com.insiro.weather.service.CityService;
import com.insiro.weather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("cities/{cityName}")
public class CityWeatherController {
    private final WeatherService weatherService;
    private final CityService cityService;

    public CityWeatherController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<WeatherDTO> newWeather(@PathVariable String cityName, @RequestBody NewWeatherDTO newWeatherDTO) {
        Optional<City> city = this.cityService.getCityByName(cityName);
        if (city.isEmpty()) throw new CityNotFoundException(cityName);

        Weather weather = this.weatherService.createWeather(city.get(), newWeatherDTO);

        return new ResponseEntity<>(new WeatherDTO(weather), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WeatherDTO>> getWeathers(@PathVariable String cityName) {
        Optional<City> city = this.cityService.getCityByName(cityName);
        if (city.isEmpty()) throw new CityNotFoundException(cityName);
        List<WeatherDTO> weathers = this.weatherService.getWeathers(city.get()).stream().map(WeatherDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }

}
