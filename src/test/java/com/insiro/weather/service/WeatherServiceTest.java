package com.insiro.weather.service;

import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @Mock
    private WeatherRepository weatherRepository;
    @InjectMocks
    private WeatherService weatherService;

    private Weather createWeatherInstance() {
        LocalDateTime time = LocalDateTime.now();
        City city = new City((long) 1, "City");
        return new Weather((long) 1, time, 25.0, 23.4, 2.1, city);
    }

    @Test
    public void testGetWeatherById() {
        Weather weather = createWeatherInstance();
        long id = weather.getId();
        Mockito.when(weatherRepository.findById(id)).thenReturn(Optional.of(weather));

        Optional<Weather> result = weatherService.getWeatherById(id);
        assertTrue(result.isPresent());
    }

    @Test
    public void testGetWeathers() {
        List<Weather> weatherList = new ArrayList<>();
        Mockito.lenient().when(weatherRepository.findAll()).thenReturn(weatherList);

        List<Weather> result = weatherService.getWeathers();

        assertEquals(0, result.size());

    }
}
