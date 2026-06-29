package sr.grpc.client;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import sr.grpc.gen.*;
import sr.grpc.gen.GradeBookGrpc.GradeBookFutureStub;
import sr.grpc.gen.GradeBookGrpc.GradeBookStub;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class GrpcClient {
    private final ManagedChannel channel;
    private final GradeBookStub gradeBookNonblockingStub;
    private final GradeBookFutureStub gradeBookFutureStub;
    private final Scanner scanner = new Scanner(System.in);

    public GrpcClient(String remoteHost, int remotePort) {
        channel = ManagedChannelBuilder.forAddress(remoteHost, remotePort)
                .usePlaintext()
                .build();
        gradeBookNonblockingStub = GradeBookGrpc.newStub(channel);
        gradeBookFutureStub = GradeBookGrpc.newFutureStub(channel);
    }

    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("127.0.0.5", 50051);
        client.run();
    }

    private String[] extractArgument(String line, List<String> argumentMethods) {
        String[] split = line.trim().split("\\s*--\\s*");
        return split.length == 2 && argumentMethods.contains(split[0]) && !split[1].isEmpty() ? split : null;
    }

    private void run() throws InterruptedException {

        final String grades = "grades";
        final String gpa = "gpa";
        final String bestSubject = "best-subject";
        final String bestStudent = "best-student";
        List<String> requiresArgument = List.of(grades, gpa, bestSubject, bestStudent);
        String format = "%-10s:%-10s\n";
        while (true) {
            System.out.print("==> ");
            String method = scanner.nextLine();
            String argument = null;
            if (method.contains("--")) {
                String[] split = extractArgument(method, requiresArgument);
                if (split == null) {
                    System.out.println("Invalid argument");
                    continue;
                }
                method = split[0];
                argument = split[1];
            }

            switch (method) {
                case grades:
                    Student student1 = Student.newBuilder().setName(argument).build();
                    StreamObserver<Grades> gradesObserver = createResponseObserver((g) -> {
                        List<Grade> gradesList = g.getGradesList();
                        System.out.printf("%s's grades:\n", gradesList.getFirst().getStudent().getName());
                        for (Grade grade : gradesList) {
                            System.out.printf(format,
                                    subjectNameToLowerCase(grade.getSubjectName()),
                                    grade.getGrade());
                        }
                    });
                    gradeBookNonblockingStub.getStudentGrades(student1, gradesObserver);
                    break;
                case gpa:
                    Student student2 = Student.newBuilder().setName(argument).build();
                    StreamObserver<DoubleValue> gpaObserver = createResponseObserver(d ->
                            System.out.println(d.getValue()));
                    gradeBookNonblockingStub.getStudentGpa(student2, gpaObserver);
                    break;
                case bestSubject:
                    Student student3 = Student.newBuilder().setName(argument).build();
                    StreamObserver<Grade> bestSubjectObserver = createResponseObserver(g ->
                            System.out.printf(format, subjectNameToLowerCase(g.getSubjectName()), g.getGrade()));
                    gradeBookNonblockingStub.getStudentBestSubject(student3, bestSubjectObserver);
                    break;
                case bestStudent:
                    SubjectName subjectName = null;
                    try {
                        subjectName = SubjectName.valueOf(argument.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid subject name");
                    }
                    Subject subject = Subject.newBuilder().setSubjectName(subjectName).build();
                    ListenableFuture<Grade> subjectBestStudent = gradeBookFutureStub.getSubjectBestStudent(subject);
                    FutureCallback<Grade> subjectBestStudentCallback = createFutureCallable(g ->
                            System.out.printf(format, g.getStudent().getName(), g.getGrade()));
                    Futures.addCallback(subjectBestStudent, subjectBestStudentCallback, MoreExecutors.directExecutor());
                    break;
                case "best-gpa":
                    ListenableFuture<StudentGpa> bestGpa = gradeBookFutureStub.getBestGpa(Empty.getDefaultInstance());
                    FutureCallback<StudentGpa> bestGpaCallback = createFutureCallable(s ->
                            System.out.printf(format, s.getStudent().getName(), s.getGpa().getValue()));
                    Futures.addCallback(bestGpa, bestGpaCallback, MoreExecutors.directExecutor());
                    break;
                case "x":
                case "":
                    break;
                default:
                    System.out.println("???");
                    break;
            }
            if (method.equals("x")) {
                break;
            }
        }
        scanner.close();
        shutdown();
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String subjectNameToLowerCase(SubjectName subjectName) {
        return subjectName.toString().toLowerCase();
    }

    public String subjectNameToLowerCase(Subject subject) {
        return subjectNameToLowerCase(subject.getSubjectName());
    }

    public <V> StreamObserver<V> createResponseObserver(Consumer<V> consumer) {
        return new StreamObserver<>() {
            @Override
            public void onNext(V var1) {
                consumer.accept(var1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("gRPC ERROR");
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    public <V> FutureCallback<V> createFutureCallable(Consumer<V> consumer) {
        return new FutureCallback<V>() {
            @Override
            public void onSuccess(V result) {
                consumer.accept(result);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("gRPC ERROR");
            }
        };
    }
}
