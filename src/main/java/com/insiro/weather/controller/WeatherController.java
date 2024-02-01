package com.insiro.weather.controller;

import com.insiro.weather.domain.dto.NewWeatherDTO;
import com.insiro.weather.domain.dto.WeatherDTO;
import com.insiro.weather.exception.WeatherNotFoundException;
import com.insiro.weather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("weather")
public class WeatherController {
    private final WeatherService weatherService;

    WeatherController(WeatherService weatherService){
        this.weatherService = weatherService;
    }
    @PostMapping
    public ResponseEntity<WeatherDTO> newWeather(@RequestBody NewWeatherDTO newWeatherDTO){
        WeatherDTO weatherDTO = this.weatherService.createWeather(newWeatherDTO);
        return new ResponseEntity<>(weatherDTO,HttpStatus.CREATED );
    }
    @GetMapping
    public ResponseEntity<List<WeatherDTO>>getWeathers(){
        List<WeatherDTO> weathers = this.weatherService.getWeathers();
        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id){
        weatherService.deleteWeather(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<WeatherDTO>  getWeather(@PathVariable long id){
        Optional<WeatherDTO> weatherDTO = weatherService.getWeatherById(id);
        if (weatherDTO.isEmpty())
            throw  new WeatherNotFoundException(id);
        else
            return new ResponseEntity<>( weatherDTO.get(), HttpStatus.OK);
    }
}

