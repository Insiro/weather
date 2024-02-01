package com.insiro.weather.domain.dto;

import com.insiro.weather.domain.model.Weather;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewWeatherDTO {
    LocalDateTime date;
    Double temperature;
    Double perceivedTemperature;
    Double humidity;
    @Builder
    public NewWeatherDTO(Weather weather){
        this.date= weather.getDate();
        this.temperature = weather.getTemperature();
        this.perceivedTemperature = weather.getPerceivedTemperature();
        this.humidity = weather.getHumidity();
    }

}
