package com.insiro.weather.service;

import com.insiro.weather.domain.dto.CityDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.exception.CityNameConflictException;
import com.insiro.weather.exception.CityNotFoundException;
import com.insiro.weather.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(CityDTO cityDTO) {
        if (this.getCityByName(cityDTO.getName()).isEmpty())
            throw new CityNameConflictException(cityDTO.getName());

        City city =createCityFromDTO(cityDTO);
        return cityRepository.save(city);
    }

    public Optional<City> getCityByName(String name) {
        return cityRepository.findByName(name);
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }
    public City updateCity(String cityName, CityDTO cityDTO) {
        Optional<City> optionalCity = getCityByName(cityName);
        if (optionalCity.isEmpty())
            throw new CityNotFoundException(cityName);
        if (getCityByName(cityName).isPresent())
            throw new CityNameConflictException(cityName);

        City newCity = createCityFromDTO(cityDTO);
        newCity.setId(optionalCity.get().getId());
        return cityRepository.save(newCity);
    }
    public City createCityFromDTO(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        Long id = city.getId();
        if (id!= null)
            city.setId(id);
        return city;
    }
}
