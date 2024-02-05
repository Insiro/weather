package com.insiro.weather.controller;

import com.insiro.weather.domain.dto.NewWeatherDTO;
import com.insiro.weather.domain.dto.WeatherDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.service.CityService;
import com.insiro.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest extends AbstractControllerTest {
    @Mock
    private WeatherService weatherService;
    @Mock
    private CityService cityService;
    private MockMvc mockMvc;


    private Weather createWeatherInstance() {
        LocalDateTime time = LocalDateTime.now();
        City city = new City((long) 1, "City");
        return new Weather((long) 1, time, 25.0, 23.4, 2.1, city);
    }

    @BeforeEach
    @Override
    public void init() {
        MockitoAnnotations.openMocks(this);
        WeatherController weatherController = new WeatherController(weatherService, cityService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    public void testGetAllWeather() throws Exception {
        List<Weather> weatherList = new ArrayList<>();
        Mockito.when(weatherService.getWeathers()).thenReturn(weatherList);

        mockMvc.perform(MockMvcRequestBuilders.get("/weathers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }


    @Test
    public void testGetWeatherById() throws Exception {
        Weather weather = createWeatherInstance();

        Mockito.when(weatherService.getWeatherById((long) 1)).thenReturn(Optional.of(weather));
        mockMvc.perform(MockMvcRequestBuilders.get("/weathers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testCreateWeather() throws Exception {
        Weather weather = createWeatherInstance();
        WeatherDTO weatherDTO = new WeatherDTO(weather);
        String content = gson.toJson(weatherDTO);


        // Mocking service response
        Mockito.when(cityService.getCityByName(Mockito.any(String.class))).thenReturn(Optional.of(weather.getCity()));
        Mockito.when(weatherService.createWeather(Mockito.any(City.class), Mockito.any(NewWeatherDTO.class))).thenReturn(weather);

        mockMvc.perform(MockMvcRequestBuilders.post("/weathers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cityName").value(weather.getCity().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.humidity").value(weather.getHumidity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(weather.getTemperature()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perceivedTemperature").value(weather.getPerceivedTemperature()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(weather.getDate().format(dateTimeFormatter)));

    }

}
