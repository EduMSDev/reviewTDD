package mockito.example.examples.services;

import mockito.example.examples.models.Exam;

import java.util.Optional;

public interface ExamServices {
    Optional<Exam> findByName(String name);
}
