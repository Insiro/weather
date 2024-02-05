package com.insiro.weather.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insiro.weather.utils.LocalDateTypeAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractControllerTest{

    protected MockMvc mockMvc;
    protected final Gson gson;
    final DateTimeFormatter dateTimeFormatter;

    public AbstractControllerTest() {
        this.dateTimeFormatter = LocalDateTypeAdapter.dateTimeFormatter;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();
    }
    @BeforeEach
    abstract public void init();
}
