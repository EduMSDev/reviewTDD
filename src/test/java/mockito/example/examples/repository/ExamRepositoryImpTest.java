package mockito.example.examples.repository;

import mockito.example.examples.models.Exam;
import mockito.example.examples.services.ExamServiceImp;
import mockito.example.examples.services.ExamServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Optional;

import static mockito.example.examples.repository.Data.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamRepositoryImpTest {

    @InjectMocks
    ExamServiceImp examServices;

    @Mock
    ExamRepository repository;

    @Mock
    QuestionRepository questionRepository;

    @Captor
    ArgumentCaptor<Long> captor;

    @Test
    void findByName() {
        when(repository.findAll()).thenReturn(EXAMS);
        ExamServices services = new ExamServiceImp(repository, questionRepository);
        Optional<Exam> exam = services.findByName("Matematicas");
        assertTrue(exam.isPresent());
        assertEquals(5L, exam.get().getId());
        assertEquals("Matematicas", exam.get().getName());
    }

    @Test
    void findByNameEmpty() {
        ExamRepository repository = mock(ExamRepository.class);
        when(repository.findAll()).thenReturn(Collections.emptyList());
        ExamServices services = new ExamServiceImp(repository, questionRepository);
        Optional<Exam> exam = services.findByName("Matematicas");
        assertFalse(exam.isPresent());
    }

    @Test
    void examQuestionTest() {
        when(repository.findAll()).thenReturn(EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        Exam exam = examServices.findExamByNameWithQuestions("Matematicas");
        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("Aritmetica"));
    }

    @Test
    void examQuestionVerifyTest() {
        when(repository.findAll()).thenReturn(EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        Exam exam = examServices.findExamByNameWithQuestions("Matematicas");
        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("Aritmetica"));
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(5L);
    }

    @Test
    void examQuestionNotExistVerifyTest() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        Exam exam = examServices.findExamByNameWithQuestions("Matematicas2");
        assertNull(exam);
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(5L);
    }

    @Test
    void saveExamTest() {
        Exam newExam = EXAM;
        newExam.setQuestions(QUESTIONS);
        when(repository.saveExam(any(Exam.class))).then(new Answer<Exam>() {
            //GIVEN
            Long secuence = 8L;

            @Override
            public Exam answer(InvocationOnMock invocationOnMock) {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(secuence++);
                return exam;
            }
        });

        //WHEN
        Exam exam = examServices.save(newExam);
        assertNotNull(exam.getId());
        assertEquals(8L, exam.getId());
        assertEquals("Fisica", exam.getName());
        verify(repository).saveExam(any(Exam.class));
        verify(questionRepository).saveQuestions(anyList());
    }

    @Test
    void controllingExceptionTest() {
        when(repository.findAll()).thenReturn(EXAMS_ID_NULL);
        when(questionRepository.findQuestionByExamId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examServices.findExamByNameWithQuestions("Matematicas");
        });

        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(isNull());

    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(NEGATIVE_ID_EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        examServices.findExamByNameWithQuestions("Matematicas");

        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(argThat(new MiArgsMatchers()));
    }

    @Test
    void testArgumentMatchers2() {
        when(repository.findAll()).thenReturn(EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        examServices.findExamByNameWithQuestions("Matematicas");

        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(argThat(args -> args == 5L));
    }

    @Test
    void argumentCaptureTest() {
        when(repository.findAll()).thenReturn(EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        examServices.findExamByNameWithQuestions("Matematicas");

        // ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(questionRepository).findQuestionByExamId(captor.capture());

        assertEquals(5L, captor.getValue());
    }

    @Test
    void doThrowTest() {
        Exam exam = EXAM;
        exam.setQuestions(QUESTIONS);

        doThrow(IllegalArgumentException.class).when(questionRepository).saveQuestions(anyList());

        assertThrows(IllegalArgumentException.class, () -> {
            examServices.save(exam);
        });

    }

    @Test
    void doAnswerTest() {
        when(repository.findAll()).thenReturn(EXAMS);
        //when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(QUESTIONS);
        doAnswer(invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0);
            return id == 5L ? QUESTIONS : Collections.emptyList();

        }).when(questionRepository).findQuestionByExamId(anyLong());

        Exam exam = examServices.findExamByNameWithQuestions("Matematicas");
        assertEquals(5L, exam.getId());
        assertEquals("Matematicas", exam.getName());
        assertEquals(5, exam.getQuestions().size());
    }

    @Test
    void saveExamDoAnswerTest() {
        Exam newExam = EXAM;
        newExam.setQuestions(QUESTIONS);
        doAnswer(new Answer<Exam>() {
            //GIVEN
            Long secuence = 8L;

            @Override
            public Exam answer(InvocationOnMock invocationOnMock) {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(secuence++);
                return exam;
            }
        }).when(repository).saveExam(any(Exam.class));

        //WHEN
        Exam exam = examServices.save(newExam);
        assertNotNull(exam.getId());
        assertEquals(8L, exam.getId());
        assertEquals("Fisica", exam.getName());
        verify(repository).saveExam(any(Exam.class));
        verify(questionRepository).saveQuestions(anyList());
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {
        private Long argument;

        @Override
        public boolean matches(Long aLong) {
            this.argument = aLong;
            return aLong != null && aLong > 0;
        }

        @Override
        public String toString() {
            return "Custom error message. The long must be a positive number!: " + argument;
        }
    }
}