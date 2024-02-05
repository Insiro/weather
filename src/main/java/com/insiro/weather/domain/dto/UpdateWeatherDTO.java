package com.insiro.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateWeatherDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Nullable LocalDateTime date;
    @Nullable Double temperature;
    @Nullable Double perceivedTemperature;
    @Nullable Double humidity;
    @Nullable String cityName;
}
