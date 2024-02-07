package com.insiro.weather.service;

import com.insiro.weather.domain.dto.CityDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.exception.CityNameConflictException;
import com.insiro.weather.exception.CityNotFoundException;
import com.insiro.weather.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private CityService cityService;

    public City generateCity() {
        City city = new City();
        city.setName("Seoul");
        city.setId(1L);
        return city;
    }


    @Test
    public void testCreateCity() {
        String busan = "Busan";

        City city = generateCity();
        CityDTO cityDTO = new CityDTO(city);
        Mockito.when(cityRepository.save(any())).thenReturn(city);

        Mockito.when(cityRepository.findByName(city.getName())).thenReturn(Optional.of(city));
        Mockito.when(cityRepository.findByName(busan)).thenReturn(Optional.empty());

        // conflict test
        assertThrows(CityNameConflictException.class, () -> cityService.addCity(cityDTO));
        // Test create Success
        cityDTO.setName(busan);
        City addedCity = cityService.addCity(cityDTO);
        assertEquals(city.getName(), addedCity.getName());

    }


    // Add more test cases for other methods in CityService if needed
    @Test
    public void testUpdateCity() {
        City city = generateCity();
        CityDTO updateDTO = new CityDTO();
        String cityName = city.getName();
        String busan = "Busan";

        updateDTO.setName(busan);
        Mockito.when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
        Mockito.when(cityRepository.findByName(busan)).thenReturn(Optional.empty());
        //Conflict Test
        assertThrows(CityNameConflictException.class, () -> cityService.updateCity(cityName, new CityDTO(city)));

        // Not Found Test
        assertThrows(CityNotFoundException.class, () -> cityService.updateCity(busan, new CityDTO(city)));
        // Success test, should not throw Exception
        assertDoesNotThrow(()->cityService.updateCity(city.getName(), updateDTO));

    }
}