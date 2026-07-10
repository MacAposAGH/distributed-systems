package sr.serialization;

import sr.serialization.proto.ForecastProto.Forecast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ForecastPojo {
    private final String city;
    private final Collection<WeatherPojo> weather = new ArrayList<>();

    public ForecastPojo(Forecast forecast) {
        this.city = forecast.getCity().getName();
        forecast.getForecastList().forEach(w -> this.weather.add(new WeatherPojo(w)));
    }

    @Override
    public String toString() {
        String weather = this.weather.stream()
                .map(WeatherPojo::toString)
                .collect(Collectors.joining("\n\n"));
        return """
                Weather forecast for %s
                %s""".formatted(city, weather);
    }
}
