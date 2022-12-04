package pl.pjatk.clinic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.clinic.exception.PatientException;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;
import pl.pjatk.clinic.repository.PatientRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Patient patient;

    @Mock
    private Doctor doctor;

    @InjectMocks
    private PatientService patientService;

//    @BeforeEach
//    void createObjects() throws PatientException {
//        Patient patient = new Patient("Pablo", "Badyliano", "92092363677", new Doctor(1));
//        Patient patient2 = new Patient("Piotro", "Badolio", "95042614625", new Doctor(1));
//        Patient patient3 = new Patient("Agacia", "Wszebcia", "93042233482", new Doctor(2));
//
//        patientService.save(patient);
//        System.out.println("Before each here");
//    }

    @AfterEach
    void afterEach() throws PatientException {
        patientService.deleteAll();
        System.out.println("After each here");

    }
}
