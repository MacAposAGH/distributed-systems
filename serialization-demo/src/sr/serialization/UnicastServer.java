package sr.serialization;

import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnicastServer {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(new Serializer());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
