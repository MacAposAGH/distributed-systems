package sr.unicast;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import sr.serialization.proto.WeatherProto;
import sr.serialization.proto.WeatherProto.Weather;
import sr.serialization.proto.WeatherProto.Weather.City;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;

public class Serializer implements Runnable {
    private final DatagramSocket socket;

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

    private double round(double number) {
        double pow = Math.pow(10, 2);
        return Math.round(number * pow) / pow;
    }

    private byte[] serialize(String cityName) {
        Random random = new Random();
        Timestamp date = Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build();
        City city = WeatherProto.Weather.City.newBuilder().setName(cityName).build();
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
        return weather.toByteArray();
    }
}
