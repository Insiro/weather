package com.insiro.weather.controller;

import com.insiro.weather.domain.dto.CityDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.service.CityService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest extends AbstractControllerTest{
    @Mock
    private CityService cityService;
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        CityController cityController = new CityController(cityService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    public void testGetAllCity() throws Exception {
        List<City> cities = new ArrayList<>();
        Mockito.when(cityService.getAllCities()).thenReturn(cities);
        mockMvc.perform(MockMvcRequestBuilders.get("/cities"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetCity() throws Exception {
        City city = new City((long) 1, "City");
        Mockito.when(cityService.getCityByName(city.getName())).thenReturn(Optional.of(city));
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cities/%s", city.getName())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(city.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(city.getName()));
    }

    @Test
    public void testCreateCity() throws Exception {
        City city = new City((long) 1, "City");

        Mockito.when(cityService.addCity(Mockito.any(CityDTO.class))).thenReturn(city);
        city.setId(null);
        String content = gson.toJson(city);


        mockMvc.perform((MockMvcRequestBuilders.post("/cities")
                        .contentType(MediaType.APPLICATION_JSON))
                        .content(content)).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(city.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(city.getName()));
    }

}
