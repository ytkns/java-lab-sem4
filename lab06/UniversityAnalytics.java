import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.*;
import java.io.IOException;

record CourseResult(String courseName, int credits, double grade) {}
record Student(int id, String name, String major, List<CourseResult> results) {}

public class UniversityAnalytics{

    public static void main(String[] args) {
        Map<Integer, Student> studentMap = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("course_data.csv"));

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String major = parts[2];
                String courseName = parts[3];
                int credits = Integer.parseInt(parts[4]);
                double grade = Double.parseDouble(parts[5]);

                CourseResult newResult = new CourseResult(courseName, credits, grade);

                studentMap.computeIfAbsent(id, k -> new Student(id, name, major, new ArrayList<>()))
                          .results().add(newResult);
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        }

        List<Student> students = new ArrayList<>(studentMap.values());

        System.out.println("Część A:");
        System.out.println("Liczba studentów: " + students.size());
        students.forEach(s -> System.out.println(s.name()));

 
        System.out.println("\nCzęść B:");
        System.out.println("Studenci Informatyki (CS):");
        students.stream()
                .filter(s -> s.major().equalsIgnoreCase("CS"))
                .map(Student::name)
                .forEach(System.out::println);

        long highAchievers = students.stream()
                .filter(s -> s.results().stream().anyMatch(r -> r.grade() >= 4.5))
                .count();
        System.out.println("Liczba osób z przynajmniej jedną oceną >= 4.5: " + highAchievers);


        System.out.println("\nCzęść C:");
        List<String> uniqueCourses = students.stream()
                .flatMap(s -> s.results().stream())
                .map(CourseResult::courseName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Unikalne kursy: " + uniqueCourses);


        System.out.println("\nCzęść D:");
        double avgAll = students.stream()
                .flatMap(s -> s.results().stream())
                .mapToDouble(CourseResult::grade)
                .average()
                .orElse(0.0);
        System.out.printf("Średnia wszystkich ocen: %.2f%n", avgAll);

        double maxGrade = students.stream()
                .flatMap(s -> s.results().stream())
                .mapToDouble(CourseResult::grade)
                .max()
                .orElse(0.0);
        System.out.println("Najwyższa ocena: " + maxGrade);
        
        System.out.println("Studenci z najwyższą oceną:");
        students.stream()
                .filter(s -> s.results().stream().anyMatch(r -> r.grade() == maxGrade))
                .map(Student::name)
                .distinct()
                .forEach(System.out::println);


        System.out.println("\nCzęść E:");

        Map<String, Double> avgByMajor = students.stream()
                .flatMap(s -> s.results().stream()
                .map(r -> new AbstractMap.SimpleEntry<>(s.major(), r.grade())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.averagingDouble(Map.Entry::getValue)
                ));
        System.out.println("Średnia ocen kieruneku: " + avgByMajor);

        Map<String, Double> avgByCourse = students.stream()
                .flatMap(s -> s.results().stream())
                .collect(Collectors.groupingBy(
                        CourseResult::courseName,
                        Collectors.averagingDouble(CourseResult::grade)
                ));
        System.out.println("Średnia ocen kursu: " + avgByCourse);
    }
}