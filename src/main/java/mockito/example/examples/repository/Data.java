package mockito.example.examples.repository;

import mockito.example.examples.models.Exam;

import java.util.Arrays;
import java.util.List;

public class Data {
    public final static List<Exam> EXAMS = Arrays.asList(new Exam(5L, "Matematicas"), new Exam(6L, "Lengua"), new Exam(7L, "Filosofia"), new Exam(8L, "Historia"));

    public final static List<Exam> EXAMS_ID_NULL = Arrays.asList(new Exam(null, "Matematicas"), new Exam(null, "Lengua"), new Exam(null, "Filosofia"),
            new Exam(null, "Historia"));

    public final static List<Exam> NEGATIVE_ID_EXAMS = Arrays.asList(new Exam(-5L, "Matematicas"), new Exam(-6L, "Lengua"), new Exam(-7L, "Filosofia"),
            new Exam(-8L, "Historia"));

    public final static List<String> QUESTIONS = Arrays.asList("Aritmetica", "Integrales", "Derivadas", "Trigonometria", "Geometria");

    public final static Exam EXAM = new Exam(null, "Fisica");
}
