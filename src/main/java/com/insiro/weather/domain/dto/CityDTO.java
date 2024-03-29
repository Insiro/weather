package com.insiro.weather.domain.dto;

import com.insiro.weather.domain.model.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    @Nullable
    Long id;
    @NonNull
    String name;
    public CityDTO(City city){
        this.id = city.getId();
        this.name = city.getName();
    }
}
