package pl.pjatk.clinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.clinic.exception.DoctorException;
import pl.pjatk.clinic.exception.PatientException;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private ConsultationService consultationService;

    Doctor doctor;
    Patient patient;

    @BeforeEach
    void createObjects() {
        doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        doctor.setId(1);

        patient = new Patient("Pablo", "Badyliano", "92092363677", LocalDate.parse("2021-02-13"), doctor);
        patient.setId(1);
    }

    @Test
    void testDelayConsultationByWeekById() throws DoctorException, PatientException {
        //Given
        when(patientService.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(doctorService.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        Patient expected = new Patient("Pablo", "Badyliano", "92092363677", LocalDate.parse("2021-02-20"), doctor);
        when(patientService.update(patient.getPesel(), patient)).thenReturn(expected);
        //When
        consultationService.delayConsultationByWeekById(doctor.getId(), patient.getId());
        //Then
        assertThat(patient.getDateOfConsultation()).isEqualTo(expected.getDateOfConsultation());
    }

    @Test
    void testDelayConsultationByWeekByIdThrowException() throws PatientException, DoctorException {
        //Given
        int notDoctorID = 1519;
        when(patientService.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(doctorService.findById(notDoctorID)).thenReturn(Optional.of(doctor));
        //Then
        assertThrows(DoctorException.class, () ->
                consultationService.delayConsultationByWeekById(notDoctorID, patient.getId()));
    }
}