package mockito.example.examples.repository;

import java.util.List;

import static mockito.example.examples.repository.Data.QUESTIONS;

public class QuestionRepositoryImpl implements QuestionRepository {
    @Override
    public List<String> findQuestionByExamId(Long id) {
        System.out.println("QuestionRepositoryImpl.findQuestionByExamId");
        return QUESTIONS;
    }

    @Override
    public void saveQuestions(List<String> questions) {
        System.out.println("QuestionRepositoryImpl.saveQuestions");

    }
}
