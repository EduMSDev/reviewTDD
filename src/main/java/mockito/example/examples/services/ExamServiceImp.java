package mockito.example.examples.services;

import mockito.example.examples.models.Exam;
import mockito.example.examples.repository.ExamRepository;

import java.util.Optional;

public class ExamServiceImp implements ExamServices {

    private final ExamRepository examRepository;

    public ExamServiceImp(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Optional<Exam> findByName(String name) {
        return examRepository.findAll().stream().filter(exam -> exam.getName().equals(name)).findFirst();
    }
}
