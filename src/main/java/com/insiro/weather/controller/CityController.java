package com.insiro.weather.controller;

import com.insiro.weather.domain.dto.CityDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.exception.CityNotFoundException;
import com.insiro.weather.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("cities")
public class CityController {
    private final CityService cityService;

    CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<CityDTO> cityDTOS = cityService.getAllCities().stream().map(CityDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(cityDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CityDTO> createNewCity(@RequestBody CityDTO cityDTO) {
        City newCity = cityService.addCity(cityDTO);
        CityDTO newCityDTO = new CityDTO(newCity);
        return new ResponseEntity<>(newCityDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{cityName}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable String cityName, @RequestBody CityDTO cityDTO) {
        City updateCity = cityService.updateCity(cityName, cityDTO);
        CityDTO dto = new CityDTO(updateCity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<CityDTO> getAllCity(@PathVariable String cityName) {
        Optional<City> city = cityService.getCityByName(cityName);
        if (city.isEmpty())
            throw new CityNotFoundException(cityName);
        return new ResponseEntity<>(new CityDTO(city.get()), HttpStatus.OK);
    }
}
