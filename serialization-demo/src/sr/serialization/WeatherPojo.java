package sr.serialization;

import sr.serialization.proto.ForecastProto.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class WeatherPojo {
    private final LocalDate date;
    private final double temperature;
    private final double wind;
    private final double cloudy;
    private final double chanceOfPrecipitation;

    public WeatherPojo(LocalDate date, double temperature, double wind, double cloudy, double chanceOfPrecipitation) {
        this.date = date;
        this.temperature = temperature;
        this.wind = wind;
        this.cloudy = cloudy;
        this.chanceOfPrecipitation = chanceOfPrecipitation;
    }

    public WeatherPojo(Weather weather) {
        this.date = Instant.ofEpochSecond(weather.getDate().getSeconds())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        this.temperature = weather.getTemperature();
        this.wind = weather.getWind();
        this.cloudy = weather.getCloudy();
        this.chanceOfPrecipitation = weather.getChanceOfPrecipitation();
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWind() {
        return wind;
    }

    public double getCloudy() {
        return cloudy;
    }

    public double getChanceOfPrecipitation() {
        return chanceOfPrecipitation;
    }

    @Override
    public String toString() {
        return """
                \tDate: %s
                \tTemperature: %s C
                \tWind: %s km/h
                \tCloudy: %s %%
                \tChance of precipitation: %s %%""".formatted(date, temperature, wind, cloudy, chanceOfPrecipitation);
    }
}
