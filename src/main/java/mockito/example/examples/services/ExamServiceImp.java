package mockito.example.examples.services;

import mockito.example.examples.models.Exam;
import mockito.example.examples.repository.ExamRepository;
import mockito.example.examples.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class ExamServiceImp implements ExamServices {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;


    public ExamServiceImp(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Exam> findByName(String name) {
        return examRepository.findAll().stream().filter(exam -> exam.getName().equals(name)).findFirst();
    }

    @Override
    public Exam findExamByNameWithQuestions(String name) {
        Optional<Exam> examOptional = findByName(name);
        Exam exam = null;
        if (examOptional.isPresent()) {
            exam = examOptional.orElseThrow(NullPointerException::new);
            List<String> questions = questionRepository.findQuestionByExamId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }

    @Override
    public Exam save(Exam exam) {
        if (!exam.getQuestions().isEmpty()) {
            questionRepository.saveQuestions(exam.getQuestions());
        }

        return examRepository.saveExam(exam);
    }


}
