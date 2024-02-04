package com.insiro.weather.service;

import com.insiro.weather.domain.dto.CityDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityDTO addCity(String name) {
        City city = new City();
        city.setName(name);
        city = cityRepository.save(city);
        return new CityDTO(city);
    }

    public Optional<CityDTO> getCityByName(String name) {
        Optional<City> city = cityRepository.findByName(name);
        if (city.isEmpty()) return Optional.empty();
        return Optional.of(new CityDTO(city.get()));
    }
    public List<CityDTO> getAllCities(){
        return cityRepository.findAll().stream().map(CityDTO::new).collect(Collectors.toList());
    }
    public void removeCity(String name) {
        cityRepository.deleteByName(name);
    }
}
