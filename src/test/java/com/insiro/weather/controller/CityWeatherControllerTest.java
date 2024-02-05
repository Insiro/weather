package com.insiro.weather.controller;

import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.service.CityService;
import com.insiro.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CityWeatherControllerTest extends AbstractControllerTest {
    @Mock
    private CityService cityService;
    @Mock
    private WeatherService weatherService;
    private MockMvc mockMvc;

    @Override
    public void init() {
        MockitoAnnotations.openMocks(this);
        CityWeatherController cityWeatherController = new CityWeatherController(weatherService, cityService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cityWeatherController).build();
    }

    private Weather createWeatherInstance() {
        LocalDateTime time = LocalDateTime.now();
        City city = new City((long) 1, "City");
        return new Weather((long) 1, time, 25.0, 23.4, 2.1, city);
    }

    @Test
    public void testGetWeathers() throws Exception {
        Weather weather = createWeatherInstance();
        City city = weather.getCity();
        List<Weather> weatherList = new ArrayList<>();
        Mockito.when(cityService.getCityByName(city.getName())).thenReturn(Optional.of(city));
        Mockito.when(weatherService.getWeathers(city)).thenReturn(weatherList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/cities/%s", city.getName())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }


}
