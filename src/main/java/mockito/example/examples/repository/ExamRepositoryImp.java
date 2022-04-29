package mockito.example.examples.repository;

import mockito.example.examples.models.Exam;

import java.util.List;

import static mockito.example.examples.repository.Data.EXAM;
import static mockito.example.examples.repository.Data.EXAMS;

public class ExamRepositoryImp implements ExamRepository {
    @Override
    public List<Exam> findAll() {
        System.out.println("ExamRepositoryImp.findAll");
        return EXAMS;
    }

    @Override
    public Exam saveExam(Exam exam) {
        System.out.println("ExamRepositoryImp.saveExam");
        return EXAM;
    }
}
