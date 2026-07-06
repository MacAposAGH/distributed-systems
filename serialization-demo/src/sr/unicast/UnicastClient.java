package sr.unicast;

import com.google.protobuf.InvalidProtocolBufferException;
import sr.serialization.proto.WeatherProto.Weather;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UnicastClient {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("No arguments");
        }

        String city = args[0];
        String host = "127.0.0.7";
        int port = 4445;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);
            byte[] requestBytes = city.getBytes();
            DatagramPacket packet = new DatagramPacket(requestBytes, requestBytes.length, address, port);
            socket.send(packet);
            System.out.println("Packet sent to server");

            byte[] responseBytes = new byte[256];
            packet = new DatagramPacket(responseBytes, responseBytes.length);
            socket.receive(packet);
            System.out.println("A packet received from server");
            byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());

            Weather deserializedWeather;
            try {
                deserializedWeather= Weather.parseFrom(data);
            } catch (InvalidProtocolBufferException e) {
                throw new InvalidProtocolBufferException(e);
            }
            System.out.printf("Deserialization complete: \n%s\n", deserializedWeather);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
