package com.insiro.weather.controller;

import com.insiro.weather.domain.dto.NewWeatherDTO;
import com.insiro.weather.domain.dto.UpdateWeatherDTO;
import com.insiro.weather.domain.dto.WeatherDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.exception.CityNotFoundException;
import com.insiro.weather.exception.WeatherNotFoundException;
import com.insiro.weather.service.CityService;
import com.insiro.weather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("weathers")
public class WeatherController {
    private final WeatherService weatherService;
    private final CityService cityService;

    public WeatherController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<WeatherDTO> newWeather(@RequestBody NewWeatherDTO newWeatherDTO) {
        Optional<City> city = this.cityService.getCityByName(newWeatherDTO.getCityName());
        if (city.isEmpty())throw  new CityNotFoundException(newWeatherDTO.getCityName());
        Weather weather = this.weatherService.createWeather(city.get(), newWeatherDTO);

        return new ResponseEntity<>(new WeatherDTO(weather), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WeatherDTO>> getWeathers() {
        List<WeatherDTO> weathers = this.weatherService.getWeathers().stream().map(WeatherDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        weatherService.deleteWeather(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherDTO> getWeather(@PathVariable long id) {
        Optional<Weather> weather = weatherService.getWeatherById(id);
        if (weather.isEmpty())
            throw new WeatherNotFoundException(id);
        return new ResponseEntity<>(new WeatherDTO(weather.get()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WeatherDTO> updateWeather(@PathVariable long id, @RequestBody UpdateWeatherDTO updateWeatherDTO) {
        Optional<City> city ;
        if (updateWeatherDTO.getCityName() !=null) {
            city = cityService.getCityByName(updateWeatherDTO.getCityName());
            if (city.isEmpty())throw  new CityNotFoundException(updateWeatherDTO.getCityName());
        }
        else city = Optional.empty();

        Optional<WeatherDTO> weatherDTO = weatherService.updateWeather(id, updateWeatherDTO, city);
        if (weatherDTO.isEmpty())
            throw new WeatherNotFoundException(id);
        return new ResponseEntity<>(weatherDTO.get(), HttpStatus.OK);
    }

}

