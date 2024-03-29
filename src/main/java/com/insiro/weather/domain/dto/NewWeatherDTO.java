package com.insiro.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewWeatherDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NonNull LocalDateTime date;
    @NonNull Double temperature;
    @NonNull Double perceivedTemperature;
    @NonNull Double humidity;
    @Nullable String cityName;
}
