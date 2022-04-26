package mockito.example.examples.repository;

import mockito.example.examples.models.Exam;

import java.util.List;

public interface ExamRepository {

    List<Exam> findAll();
}
