package sr.grpc.server;

import sr.grpc.gen.Grade;
import sr.grpc.gen.GradeBookGrpc.GradeBookImplBase;
import sr.grpc.gen.GradesOpResult;
import sr.grpc.gen.Student;
import sr.grpc.gen.SubjectName;

import java.util.*;
import java.util.stream.Collectors;

public class GradeBookImpl extends GradeBookImplBase {
    private final List<Grade> gradeBook = new ArrayList<>();
    private final Random random = new Random();

    public GradeBookImpl() {
        createGrades();
    }

    public double randomGrade() {
        return random.nextInt(0, 7) * 0.5 + 2;
    }

    public List<Grade> getAllGrades() {
        return gradeBook.stream()
                .sorted(Comparator.comparing(Grade::getSubject)
                        .thenComparing(s -> s.getStudent().getName()))
                .toList();
    }

    public List<Grade> getAllGradesByStudentName(String studentName) {
        return gradeBook
                .stream()
                .filter(grade -> grade.getStudent().getName().equalsIgnoreCase(studentName))
                .toList();
    }

    public List<Grade> getAllGradesBySubjectName(SubjectName subjectName) {
        return gradeBook
                .stream()
                .filter(student -> student.getSubject() == subjectName)
                .toList();
    }

    public Grade getSubjectBestStudent(SubjectName subjectName) {
        return getAllGradesBySubjectName(subjectName)
                .stream()
                .max(Comparator.comparing(Grade::getGrade))
                .orElse(null);
    }

    public Grade getStudentBestSubject(String studentName) {
        return getAllGradesByStudentName(studentName)
                .stream()
                .max(Comparator.comparing(Grade::getGrade))
                .orElse(null);
    }

    public double getStudentGpa(String studentName) {
        return getAllGradesByStudentName(studentName)
                .stream()
                .mapToDouble(Grade::getGrade)
                .average()
                .orElse(0);
    }

    public Map.Entry<Student, Double> getBestGpa() {
        return gradeBook.stream()
                .collect(Collectors.groupingBy(
                        Grade::getStudent,
                        Collectors.averagingDouble(Grade::getGrade)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }

    private void createGrades() {
        for (String name : List.of("Daniel", "William", "Emma", "Olivia", "Sophia")) {
            Student student = Student.newBuilder()
                    .setName(name)
                    .build();
            List<Grade> grades = Arrays.stream(SubjectName.values())
                    .filter(subjectName -> subjectName != SubjectName.UNRECOGNIZED)
                    .map(subjectName ->
                            Grade.newBuilder()
                                    .setGrade(randomGrade())
                                    .setSubject(subjectName)
                                    .setStudent(student)
                                    .build())
                    .toList();
            gradeBook.addAll(grades);
        }
    }

    @Override
    public void getAllGradesByStudentName(sr.grpc.gen.StudentNameOpArguments request,
                                          io.grpc.stub.StreamObserver<sr.grpc.gen.GradesOpResult> responseObserver) {
        String name = request.getName();
        List<Grade> grades = getAllGradesByStudentName(name);
        GradesOpResult result = GradesOpResult.newBuilder()
                .addAllGrades(grades)
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
