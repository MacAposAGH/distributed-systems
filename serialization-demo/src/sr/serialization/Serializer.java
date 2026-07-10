package sr.serialization;

import com.google.protobuf.Timestamp;
import sr.serialization.proto.ForecastProto.City;
import sr.serialization.proto.ForecastProto.Forecast;
import sr.serialization.proto.ForecastProto.Weather;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

            byte[] bytesResponse = serialize(payload);
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

    private byte[] serialize(String cityName) {
        Forecast.Builder builder = Forecast.newBuilder()
                .setCity(City.newBuilder().setName(cityName).build());
        for (int i = 0; i < 3; i++) {
            builder.addForecast(generateWeather(i));
        }
        return builder.build().toByteArray();
    }

    private double round(double number) {
        return Math.round(number * 10) / 10.0;
    }

    private Weather generateWeather(int day) {
        Timestamp date = Timestamp.newBuilder()
                .setSeconds(Instant.now().plus(day, ChronoUnit.DAYS).getEpochSecond())
                .build();
        double minTemperature = 10;
        double maxTemperature = 35;
        double temperature = round(minTemperature + (maxTemperature - minTemperature) * random.nextDouble());
        double cloudy = round(100 * random.nextDouble());
        double chanceOfPrecipitation = round(cloudy * random.nextDouble());
        double wind = round(20 * random.nextDouble());
        return Weather.newBuilder()
                .setDate(date)
                .setTemperature(temperature)
                .setCloudy(cloudy)
                .setChanceOfPrecipitation(chanceOfPrecipitation)
                .setWind(wind)
                .build();
    }
}
