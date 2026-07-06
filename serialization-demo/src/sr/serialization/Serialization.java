package sr.serialization;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import sr.serialization.proto.WeatherProto.Weather;
import sr.serialization.proto.WeatherProto.Weather.City;

import java.time.Instant;
import java.util.Random;

public class Serialization {

    public static double round(double number){
        double pow = Math.pow(10, 2);
        return Math.round(number * pow) / pow;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Timestamp date = Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build();
        City city = City.newBuilder().setName("Poznan").build();
        double minTemperature = 10;
        double maxTemperature = 35;
        double temperature = round(minTemperature + (maxTemperature - minTemperature) * random.nextDouble());
        double cloudy = round(100 * random.nextDouble());
        double chanceOfPrecipitation = round(cloudy * random.nextDouble());
        double wind = round(20 * random.nextDouble());

        Weather weather = Weather.newBuilder()
                .setCity(city)
                .setDate(date)
                .setTemperature(temperature)
                .setCloudy(cloudy)
                .setChanceOfPrecipitation(chanceOfPrecipitation)
                .setWind(wind)
                .build();
        System.out.printf("Before serialization:\n%s\n" , weather);

        byte[] serializedWeather = weather.toByteArray();
        System.out.printf("After serialization:\n%s\n" , serializedWeather);


        Weather deserializedWeather;
        try {
             deserializedWeather = Weather.parseFrom(serializedWeather);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("After deserialization \n%s\n" , deserializedWeather);
    }
}
