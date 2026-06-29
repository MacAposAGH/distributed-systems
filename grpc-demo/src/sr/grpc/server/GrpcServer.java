package sr.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class GrpcServer {
    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
    private Server server;

    private void start() throws IOException {
        String address = "127.0.0.5";
        int port = 50051;
        SocketAddress socket;

        try {
            socket = new InetSocketAddress(InetAddress.getByName(address), port);
        } catch (UnknownHostException ignored) {
        }

        server = ServerBuilder.forPort(port).executor((Executors.newFixedThreadPool(16)))
                .addService(new GradeBookImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("Shutting down gRPC server...");
                GrpcServer.this.stop();
                System.err.println("Server shut down.");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }

}
