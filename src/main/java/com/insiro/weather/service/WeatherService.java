package com.insiro.weather.service;

import com.insiro.weather.domain.dto.NewWeatherDTO;
import com.insiro.weather.domain.dto.WeatherDTO;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }
    public Optional<WeatherDTO> getWeatherById(Long id){
        Weather weather =  weatherRepository.findById(id).orElse(null);
        if (weather ==null)
            return Optional.empty();
        return Optional.of( new WeatherDTO(weather));
    }
    public List<WeatherDTO> getWeathers(){
        List<Weather> weathers = weatherRepository.findAll();
        return weathers.stream().map(weather-> new WeatherDTO(weather)).collect(Collectors.toList());
    }
    public WeatherDTO createWeather(NewWeatherDTO newWeatherDTO){
        Weather weather = new Weather();
        weather.setDate(newWeatherDTO.getDate());
        weather.setHumidity(newWeatherDTO.getHumidity());
        weather.setTemperature(newWeatherDTO.getTemperature());
        weather.setPerceivedTemperature(newWeatherDTO.getHumidity());
        Weather saved = this.weatherRepository.save(weather);
        return new WeatherDTO(saved);
    }
    public void deleteWeather(Long id){
        this.weatherRepository.deleteById(id);
    }
}
