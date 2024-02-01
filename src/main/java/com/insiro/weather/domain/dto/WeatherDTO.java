package com.insiro.weather.domain.dto;


import com.insiro.weather.domain.model.Weather;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherDTO {
    Long id;
    LocalDateTime date;
    Double temperature;
    Double perceivedTemperature;
    Double humidity;
    @Builder
    public WeatherDTO(Weather weather){
        this.id = weather.getId();
        this.date= weather.getDate();
        this.temperature = weather.getTemperature();
        this.perceivedTemperature = weather.getPerceivedTemperature();
        this.humidity = weather.getHumidity();
    }

}
