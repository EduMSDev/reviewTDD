package mockito.example.examples.repository;

import mockito.example.examples.models.Exam;
import mockito.example.examples.services.ExamServiceImp;
import mockito.example.examples.services.ExamServices;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamRepositoryImpTest {

    @Test
    void findByName() {
        List<Exam> exams = Arrays.asList(new Exam(5L, "Matematicas"), new Exam(6L, "Lengua"), new Exam(5L, "Matematicas"),
                new Exam(7L, "Historia"));
        ExamRepository repository = mock(ExamRepository.class);
        when(repository.findAll()).thenReturn(exams);
        ExamServices services = new ExamServiceImp(repository);
        Optional<Exam> exam = services.findByName("Matematicas");
        assertTrue(exam.isPresent());
        assertEquals(5L, exam.get().getId());
        assertEquals("Matematicas", exam.get().getName());
    }

    @Test
    void findByNameEmpty() {
        ExamRepository repository = mock(ExamRepository.class);
        when(repository.findAll()).thenReturn(Collections.emptyList());
        ExamServices services = new ExamServiceImp(repository);
        Optional<Exam> exam = services.findByName("Matematicas");
        assertFalse(exam.isPresent());
    }
}