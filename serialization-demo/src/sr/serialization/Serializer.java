package sr.serialization;

import com.google.protobuf.Timestamp;
import sr.serialization.proto.ForecastProto.Forecast;
import sr.serialization.proto.ForecastProto.Weather;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Random;

public class Serializer implements Runnable {
    private final DatagramSocket socket;
    private final Random random = new Random();

    public Serializer() throws SocketException {
        this.socket = new DatagramSocket(4445);
    }

    @Override
    public void run() {
        while (true) {
            byte[] bytes = new byte[256];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String payload = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.printf("A packet received from: %s : %s, payload: %s\n", address.getHostAddress(), port, payload);
            ForecastPojo forecastPojo = generateForecast(payload);
            byte[] bytesResponse = serialize(forecastPojo);
            System.out.println("Serialization completed");

            packet = new DatagramPacket(bytesResponse, bytesResponse.length, packet.getAddress(), packet.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Packet sent to the client");
        }
    }

    private byte[] serialize(ForecastPojo forecastPojo) {
        Forecast.Builder builder = Forecast.newBuilder()
                .setCity(forecastPojo.getCity());
        for (WeatherPojo weatherPojo : forecastPojo.getWeatherPojos()) {
            Weather weather = Weather.newBuilder()
                    .setDate(Timestamp.newBuilder()
                            .setSeconds(weatherPojo.getDate().atStartOfDay().getSecond())
                            .build())
                    .setTemperature(weatherPojo.getTemperature())
                    .setCloudy(weatherPojo.getCloudy())
                    .setChanceOfPrecipitation(weatherPojo.getChanceOfPrecipitation())
                    .setWind(weatherPojo.getWind())
                    .build();
            builder.addForecast(weather);
        }
        builder.build();
        return builder.build().toByteArray();
    }

    private ForecastPojo generateForecast(String city) {
        ForecastPojo forecastPojo = new ForecastPojo(city);
        for (int i = 0; i < 3; i++) {
            forecastPojo.addWeather(generateWeatherPojo(i));
        }
        Path path = Path.of("forecast.txt");
        try {
            Files.writeString(path, forecastPojo.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return forecastPojo;
    }

    private WeatherPojo generateWeatherPojo(int day) {
        LocalDate date = LocalDate.now().plusDays(day);
        double minTemperature = 10;
        double maxTemperature = 35;
        double temperature = round(minTemperature + (maxTemperature - minTemperature) * random.nextDouble());
        double wind = round(20 * random.nextDouble());
        double cloudy = round(100 * random.nextDouble());
        double chanceOfPrecipitation = round(cloudy * random.nextDouble());
        return new WeatherPojo(date, temperature,wind, cloudy, chanceOfPrecipitation);
    }

    private double round(double number) {
        return Math.round(number * 10) / 10.0;
    }

}
