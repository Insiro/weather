package com.insiro.weather.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.insiro.weather.domain.model.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WeatherDTO {
    @NonNull Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NonNull LocalDateTime date;
    @NonNull Double temperature;
    @NonNull Double perceivedTemperature;
    @NonNull Double humidity;
    @NonNull String cityName;
    public WeatherDTO(Weather weather){
        this.id = weather.getId();
        this.date= weather.getDate();
        this.temperature = weather.getTemperature();
        this.perceivedTemperature = weather.getPerceivedTemperature();
        this.humidity = weather.getHumidity();
        this.cityName = weather.getCity().getName();
    }
}
