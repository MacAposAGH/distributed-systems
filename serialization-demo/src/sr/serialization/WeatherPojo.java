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

    public WeatherPojo(Weather weather) {
        this.date = Instant.ofEpochSecond(weather.getDate().getSeconds())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        this.temperature = weather.getTemperature();
        this.wind = weather.getWind();
        this.cloudy = weather.getCloudy();
        this.chanceOfPrecipitation = weather.getChanceOfPrecipitation();
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
