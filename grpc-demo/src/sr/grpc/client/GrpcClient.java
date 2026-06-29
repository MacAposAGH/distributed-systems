package sr.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sr.grpc.gen.GradeBookGrpc;
import sr.grpc.gen.GradeBookGrpc.GradeBookStub;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
    private final ManagedChannel channel;
    private final GradeBookStub calcBlockingStub;

    public GrpcClient(String remoteHost, int remotePort) {
        channel = ManagedChannelBuilder.forAddress(remoteHost, remotePort)
                .usePlaintext()
                .build();
        calcBlockingStub = GradeBookGrpc.newStub(channel);
    }

    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("127.0.0.5", 50051);
        client.run();
    }

    private void run() throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("==> ");
            String line = scanner.nextLine();
            switch (line) {
                case "x":
                case "":
                    break;
                default:
                    System.out.println("???");
                    break;
            }
            if (line.equals("x")) {
                break;
            }
        }

        scanner.close();
        shutdown();
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

}
