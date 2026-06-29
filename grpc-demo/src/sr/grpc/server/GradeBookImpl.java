package sr.grpc.server;

import com.google.protobuf.DoubleValue;
import sr.grpc.gen.*;
import sr.grpc.gen.GradeBookGrpc.GradeBookImplBase;

import java.util.*;
import java.util.stream.Collectors;

public class GradeBookImpl extends GradeBookImplBase {
    private final List<Grade> gradeBook = new ArrayList<>();
    private final Random random = new Random();

    public GradeBookImpl() {
        createData();
    }

    public List<Grade> getStudentGrades(String studentName) {
        return gradeBook
                .stream()
                .filter(grade -> grade.getStudent().getName().equalsIgnoreCase(studentName))
                .toList();
    }

    public List<Grade> getAllSubjectGrades(SubjectName subjectName) {
        return gradeBook
                .stream()
                .filter(student -> student.getSubjectName() == subjectName)
                .toList();
    }

    public Grade getSubjectBestStudent(SubjectName subjectName) {
        return getAllSubjectGrades(subjectName)
                .stream()
                .max(Comparator.comparing(Grade::getGrade))
                .orElse(null);
    }

    public Grade getStudentBestSubject(String studentName) {
        return getStudentGrades(studentName)
                .stream()
                .max(Comparator.comparing(Grade::getGrade))
                .orElse(null);
    }

    public DoubleValue getStudentGpa(String studentName) {
        return DoubleValue.of(getStudentGrades(studentName)
                .stream()
                .mapToDouble(Grade::getGrade)
                .average()
                .orElse(0));
    }

    public StudentGpa getBestGpa() {
        return gradeBook.stream()
                .collect(Collectors.groupingBy(
                        Grade::getStudent,
                        Collectors.averagingDouble(Grade::getGrade)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> StudentGpa.newBuilder().setStudent(e.getKey()).setGpa(DoubleValue.of(e.getValue())).build())
                .orElse(null);
    }


    public Grades createGrades(List<Grade> grades) {
        return Grades.newBuilder()
                .addAllGrades(grades)
                .build();
    }

    private void createData() {
        for (String name : List.of("Daniel", "William", "Emma", "Olivia", "Sophia")) {
            Student student = Student.newBuilder()
                    .setName(name)
                    .build();
            List<Grade> grades = Arrays.stream(SubjectName.values())
                    .filter(subjectName -> subjectName != SubjectName.UNRECOGNIZED)
                    .map(subjectName ->
                            Grade.newBuilder()
                                    .setGrade(randomGrade())
                                    .setSubjectName(subjectName)
                                    .setStudent(student)
                                    .build())
                    .toList();
            gradeBook.addAll(grades);
        }
    }

    public double randomGrade() {
        return random.nextInt(0, 7) * 0.5 + 2;
    }

    @Override
    public void getStudentGrades(sr.grpc.gen.Student request,
                                    io.grpc.stub.StreamObserver<sr.grpc.gen.Grades> responseObserver) {
        String name = request.getName();
        Grades result = createGrades(getStudentGrades(name));
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentGpa(sr.grpc.gen.Student request,
                              io.grpc.stub.StreamObserver<com.google.protobuf.DoubleValue> responseObserver) {
        String name = request.getName();
        DoubleValue result = getStudentGpa(name);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentBestSubject(sr.grpc.gen.Student request,
                                      io.grpc.stub.StreamObserver<sr.grpc.gen.Grade> responseObserver) {
        String name = request.getName();
        Grade result = getStudentBestSubject(name);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getSubjectBestStudent(sr.grpc.gen.Subject request,
                                      io.grpc.stub.StreamObserver<sr.grpc.gen.Grade> responseObserver) {
        SubjectName subjectName = request.getSubjectName();
        Grade result = getSubjectBestStudent(subjectName);
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }


    @Override
    public void getBestGpa(com.google.protobuf.Empty request,
                           io.grpc.stub.StreamObserver<sr.grpc.gen.StudentGpa> responseObserver) {
        StudentGpa result = getBestGpa();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}