package com.insiro.weather.service;

import com.insiro.weather.domain.dto.NewWeatherDTO;
import com.insiro.weather.domain.dto.UpdateWeatherDTO;
import com.insiro.weather.domain.model.City;
import com.insiro.weather.domain.model.Weather;
import com.insiro.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Optional<Weather> getWeatherById(Long id) {
        return weatherRepository.findById(id);
    }

    public List<Weather> getWeathers() {
        return weatherRepository.findAll();
    }

    public List<Weather> getWeathers(City city) {
        return weatherRepository.findByCity(city);
    }

    public Weather createWeather(City city, NewWeatherDTO newWeatherDTO) {
        Weather weather = new Weather();
        weather.setDate(newWeatherDTO.getDate());
        weather.setHumidity(newWeatherDTO.getHumidity());
        weather.setTemperature(newWeatherDTO.getTemperature());
        weather.setPerceivedTemperature(newWeatherDTO.getHumidity());
        weather.setCity(city);
        weather = this.weatherRepository.save(weather);
        return weather;
    }

    public void deleteWeather(Long id) {
        this.weatherRepository.deleteById(id);
    }

    public Optional<Weather> updateWeather(Long id, UpdateWeatherDTO weatherDTO, Optional<City> city) {
        Optional<Weather> optionalWeather = weatherRepository.findById(id);
        if (optionalWeather.isEmpty())
            return Optional.empty();

        Weather weather = optionalWeather.get();

        if (weatherDTO.getDate() != null)
            weather.setDate(weatherDTO.getDate());
        if (weatherDTO.getHumidity() != null)
            weather.setHumidity(weatherDTO.getHumidity());
        if (weatherDTO.getPerceivedTemperature() != null)
            weather.setPerceivedTemperature(weatherDTO.getPerceivedTemperature());
        if (weatherDTO.getTemperature() != null)
            weather.setTemperature(weatherDTO.getTemperature());
        if (city.isPresent())
            weather.setCity(city.get());
        Weather updatedWeather = weatherRepository.save(weather);
        return Optional.of(updatedWeather);
    }


}
