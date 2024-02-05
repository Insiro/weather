package com.insiro.weather.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    LocalDateTime date;
    @Column
    Double temperature;
    @Column
    Double perceivedTemperature;
    @Column
    Double humidity;
    @ManyToOne
    @JoinColumn(name = "city_id")
    City city ;
}
