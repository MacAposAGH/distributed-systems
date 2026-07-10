package sr.serialization;

import sr.serialization.proto.ForecastProto.Forecast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ForecastPojo {
    private final String city;
    private final Collection<WeatherPojo> weatherPojos = new ArrayList<>();

    public ForecastPojo(String city) {
        this.city = city;
    }

    public ForecastPojo(Forecast forecast) {
        this.city = forecast.getCity();
        forecast.getForecastList().forEach(w -> this.weatherPojos.add(new WeatherPojo(w)));
    }

    public String getCity() {
        return city;
    }

    public Collection<WeatherPojo> getWeatherPojos() {
        return weatherPojos;
    }

    public void addWeather(WeatherPojo weatherPojo) {
        weatherPojos.add(weatherPojo);
    }

    @Override
    public String toString() {
        String weather = this.weatherPojos.stream()
                .map(WeatherPojo::toString)
                .collect(Collectors.joining("\n\n"));
        return """
                Weather forecast for %s
                %s""".formatted(city, weather);
    }
}